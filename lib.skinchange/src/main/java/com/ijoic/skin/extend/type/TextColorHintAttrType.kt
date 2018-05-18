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
 * 提示文字颜色属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.5
 */
object TextColorHintAttrType : SkinAttrType {
  override fun apply(view: View, resName: String) {
    if (view !is TextView) {
      return
    }
    val rm = SkinManager.resourcesManager
    val colorList = rm.getColorStateList(resName) ?: return

    view.setHintTextColor(colorList)
  }
}
