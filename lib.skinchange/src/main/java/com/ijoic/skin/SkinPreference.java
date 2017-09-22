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

package com.ijoic.skin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.ijoic.skin.constant.SkinConfig;

/**
 * 皮肤首选项
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
class SkinPreference {

  private final @NonNull SharedPreferences innerPrefs;

  private static final String KEY_PLUGIN_PATH = "pluginPath";
  private static final String KEY_PLUGIN_PACKAGE = "pluginPackage";
  private static final String KEY_PLUGIN_SUFFIX = "pluginSuffix";

  /**
   * 构造函数
   *
   * @param context 上下文
   */
  SkinPreference(@NonNull Context context) {
    innerPrefs = context.getSharedPreferences(SkinConfig.SKIN_PREF_NAME, Context.MODE_PRIVATE);
  }

  /**
   * 获取皮肤插件路径
   *
   * @return 皮肤插件路径
   */
  String getPluginPath() {
    return innerPrefs.getString(KEY_PLUGIN_PATH, null);
  }

  /**
   * 设置皮肤插件路径
   *
   * @param pluginPath 皮肤插件路径
   */
  void setPluginPath(String pluginPath) {
    innerPrefs.edit()
      .putString(KEY_PLUGIN_PATH, pluginPath)
      .apply();
  }

  /**
   * 获取皮肤包名称
   *
   * @return 皮肤插件包名称
   */
  String getPluginPackageName() {
    return innerPrefs.getString(KEY_PLUGIN_PACKAGE, null);
  }

  /**
   * 设置皮肤插件包名称
   *
   * @param packageName 皮肤插件包名称
   */
  void setPluginPackageName(String packageName) {
    innerPrefs.edit()
      .putString(KEY_PLUGIN_PACKAGE, packageName)
      .apply();
  }

  /**
   * 获取皮肤插件后缀
   *
   * @return 皮肤插件后缀
   */
  String getPluginSuffix() {
    return innerPrefs.getString(KEY_PLUGIN_SUFFIX, null);
  }

  /**
   * 设置皮肤插件后缀
   *
   * @param pluginSuffix 皮肤插件后缀
   */
  void setPluginSuffix(String pluginSuffix) {
    innerPrefs.edit()
      .putString(KEY_PLUGIN_SUFFIX, pluginSuffix)
      .apply();
  }

  /**
   * 清空首选项数据
   */
  void clear() {
    innerPrefs
      .edit()
      .clear()
      .apply();
  }

}
