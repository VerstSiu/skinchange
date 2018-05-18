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

package com.ijoic.skin.extend.type

import android.view.View
import android.widget.TextView
import com.ijoic.skin.SkinManager
import com.ijoic.skin.attr.SkinAttrType

/**
 * 边缘图片属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.2
 */
abstract class CompoundDrawableAttrType : SkinAttrType {

  /**
   * 获取边缘图片索引位置
   *
   * @return 边缘图片索引位置
   *
   * @see .INDEX_LEFT
   * @see .INDEX_TOP
   * @see .INDEX_RIGHT
   * @see .INDEX_BOTTOM
   */
  protected abstract val compoundIndex: Int

  override fun apply(view: View, resName: String) {
    if (view !is TextView) {
      return
    }
    val rm = SkinManager.resourcesManager
    val d = rm.getDrawableByName(resName)

    if (d != null) {
      val drawables = view.compoundDrawables

      d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
      drawables[compoundIndex] = d

      view.setCompoundDrawables(
          drawables[INDEX_LEFT],
          drawables[INDEX_TOP],
          drawables[INDEX_RIGHT],
          drawables[INDEX_BOTTOM]
      )
    }
  }

  companion object {
    const val INDEX_LEFT = 0
    const val INDEX_TOP = 1
    const val INDEX_RIGHT = 2
    const val INDEX_BOTTOM = 3
  }

}
