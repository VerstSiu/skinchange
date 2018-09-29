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
package rd.ijoic.skin

import com.ijoic.ktx.content.preference.PrefsChild
import com.ijoic.ktx.content.preference.bindBoolean
import com.ijoic.ktx.content.preference.bindOptionalString

/**
 * Skin config.
 *
 * @author verstsiu on 2018/9/28
 * @version 1.1
 */
class SkinConfig: PrefsChild() {
  /**
   * Skin suffix.
   */
  var suffix by bindOptionalString("suffix")
    internal set

  /**
   * Plugin enabled status.
   */
  var pluginEnabled by bindBoolean("plugin_enabled")
    internal set

  /**
   * Plugin apk file path.
   */
  var pluginPath by bindOptionalString("plugin_path")
    internal set

  /**
   * Plugin name.
   */
  var pluginName by bindOptionalString("plugin_name")
    internal set

  /**
   * Plugin package name.
   */
  var pluginPackage by bindOptionalString("plugin_package")
    internal set

  /**
   * Plugin skin suffix.
   */
  var pluginSuffix by bindOptionalString("plugin_suffix")
    internal set

  /**
   * Skin id.
   *
   * <p>Generated field. Do cache this value when you use it as a second time.</p>
   */
  val skinId: String
    get() = when(pluginEnabled) {
      true -> "${pluginPackage.orEmpty()}:${pluginSuffix.orEmpty()}"
      false -> suffix.orEmpty()
    }
}