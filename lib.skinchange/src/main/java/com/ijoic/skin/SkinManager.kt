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
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.ijoic.ktx.util.getOrCreate
import com.ijoic.ktx.util.weak.weak
import com.ijoic.skin.callback.SkinChangeCallback
import com.ijoic.skin.edit.SkinEditor

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

  internal var context: Context? by weak()
  private val currentManager = ChildSkinManager()

  /**
   * Initialize.
   *
   * <p>Do invoke this method on your custom Application's onCreate method.</p>
   *
   * @param context context.
   *
   * @see android.app.Application
   */
  @JvmStatic
  fun init(context: Context) {
    this.context = context
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Resources manager.
   */
  @JvmStatic
  internal val resourcesManager: ResourcesManager
      get() = currentManager.resourcesManager

  @JvmStatic
  val resourcesTool: ResourcesTool
      get() = currentManager.resourcesTool

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> child manager :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val childMap by lazy { mutableMapOf<String, ChildSkinManager>() }

  /**
   * Returns child skin manager.
   *
   * @param tag manager tag.
   */
  fun getChildManager(tag: String): ChildSkinManager {
    return childMap.getOrCreate(tag) { ChildSkinManager(tag) }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> child manager :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Returns skin editor for expected lifecycle.
   *
   * @param lifecycle lifecycle.
   */
  @JvmStatic
  fun edit(lifecycle: Lifecycle): SkinEditor {
    return currentManager.edit(lifecycle)
  }

  /**
   * Register activity.
   *
   * @param activity activity.
   * @return editor instance.
   */
  @JvmStatic
  fun register(activity: FragmentActivity) {
    currentManager.register(activity)
  }

  /**
   * Register fragment.
   *
   * @param fragment fragment.
   * @return editor instance.
   */
  @JvmStatic
  fun register(fragment: Fragment) {
    currentManager.register(fragment)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> register/unregister skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> skin info :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Skin suffix.
   */
  @JvmStatic
  val skinSuffix: String?
    get() = currentManager.skinSuffix

  /**
   * Skin id.
   */
  internal val skinId: String?
    get() = currentManager.skinId

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> skin info :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> change skin :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Change skin.
   *
   * Use <code>null</code> to restore default skin, and [suffix] otherwise.
   */
  @JvmStatic
  fun changeSkin(suffix: String?) {
    currentManager.changeSkin(suffix)
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
    currentManager.changeSkin(path, packageName, suffix, callback)
  }

  /**
   * Remove all skin.
   */
  @JvmStatic
  fun removeAnySkin() {
    currentManager.removeAnySkin()
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
    currentManager.injectSkin(view)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> inject skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}