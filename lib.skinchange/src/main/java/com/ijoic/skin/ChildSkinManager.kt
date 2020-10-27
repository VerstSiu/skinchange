/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.skin

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import com.ijoic.ktx.guava.cache.weakCache
import com.ijoic.ktx.rxjava.execute
import com.ijoic.ktx.util.getOrCreate
import com.ijoic.skin.attr.SkinAttrSupport
import com.ijoic.skin.callback.SkinChangeCallback
import com.ijoic.skin.edit.SkinEditor
import com.ijoic.skin.edit.SkinEditorManager
import com.ijoic.skin.res.ResFactory
import com.ijoic.skin.view.ActivitySkinTask
import com.ijoic.skin.view.FragmentSkinTask
import java.util.concurrent.TimeUnit

/**
 * Child skin manager.
 *
 * @author verstsiu on 2018/6/23.
 * @version 2.0
 */
class ChildSkinManager internal constructor(
    private val tag: String? = null,
    private val parent: ChildSkinManager? = null) {

  private val skinPrefs by weakCache(10, TimeUnit.SECONDS) {
    val context = SkinManager.context

    when {
      context != null -> SkinPreference(context, tag)
      else -> null
    }
  }

  private var currResManager: ResourcesManager? = null
  private var currResTool: ResourcesTool? = null

  /**
   * Resources manager.
   */
  internal val resourcesManager: ResourcesManager
      get() = matchFollowParent(
          { it.resourcesManager },
          { this::currResManager.getOrCreate { initResourcesManager() } }
      )

  val resourcesTool: ResourcesTool
      get() = matchFollowParent(
          { it.resourcesTool },
          { this::currResTool.getOrCreate { ResourcesTool(resourcesManager) } }
      )

  /**
   * Follow parent status.
   *
   * <p>Use <code>followParent = false</code> or <p>changeSkin(suffix)</p> to toggle
   * follow parent status.</p>
   */
  var followParent: Boolean
      get() = skinPrefs?.followParent ?: false
      set(value) {
        val oldValue = this::followParent.get()

        if (value != oldValue) {
          if (value) {
            skinPrefs?.followParent = true
            notifyChangedListeners()
          } else {
            skinPrefs?.followParent = false
            changeSkin(null)
          }
        }
      }

  /**
   * Match follow parent.
   *
   * @param onTrue picker: fun(parent).
   * @param onFalse picker: fun().
   * @return pick result.
   */
  private fun<T> matchFollowParent(onTrue: (ChildSkinManager) -> T, onFalse: () -> T): T {
    return when {
      followParent && parent != null -> onTrue(parent)
      else -> onFalse()
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val editManager by lazy { SkinEditorManager(this) }

  /**
   * Returns skin editor for expected lifecycle.
   *
   * @param lifecycle lifecycle.
   */
  fun edit(lifecycle: Lifecycle): SkinEditor {
    return editManager.getEditor(lifecycle)
  }

  /**
   * Register activity.
   *
   * @param activity activity.
   * @return editor instance.
   */
  fun register(activity: FragmentActivity) {
    val editor = editManager.getEditor(activity.lifecycle)
    editor.clearCompatItems()
    editor.addTask(activity, ActivitySkinTask)
  }

  /**
   * Register fragment.
   *
   * @param fragment fragment.
   * @return editor instance.
   */
  fun register(fragment: Fragment) {
    val editor = editManager.getEditor(fragment.lifecycle)
    editor.clearCompatItems()
    editor.addTask(fragment, FragmentSkinTask)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> skin info :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Skin suffix.
   */
  val skinSuffix: String?
    get() = matchFollowParent(
        { it.skinSuffix },
        {
          val prefs = skinPrefs

          when {
            prefs == null -> null
            prefs.pluginEnabled -> prefs.pluginSuffix
            else -> prefs.suffix
          }
        }
    )

  private var currSkinId: String? = null

  /**
   * Skin id.
   */
  internal val skinId: String?
      get() = matchFollowParent(
          { it.skinId },
          { currSkinId }
      )

  /**
   * Upgrade skin id.
   *
   * @param suffix suffix.
   */
  private fun upgradeSkinId(suffix: String?) {
    currSkinId = suffix
  }

  /**
   * Upgrade skin id.
   *
   * @param pluginPackage plugin package.
   * @param pluginSuffix plugin suffix.
   */
  private fun upgradeSkinId(pluginPackage: String, pluginSuffix: String?) {
    currSkinId = "$pluginPackage:${pluginSuffix.orEmpty()}"
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> skin info :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> change skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Change skin.
   *
   * Use <code>null</code> to restore default skin, and [suffix] otherwise.
   */
  fun changeSkin(suffix: String?) {
    clearPluginInfo() // clear before

    skinPrefs?.apply {
      this.followParent = false
      this.pluginEnabled = false
      this.suffix = suffix
      upgradeSkinId(suffix)
      resetResourcesManager()
      notifyChangedListeners()
    }
  }

  /**
   * Change skin(plugin).
   *
   * @param path plugin path.
   * @param packageName plugin package name.
   * @param suffix skin suffix.
   * @param callback callback.
   */
  @JvmOverloads
  fun changeSkin(path: String?, packageName: String?, suffix: String? = null, callback: SkinChangeCallback? = null) {
    val prefs = this.skinPrefs ?: return

    prefs.apply {
      followParent = false
      pluginEnabled = true
      pluginPath = path
      pluginPackage = packageName
      pluginSuffix = suffix
    }

    execute({
      resetResourcesManager()
      prefs.pluginEnabled

    }, { initSuccess ->
      if (!initSuccess) {
        callback?.onError("load plugin occur error")

      } else {
        try {
          notifyChangedListeners()
          callback?.onComplete()
        } catch (e: Exception) {
          e.printStackTrace()
          callback?.onError(e.message ?: "")
        }
      }
    })
  }

  /**
   * Remove all skin.
   */
  fun removeAnySkin() {
    clearPluginInfo()
    upgradeSkinId(null)
    resetResourcesManager()
    notifyChangedListeners()
  }

  private fun clearPluginInfo() {
    skinPrefs?.clearPluginInfo()
  }

  private fun resetResourcesManager() {
    currResManager = initResourcesManager()
    currResTool = null
  }

  private fun notifyChangedListeners() {
    editManager.performSkinChange(skinId)
  }

  private fun initResourcesManager(): ResourcesManager {
    val context = SkinManager.context
    val prefs = this.skinPrefs
    var resManager: ResourcesManager? = null

    if (context != null && prefs != null) {
      val pluginPath: String?
      val pluginPackage: String?
      val pluginSuffix: String?

      if (prefs.pluginEnabled) {
        pluginPath = prefs.pluginPath
        pluginPackage = prefs.pluginPackage
        pluginSuffix = prefs.pluginSuffix
      } else {
        pluginPath = null
        pluginPackage = null
        pluginSuffix = null
      }
      val res = ResFactory.getResources(context,pluginPath, pluginPackage, onError = { prefs.pluginEnabled = false })

      resManager = when {
        prefs.pluginEnabled && pluginPackage != null -> {
          upgradeSkinId(pluginPackage, pluginSuffix)
          ResFactory.getResManager(res, pluginPackage, pluginSuffix)
        }
        else -> {
          val suffix = prefs.suffix
          upgradeSkinId(suffix)
          ResFactory.getResManager(res, context.packageName, suffix)
        }
      }
    }
    return resManager ?: ResourcesManager.blank
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> change skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Inject skin.
   *
   * Inject and perform skin changed if needed for expected view and it's children.
   *
   * @param view expected view
   */
  fun injectSkin(view: View) {
    val skinId = this.skinId
    // measure skin views
    val skinViews = SkinAttrSupport.getSkinViews(view, skinId)

    // prepare and apply resources
    skinViews.forEach {
      it.prepareResource(skinId, resourcesManager)
      it.applyResource()
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}