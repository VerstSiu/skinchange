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

/**
 *
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */
internal class SkinInfo {
  /**
   * Skin id.
   */
  internal var skinId: String? = null

  /**
   * Skin init status.
   */
  internal var skinInit = false

  /**
   * Skin empty status.
   */
  internal val isEmpty: Boolean
      get() = items?.isEmpty ?: true

  /**
   * Skin items.
   */
  internal var items: SkinItemMap? = null

  /**
   * Returns cached or new created skin items.
   */
  internal fun getOrCreateItems(): SkinItemMap {
    val oldItems = items

    if (oldItems != null) {
      return oldItems
    }
    val newItems = SkinItemMap()
    items = newItems
    return newItems
  }
}