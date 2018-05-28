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

import java.lang.ref.WeakReference

/**
 * 皮肤组件
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
internal class SkinCompat<T>(compat: T, private val skinTask: SkinTask<T>?) {

  private val refCompat = WeakReference(compat)

  internal var skinInit = false
  internal var skinId: String? = null

  /**
   * Compat.
   */
  internal val compat: T?
      get() = refCompat.get()

  /**
   * 判断皮肤组件是否为空
   *
   * @return 判断结果
   */
  internal val isEmpty: Boolean
    get() = refCompat.get() == null || skinTask == null

  /**
   * 执行换肤
   */
  internal fun performSkinChange() {
    val compat = refCompat.get() ?: return
    val task = this.skinTask ?: return

    task.performSkinChange(compat)
  }

  override fun equals(other: Any?): Boolean {
    if (other == null || other !is SkinCompat<*>) {
      return false
    }
    val compat = refCompat.get()
    val otherCompat = other.refCompat.get()

    return compat == null && otherCompat == null || compat != null && compat == otherCompat
  }

  override fun hashCode(): Int {
    var result = skinTask?.hashCode() ?: 0
    result = 31 * result + refCompat.hashCode()
    return result
  }

}
