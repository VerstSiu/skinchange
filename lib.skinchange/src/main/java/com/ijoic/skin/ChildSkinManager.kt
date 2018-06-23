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

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.ijoic.ktx.rxjava.execute
import com.ijoic.skin.attr.SkinAttrSupport
import com.ijoic.skin.callback.SkinChangeCallback
import com.ijoic.skin.edit.SkinEditor
import com.ijoic.skin.edit.SkinEditorManager
import com.ijoic.skin.view.ActivitySkinTask
import com.ijoic.skin.view.FragmentSkinTask
import java.io.File
import java.lang.ref.WeakReference

/**
 * Child skin manager.
 *
 * @author verstsiu on 2018/6/23.
 * @version 2.0
 */
internal class ChildSkinManager {

  private var refContext: WeakReference<Context>? = null
  private var skinPrefs: SkinPreference? = null

  private val context: Context?
      get() = refContext?.get()

  /**
   * Initialize.
   *
   * <p>Do invoke this method on your custom Application's onCreate method.</p>
   *
   * @param context context.
   *
   * @see android.app.Application
   */
  fun init(context: Context) {
    val appContext = context.applicationContext
    resourcesTool.setContext(appContext)
    refContext = WeakReference(appContext)

    restoreSkinInfo(context)
  }

  private fun restoreSkinInfo(context: Context) {
    val prefs = createSkinPreference(context)

    if (prefs.pluginEnabled) {
      val pluginPath = prefs.pluginPath
      val pluginPackage = prefs.pluginPackage
      val pluginSuffix = prefs.pluginSuffix

      if (pluginPackage != null && initPlugin(pluginPath, pluginPackage, pluginSuffix)) {
        upgradeSkinId(pluginPackage, pluginSuffix)
      } else {
        prefs.pluginEnabled = false
        upgradeSkinId(prefs.suffix)
        resetResourcesManager()
      }
    } else {
      upgradeSkinId(prefs.suffix)
      resetResourcesManager()
    }
  }

  private fun createSkinPreference(context: Context): SkinPreference {
    return SkinPreference(context).apply {
      skinPrefs = this
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Resources manager.
   */
  internal val resourcesManager = ResourcesManager()

  val resourcesTool = ResourcesTool(resourcesManager)

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> plugin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private fun initPlugin(path: String?, packageName: String?, suffix: String?): Boolean {
    if (path == null || packageName == null || !isValidPluginParams(path, packageName)) {
      return false
    }

    return try {
      loadPlugin(path, packageName, suffix)
      true
    } catch (e: Exception) {
      e.printStackTrace()
      clearPluginInfo()
      false
    }
  }

  /**
   * Check plugin params.
   *
   * Pass conditions:
   * 1. path, packageName not empty.
   * 2. plugin package exist.
   * 3. packageName == [path file].packageName
   *
   * @param path plugin path.
   * @param packageName plugin package name.
   */
  private fun isValidPluginParams(path: String?, packageName: String?): Boolean {
    // check path format
    if (path == null || path.isBlank() || packageName == null || packageName.isBlank()) {
      return false
    }
    // check plugin file exist
    if (!File(path).exists()) {
      return false
    }

    // check package info
    return checkPackageExist(path, packageName)
  }

  private fun checkPackageExist(path: String, packageName: String): Boolean {
    val context = this.context ?: return false
    val pm = context.packageManager
    val info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
    return packageName == info.packageName
  }

  @Throws(Exception::class)
  private fun loadPlugin(path: String, packageName: String, suffix: String?) {
    val context = this.context ?: return
    val manager = AssetManager::class.java.newInstance()
    val addAssetPath = manager.javaClass.getMethod("addAssetPath", String::class.java)
    addAssetPath.invoke(manager, path)

    val superRes = context.resources
    val res = Resources(manager, superRes.displayMetrics, superRes.configuration)

    resourcesManager.apply {
      setResources(res, true)
      setSkinInfo(packageName, suffix)
    }
    updatePluginInfo(path, packageName, suffix)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> plugin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val editManager by lazy { SkinEditorManager() }

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
  var skinSuffix: String? = null
    private set

  /**
   * Skin id.
   */
  internal var skinId: String? = null

  /**
   * Upgrade skin id.
   *
   * @param suffix suffix.
   */
  private fun upgradeSkinId(suffix: String?) {
    skinId = suffix
    skinSuffix = suffix
  }

  /**
   * Upgrade skin id.
   *
   * @param pluginPackage plugin package.
   * @param pluginSuffix plugin suffix.
   */
  private fun upgradeSkinId(pluginPackage: String, pluginSuffix: String?) {
    skinId = "$pluginPackage:${pluginSuffix.orEmpty()}"
    skinSuffix = pluginSuffix
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
    val skinCallback = callback ?: SkinChangeCallback.DEFAULT_CALLBACK
    skinCallback.onStart()

    if (path == null || packageName == null || !isValidPluginParams(path, packageName)) {
      skinCallback.onError("invalid plugin path or package")
      return
    }

    execute({
      try {
        loadPlugin(path, packageName, suffix)
        true
      } catch (e: Exception) {
        e.printStackTrace()
        false
      }
    }, { initSuccess ->
      if (!initSuccess) {
        skinCallback.onError("load plugin occur error")

      } else {
        try {
          notifyChangedListeners()
          skinCallback.onComplete()
        } catch (e: Exception) {
          e.printStackTrace()
          skinCallback.onError(e.message ?: "")
        }
      }
    })
  }

  private fun updatePluginInfo(path: String, packageName: String, suffix: String? = null) {
    val prefs = skinPrefs ?: throw IllegalArgumentException("skin preference not found")

    prefs.apply {
      pluginEnabled = true
      pluginPath = path
      pluginPackage = packageName
      pluginSuffix = suffix
    }
    upgradeSkinId(packageName, suffix)
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
    val context = this.context ?: return

    resourcesManager.apply {
      setResources(context.resources, false)
      setSkinInfo(context.packageName, skinSuffix)
    }
  }

  private fun notifyChangedListeners() {
    editManager.performSkinChange(skinId)
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
    val skinViews = SkinAttrSupport.getSkinViews(view, skinId)

    skinViews.forEach {
      it.apply(skinId, resourcesManager)
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}