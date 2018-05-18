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

  private val innerPrefs: SharedPreferences = context.getSharedPreferences(SkinConfig.SKIN_PREF_NAME, Context.MODE_PRIVATE)

  /**
   * 皮肤插件路径
   */
  var pluginPath: String?
    get() = innerPrefs.getString(KEY_PLUGIN_PATH, null)
    set(pluginPath) = innerPrefs.edit()
        .putString(KEY_PLUGIN_PATH, pluginPath)
        .apply()

  /**
   * 皮肤插件包名称
   */
  var pluginPackageName: String?
    get() = innerPrefs.getString(KEY_PLUGIN_PACKAGE, null)
    set(packageName) = innerPrefs.edit()
        .putString(KEY_PLUGIN_PACKAGE, packageName)
        .apply()

  /**
   * 皮肤插件后缀
   */
  var pluginSuffix: String?
    get() = innerPrefs.getString(KEY_PLUGIN_SUFFIX, null)
    set(pluginSuffix) = innerPrefs.edit()
        .putString(KEY_PLUGIN_SUFFIX, pluginSuffix)
        .apply()

  /**
   * 清空首选项数据
   */
  fun clear() {
    innerPrefs
        .edit()
        .clear()
        .apply()
  }

  companion object {
    private const val KEY_PLUGIN_PATH = "pluginPath"
    private const val KEY_PLUGIN_PACKAGE = "pluginPackage"
    private const val KEY_PLUGIN_SUFFIX = "pluginSuffix"
  }

}
