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
package com.ijoic.skin.extend

import androidx.collection.ArrayMap

/**
 * Attr prefix.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
class AttrPrefix {

  private var prefixAll: String? = null
  private var prefixItems: MutableMap<String, String>? = null

  /**
   * Returns attr prefix of expected res type.
   *
   * @param resType res type.
   */
  internal fun getPrefix(resType: String): String? {
    val items = prefixItems

    if (items != null) {
      val prefix = items[resType]

      if (prefix != null) {
        return prefix
      }
    }
    return prefixAll
  }

  /**
   * Fill all res types with expected prefix.
   *
   * @param prefix expected prefix.
   */
  fun all(prefix: String): AttrPrefix {
    prefixAll = prefix
    return this
  }

  /**
   * Fill res type with expected prefix.
   *
   * @param resType res type.
   * @param prefix expected prefix.
   */
  fun whenType(resType: String, prefix: String): AttrPrefix {
    val items = prefixItems ?: ArrayMap<String, String>()
    items[resType] = prefix
    prefixItems = items
    return this
  }

  companion object {
    /**
     * Blank attr prefix.
     */
    internal val blank = AttrPrefix()
  }
}