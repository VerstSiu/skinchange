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

package com.ijoic.skin.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * 皮肤组件工具
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
class SkinCompatUtils {

  /**
   * 在容器列表中，添加容器
   *
   * @param compatItems 容器列表
   * @param compat 容器
   */
  static boolean addCompat(@NonNull List<SkinCompat> compatItems, @NonNull  SkinCompat compat) {
    if (!containsItem(compatItems, compat)) {
      compatItems.add(compat);
      return true;
    }
    return false;
  }

  /**
   * 在组件列表中，移除皮肤组件
   *
   * @param compatItems 组件列表
   * @param compat 要移除的皮肤组件
   */
  static @Nullable SkinCompat removeCompat(@NonNull List<SkinCompat> compatItems, @NonNull  SkinCompat compat) {
    SkinCompat compatItem = findCompat(compatItems, compat);

    if (compatItem != null) {
      compatItems.remove(compatItem);
    }
    return compatItem;
  }

  /**
   * 修整组件列表
   *
   * <p>把无效的皮肤组件去掉</p>
   *
   * @param compatItems 组件列表
   * @param removeCache 移除缓存
   */
  static void trim(@NonNull List<SkinCompat> compatItems, @NonNull List<SkinCompat> removeCache) {
    removeCache.clear();

    for (SkinCompat compatItem : compatItems) {
      if (compatItem != null && compatItem.isEmpty()) {
        removeCache.add(compatItem);
      }
    }
    compatItems.removeAll(removeCache);
  }

  /**
   * 查找皮肤组件
   *
   * @param compatItems 组件列表
   * @param compat 查找对象
   * @return 查找结果
   */
  private @Nullable static SkinCompat findCompat(@NonNull List<SkinCompat> compatItems, @NonNull  SkinCompat compat) {
    for (SkinCompat matchItem : compatItems) {
      if (matchItem != null && matchItem.equals(compat)) {
        return matchItem;
      }
    }
    return null;
  }

  /**
   * 判断皮肤组件是否在组件列表中
   *
   * @param compatItems 组件列表
   * @param compat 皮肤组件
   * @return 判断结果
   */
  @org.jetbrains.annotations.Contract("_, null -> false")
  private static boolean containsItem(@NonNull List<SkinCompat> compatItems, @NonNull  SkinCompat compat) {
    return findCompat(compatItems, compat) != null;
  }

  private SkinCompatUtils() {}

}
