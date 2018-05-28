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

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.ijoic.skin.attr.SkinAttrSupport
import com.ijoic.skin.callback.SkinChangeCallback
import com.ijoic.skin.edit.SkinEditor
import com.ijoic.skin.edit.SkinEditorManager
import com.ijoic.skin.view.*
import java.io.File
import java.lang.ref.WeakReference

/**
 * Skin manager.
 *
 * Use [View.getTag]/[View.setTag] to toggle application skin.
 *
 * Usage:
 * 1. Prepare multiple resources for default skin and other skins your expect to toggle.
 *
 *     ```xml
 *     <resources>
 *       <color "simple_text_color">#666</color>
 *       <color "simple_text_color_red">#FF4081</color>
 *     </resources>
 *     ```
 *
 * 2. Set tag value for views inside xml:
 *
 *     ```xml
 *     <TextView
 *       android:textColor="@color/simple_text_color"
 *       android:tag="skin:simple_text_color:textColor"/>
 *     ```
 *
 * 3. Register activity/fragment to skin manager:
 *
 *     ```kotlin
 *     class MyActivity {
 *        override fun onCreate(savedInstanceState: Bundle?) {
 *          setContentView(R.layout.simple_activity)
 *          SkinManager.register(this)
 *          ..
 *        }
 *
 *        override fun onDestroy() {
 *          super.onDestroy()
 *          SkinManager.unregister(this)
 *        }
 *     }
 *     ```
 *
 * 4. Toggle skin:
 *
 *     ```kotlin
 *     SkinManager.changeSkin("red")
 *     ```
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 *
 * @see View.getTag
 * @see View.setTag
 */
object SkinManager {

  /**
   * Returns skin manager instance.
   */
  @JvmStatic
  @Deprecated(
      "Use skin manager directly",
      ReplaceWith(
          "SkinManager",
          imports = ["com.ijoic.skin.SkinManager"]
      )
  )
  fun getInstance(): SkinManager = this

  private var refContext: WeakReference<Context>? = null
  private var skinPrefs: SkinPreference? = null

  private val context: Context?
      get() = refContext?.get()

  /**
   * Initialize.
   *
   * <p>Do invoke this method on your custom [Application]'s onCreate method.</p>
   *
   * @param context context.
   *
   * @see android.app.Application
   */
  @JvmStatic
  fun init(context: Context) {
    val appContext = context.applicationContext
    resourcesTool.setContext(appContext)
    refContext = WeakReference(appContext)

    restoreSkinInfo(context)
    editManager.defaultEditor.clearCompatItems()
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
  @JvmStatic
  val resourcesManager = ResourcesManager()

  @JvmStatic
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
  @JvmStatic
  fun edit(lifecycle: Lifecycle): SkinEditor {
    return editManager.getEditor(lifecycle)
  }

  /**
   * Register activity.
   *
   * Registered item will dynamically change skin when skin changed triggered.
   * Do invoke this method on [Activity.onCreate] and behind [Activity.setContentView].
   * Do invoke paired [unregister] method on [Activity.onDestroy].
   *
   * @param activity activity.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinManager.registerEdit(activity: Activity)"
      )
  )
  fun register(activity: Activity) {
    editManager.defaultEditor.addSkinTask(activity, ActivitySkinTask, forcePerform = true)
  }

  /**
   * Register activity.
   *
   * @param activity activity.
   * @return editor instance.
   */
  @JvmStatic
  fun register(activity: FragmentActivity) {
    val editor = editManager.getEditor(activity.lifecycle)
    editor.clearCompatItems()
    editor.addTask(activity, ActivitySkinTask)
  }

  /**
   * Register fragment.
   *
   * @param fragment fragment.
   * @param lifecycle custom lifecycle.
   * @return editor instance.
   */
  @JvmStatic
  fun register(fragment: Fragment) {
    val editor = editManager.getEditor(fragment.lifecycle)
    editor.clearCompatItems()
    editor.addTask(fragment, FragmentSkinTask)
  }

  /**
   * Unregister activity.
   *
   * @param activity activity.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinEditor.removeTask(compat: Any)"
      )
  )
  fun unregister(activity: Activity) {
    editManager.defaultEditor.removeTask(activity)
  }

  /**
   * Unregister fragment.
   *
   * @param fragment fragment.
   */
  @JvmStatic
  @Deprecated(
      "This method has not effect for current version"
  )
  fun unregister(fragment: Fragment) {
    // do nothing.
  }

  /**
   * Register skin task.
   *
   * Use this method for custom view.
   *
   * @param compat compat.
   * @param skinTask skin task.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinEditor.addTask(compat: T, task: SkinTask<T>)"
      )
  )
  fun<T> registerSkinTask(compat: T, skinTask: SkinTask<T>) {
    editManager.defaultEditor.addTask(compat, skinTask)
  }

  /**
   * Register skin task and perform skin change right now.
   *
   * @param compat compat.
   * @param skinTask skin task.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinEditor.addAndPerformTask(compat: T, task: SkinTask<T>)"
      )
  )
  fun<T> registerAndPerformSkinTask(compat: T, skinTask: SkinTask<T>) {
    editManager.defaultEditor.addSkinTask(compat, skinTask, forcePerform = true)
  }

  /**
   * Unregister skin task.
   *
   * @param compat compat.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinEditor.removeTask(compat: Any)"
      )
  )
  fun unregisterSkinTask(compat: Any) {
    editManager.defaultEditor.removeTask(compat)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> skin info :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Skin suffix.
   */
  @JvmStatic
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
   * Use [null] to restore default skin, and [suffix] otherwise.
   */
  @JvmStatic
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
  @JvmStatic
  @JvmOverloads
  fun changeSkin(path: String?, packageName: String?, suffix: String? = null, callback: SkinChangeCallback? = null) {
    val skinCallback = callback ?: SkinChangeCallback.DEFAULT_CALLBACK
    skinCallback.onStart()

    if (path == null || packageName == null || !isValidPluginParams(path, packageName)) {
      skinCallback.onError("invalid plugin path or package")
      return
    }

    object: AsyncTask<Void, Void, Boolean>() {
      override fun doInBackground(vararg params: Void?): Boolean {
        return try {
          loadPlugin(path, packageName, suffix)
          true
        } catch (e: Exception) {
          e.printStackTrace()
          false
        }
      }

      override fun onPostExecute(result: Boolean?) {
        if (result != true) {
          skinCallback.onError("load plugin occur error")
          return
        }

        try {
          notifyChangedListeners()
          skinCallback.onComplete()
        } catch (e: Exception) {
          e.printStackTrace()
          skinCallback.onError(e.message ?: "")
        }
      }
    }.execute()
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
  @JvmStatic
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
  @JvmStatic
  fun injectSkin(view: View) {
    val skinId = this.skinId
    val skinViews = SkinAttrSupport.getSkinViews(view, skinId)

    skinViews.forEach {
      it.apply(skinId)
    }
  }

  /**
   * Sticky inject skin.
   *
   * Perform skin change even if target view is outside view tree.
   * Specific for case which view is hold to be reuse.
   *
   * @param view view.
   */
  @JvmStatic
  @Deprecated(
      "Use skin editor as replace",
      ReplaceWith(
          "SkinEditor.stickyInjectSkin(view: View)"
      )
  )
  fun stickyInjectSkin(view: View) {
    editManager.defaultEditor.stickyInjectSkin(view)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}