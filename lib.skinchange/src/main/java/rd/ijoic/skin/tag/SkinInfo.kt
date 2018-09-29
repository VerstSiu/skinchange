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
package rd.ijoic.skin.tag

/**
 * Skin info.
 *
 * @author verstsiu on 2018/9/29
 * @version 1.1
 */
internal class SkinInfo {
  /**
   * Items.
   */
  var items: List<SkinItem>? = null

  /**
   * Skin style.
   */
  var style: String? = null

  /**
   * Skin group.
   */
  var group: String? = null

  /**
   * Parent skin style.
   */
  var parentStyle: String? = null

  /**
   * Parent skin group.
   */
  var parentGroup: String? = null

  /**
   * Display style.
   */
  val displayStyle: String?
    get() = style ?: parentStyle

  /**
   * Display group.
   */
  val displayGroup: String?
    get() = group ?: parentGroup
}