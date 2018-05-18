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
import android.view.View

/**
 * Skin manager.
 *
 * <p>Use [View.getTag]/[View.setTag] to toggle application skin.</p>
 * <pre>Usage:
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
 * </pre>
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
object SkinManager {
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
    // TODO
  }

  /**
   * Register activity.
   *
   * <p>Registered activity will dynamically change skin when skin changed triggered.</p>
   *
   * @param activity activity.
   */
  @JvmStatic
  fun register(activity: Activity) {
    // TODO
  }

  /**
   * Unregister activity.
   *
   * @param activity activity.
   */
  @JvmStatic
  fun unregister(activity: Activity) {
    // TODO
  }

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
   * <p>Use [null] to restore default skin, and [suffix] otherwise.</p>
   */
  @JvmStatic
  fun changeSkin(suffix: String?) {
    // TODO
    skinSuffix = suffix
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> change skin :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}