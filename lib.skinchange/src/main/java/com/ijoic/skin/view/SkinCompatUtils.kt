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

/**
 * 皮肤组件工具
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
internal object SkinCompatUtils {

  /**
   * 在容器列表中，添加容器
   *
   * @param compatItems 容器列表
   * @param compat 容器
   */
  fun addCompat(compatItems: MutableList<SkinCompat<*>>, compat: SkinCompat<*>): Boolean {
    if (!containsItem(compatItems, compat)) {
      compatItems.add(compat)
      return true
    }
    return false
  }

  /**
   * 在组件列表中，移除皮肤组件
   *
   * @param compatItems 组件列表
   * @param compat 要移除的皮肤组件
   */
  fun removeCompat(compatItems: MutableList<SkinCompat<*>>, compat: SkinCompat<*>): SkinCompat<*>? {
    val compatItem = findCompat(compatItems, compat)

    if (compatItem != null) {
      compatItems.remove(compatItem)
    }
    return compatItem
  }

  /**
   * 修整组件列表
   *
   *
   * 把无效的皮肤组件去掉
   *
   * @param compatItems 组件列表
   * @param removeCache 移除缓存
   */
  fun trim(compatItems: MutableList<SkinCompat<*>>, removeCache: MutableList<SkinCompat<*>>) {
    removeCache.clear()

    for (compatItem in compatItems) {
      if (compatItem.isEmpty) {
        removeCache.add(compatItem)
      }
    }
    compatItems.removeAll(removeCache)
  }

  /**
   * 查找皮肤组件
   *
   * @param compatItems 组件列表
   * @param compat 查找对象
   * @return 查找结果
   */
  private fun findCompat(compatItems: List<SkinCompat<*>>, compat: SkinCompat<*>): SkinCompat<*>? {
    for (matchItem in compatItems) {
      if (matchItem == compat) {
        return matchItem
      }
    }
    return null
  }

  /**
   * 判断皮肤组件是否在组件列表中
   *
   * @param compatItems 组件列表
   * @param compat 皮肤组件
   * @return 判断结果
   */
  private fun containsItem(compatItems: List<SkinCompat<*>>, compat: SkinCompat<*>): Boolean {
    return findCompat(compatItems, compat) != null
  }

}
