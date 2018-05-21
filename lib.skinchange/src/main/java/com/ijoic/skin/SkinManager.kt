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
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.view.View
import com.ijoic.skin.attr.SkinAttrSupport
import com.ijoic.skin.callback.SkinChangeCallback
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

    val prefs = createSkinPreference(context)
    skinSuffix = prefs.pluginSuffix
    containerPool.trim()

    if (!initPlugin(prefs.pluginPath, prefs.pluginPackageName, prefs.pluginSuffix)) {
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
      skinPrefs?.clear()
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
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> plugin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val containerPool = SkinCompatPool()

  private const val TAG_ACTIVITY = "activity"
  private const val TAG_FRAGMENT = "fragment"
  private const val TAG_VIEW = "view"
  private const val TAG_SKIN_TASK = "skintask"

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
  fun register(activity: Activity) {
    register(TAG_ACTIVITY, SkinCompat(activity, ActivitySkinTask))
  }

  /**
   * Register fragment.
   *
   * Registered item will dynamically change skin when skin changed triggered.
   * Do invoke this method on [Fragment.onActivityCreated].
   * Do invoke paired [unregister] method on [Fragment.onDestroy].
   *
   * @param fragment fragment.
   */
  @JvmStatic
  fun register(fragment: Fragment) {
    register(TAG_FRAGMENT, SkinCompat(fragment, FragmentSkinTask))
  }

  private fun register(view: View) {
    register(TAG_VIEW, SkinCompat(view, KeepViewSkinTask))
  }

  /**
   * Unregister activity.
   *
   * @param activity activity.
   */
  @JvmStatic
  fun unregister(activity: Activity) {
    unregister(TAG_ACTIVITY, SkinCompat(activity, null))
  }

  /**
   * Unregister fragment.
   *
   * @param fragment fragment.
   */
  @JvmStatic
  fun unregister(fragment: Fragment) {
    unregister(TAG_FRAGMENT, SkinCompat(fragment, null))
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
  fun<T> registerSkinTask(compat: T, skinTask: SkinTask<T>) {
    register(TAG_SKIN_TASK, SkinCompat(compat, skinTask))
  }

  /**
   * Register skin task and perform skin change right now.
   *
   * @param compat compat.
   * @param skinTask skin task.
   */
  @JvmStatic
  fun<T> registerAndPerformSkinTask(compat: T, skinTask: SkinTask<T>) {
    skinTask.performSkinChange(compat)
    registerSkinTask(compat, skinTask)
  }

  /**
   * Unregister skin task.
   *
   * @param compat compat.
   */
  @JvmStatic
  fun<T> unregisterSkinTask(compat: T) {
    unregister(TAG_SKIN_TASK, SkinCompat(compat, null))
  }

  private fun register(tag: String, compat: SkinCompat<*>) {
    containerPool.add(tag, compat)
    compat.performSkinChange()
  }

  private fun unregister(tag: String, compat: SkinCompat<*>) {
    containerPool.remove(tag, compat)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> change skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Skin suffix.
   */
  @JvmStatic
  var skinSuffix: String? = null
    private set

  /**
   * Change skin.
   *
   * Use [null] to restore default skin, and [suffix] otherwise.
   */
  @JvmStatic
  fun changeSkin(suffix: String?) {
    clearPluginInfo() // clear before

    skinPrefs?.apply {
      pluginSuffix = suffix
      resetResourcesManager()
    }
    notifyChangedListeners()
    skinSuffix = suffix
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
          updatePluginInfo(path, packageName, suffix)
          notifyChangedListeners()
          skinCallback.onComplete()
        } catch (e: Exception) {
          e.printStackTrace()
          skinCallback.onError(e.message ?: "")
        }
      }
    }.execute()
  }

  private fun updatePluginInfo(path: String?, packageName: String?, suffix: String? = null) {
    val prefs = skinPrefs ?: throw IllegalArgumentException("skin preference not found")

    prefs.apply {
      pluginPath = path
      pluginPackageName = packageName
      pluginSuffix = suffix
    }
  }

  /**
   * Remove all skin.
   */
  @JvmStatic
  fun removeAnySkin() {
    clearPluginInfo()
    resetResourcesManager()
    notifyChangedListeners()
  }

  private fun clearPluginInfo() {
    skinPrefs?.clear()
  }

  private fun resetResourcesManager() {
    val context = this.context ?: return

    resourcesManager.apply {
      setResources(context.resources, false)
      setSkinInfo(context.packageName, skinPrefs?.pluginSuffix)
    }
  }

  private fun notifyChangedListeners() {
    val compatItems = containerPool.getCompatItemsAll()

    compatItems.forEach {
      it.performSkinChange()
    }
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
    val skinViews = SkinAttrSupport.getSkinViews(view)

    skinViews.forEach {
      it.apply()
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
  fun stickyInjectSkin(view: View) {
    register(view)
    injectSkin(view)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}