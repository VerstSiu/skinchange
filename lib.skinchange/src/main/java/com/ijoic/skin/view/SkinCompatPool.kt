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

package com.ijoic.skin.view

import android.support.v4.util.ArrayMap
import java.util.*

/**
 * 皮肤组件对象池
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
class SkinCompatPool {

  private val compatItemsAll: MutableList<SkinCompat<*>> = ArrayList()
  private val compatItemsMap: ArrayMap<String, MutableList<SkinCompat<*>>> = ArrayMap()

  private val removeCache: MutableList<SkinCompat<*>> = ArrayList()
  private val removeCacheAll: MutableList<SkinCompat<*>> = ArrayList()

  /**
   * 添加皮肤组件
   *
   * @param tag 皮肤组件TAG
   * @param compat 皮肤组件
   */
  fun add(tag: String, compat: SkinCompat<*>) {
    var compatItems: MutableList<SkinCompat<*>>? = compatItemsMap[tag]

    if (compatItems == null) {
      compatItems = ArrayList()
      compatItemsMap[tag] = compatItems
    }
    if (SkinCompatUtils.addCompat(compatItems, compat)) {
      compatItemsAll.add(compat)
    }
  }

  /**
   * 移除皮肤组件
   *
   * @param tag 皮肤组件TAG
   * @param compat 皮肤组件
   */
  fun remove(tag: String, compat: SkinCompat<*>) {
    val compatItems = compatItemsMap[tag] ?: return

    val removedItem = SkinCompatUtils.removeCompat(compatItems, compat)

    if (removedItem != null) {
      compatItemsAll.remove(removedItem)
    }
  }

  /**
   * 获取所有皮肤组件
   *
   * @return 所有皮肤组件
   */
  fun getCompatItemsAll(): List<SkinCompat<*>> {
    return compatItemsAll
  }

  /**
   * 清除无效皮肤组件
   */
  fun trim() {
    removeCacheAll.clear()

    var compatItems: List<SkinCompat<*>>?

    var i = 0
    val size = compatItemsMap.size
    while (i < size) {
      compatItems = compatItemsMap.valueAt(i)

      if (compatItems == null) {
        ++i
        continue
      }
      SkinCompatUtils.trim(compatItems, removeCache)
      removeCacheAll.addAll(removeCache)
      ++i
    }
    compatItemsAll.removeAll(removeCacheAll)
  }

}
