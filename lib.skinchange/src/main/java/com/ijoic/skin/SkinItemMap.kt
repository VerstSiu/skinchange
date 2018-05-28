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

import android.support.v4.util.ArrayMap

/**
 * Skin item map.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
internal class SkinItemMap: ArrayMap<String, SkinItem>() {

  /**
   * Returns insert or cached skin item.
   *
   * @param resType res type.
   */
  fun insertOrCached(resType: String?): SkinItem {
    val oldItem = get(resType)

    if (oldItem != null) {
      return oldItem
    }
    val item = SkinItem.obtain()
    item.resType = resType
    put(resType, item)
    return item
  }

  override fun clear() {
    val items = if (values.isEmpty()) null else values.toList()
    super.clear()
    SkinItem.releaseItems(items)
  }

  override fun removeAt(index: Int): SkinItem? {
    val item =  super.removeAt(index)
    item?.let { SkinItem.release(it) }
    return item
  }

}