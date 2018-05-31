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
 * Skin compat factory.
 *
 * @author verstsiu on 2018/5/31.
 * @version 2.0
 */
internal interface SkinCompatFactory {
  /**
   * Create skin compat instance.
   *
   * @param compat compat.
   * @param task skin task.
   */
  fun<T> createSkinCompat(compat: T, task: SkinTask<T>): SkinCompat<T>

  companion object {
    /**
     * Weak skin compat factory.
     */
    internal val weak = object: SkinCompatFactory {
      override fun <T> createSkinCompat(compat: T, task: SkinTask<T>) = WeakSkinCompat(compat, task)
    }

    /**
     * Strong skin compat factory.
     */
    internal val strong = object: SkinCompatFactory {
      override fun <T> createSkinCompat(compat: T, task: SkinTask<T>) = StrongSkinCompat(compat, task)
    }
  }
}