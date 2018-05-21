/*
 *
 *  Copyright(c) 2017 VerstSiu
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

import android.content.Context
import android.content.SharedPreferences

import com.ijoic.skin.constant.SkinConfig

/**
 * 皮肤首选项
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
internal class SkinPreference(context: Context) {

  internal val innerPrefs: SharedPreferences = context.getSharedPreferences(SkinConfig.SKIN_PREF_NAME, Context.MODE_PRIVATE)

  /**
   * Plugin enabled.
   */
  internal var pluginEnabled: Boolean by bindBoolean("plugin_enabled")

  /**
   * Plugin path.
   */
  internal var pluginPath: String? by bindString("plugin_path")

  /**
   * Plugin package.
   */
  internal var pluginPackage: String? by bindString("plugin_package")

  /**
   * Plugin suffix.
   */
  internal var pluginSuffix: String? by bindString("plugin_suffix")

  /**
   * Normal skin suffix.
   */
  internal var suffix: String? by bindString("pluginSuffix")

  /**
   * 清空首选项数据
   */
  fun clearPluginInfo() {
    pluginEnabled = false
    pluginPath = null
    pluginPackage = null
    pluginSuffix = null
  }

}
