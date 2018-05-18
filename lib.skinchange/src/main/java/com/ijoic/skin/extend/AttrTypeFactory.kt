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

import android.support.v4.util.ArrayMap

import com.ijoic.skin.attr.SkinAttrType
import com.ijoic.skin.extend.type.BackgroundAttrType
import com.ijoic.skin.extend.type.ButtonAttrType
import com.ijoic.skin.extend.type.DividerAttrType
import com.ijoic.skin.extend.type.DrawableBottomAttrType
import com.ijoic.skin.extend.type.DrawableLeftAttrType
import com.ijoic.skin.extend.type.DrawableRightAttrType
import com.ijoic.skin.extend.type.DrawableTopAttrType
import com.ijoic.skin.extend.type.IndeterminateDrawableAttrType
import com.ijoic.skin.extend.type.ListSelectorAttrType
import com.ijoic.skin.extend.type.PopupBackgroundAttrType
import com.ijoic.skin.extend.type.ProgressDrawableAttrType
import com.ijoic.skin.extend.type.SrcAttrType
import com.ijoic.skin.extend.type.TextAttrType
import com.ijoic.skin.extend.type.TextColorAttrType
import com.ijoic.skin.extend.type.TextColorHighlightAttrType
import com.ijoic.skin.extend.type.TextColorHintAttrType
import com.ijoic.skin.extend.type.TextColorLinkAttrType
import com.ijoic.skin.extend.type.ThumbAttrType

/**
 * 皮肤属性工厂
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
object AttrTypeFactory {

  private val attrTypesMap = ArrayMap<String, SkinAttrType>()

  /**
   * 注册属性类型
   *
   * @param typeName 类型名称
   * @param typeClazz 类型类
   */
  @JvmStatic
  @Deprecated(
      "Set attr type instance directly",
      ReplaceWith(
          "AttrTypeFactory.register(typeName,type)",
          imports = [
            "com.ijoic.skin.extend.AttrTypeFactory",
            "com.ijoic.skin.attr.SkinAttrType"
          ]
      )
  )
  fun register(typeName: String, typeClazz: Class<out SkinAttrType>) {
    try {
      val attrType = typeClazz.newInstance()
      insertAttrType(typeName, attrType)

    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * 注册属性类型
   *
   * @param typeName 类型名称
   * @param type 类型实例
   */
  @JvmStatic
  fun register(typeName: String, type: SkinAttrType) {
    insertAttrType(typeName, type)
  }

  /**
   * 获取属性类型
   *
   * @param typeName 属性类型名称
   * @return 属性类型
   */
  @JvmStatic
  fun obtainAttrType(typeName: String): SkinAttrType? {
    return attrTypesMap[typeName]
  }

  private fun insertAttrType(typeName: String, type: SkinAttrType) {
    attrTypesMap[typeName] = type
  }

  init {

    // Config default attr types here.
    insertAttrType(AttrTypes.BACKGROUND, BackgroundAttrType)
    insertAttrType(AttrTypes.TEXT, TextAttrType)
    insertAttrType(AttrTypes.TEXT_COLOR, TextColorAttrType)
    insertAttrType(AttrTypes.TEXT_COLOR_HIGHLIGHT, TextColorHighlightAttrType)
    insertAttrType(AttrTypes.TEXT_COLOR_HINT, TextColorHintAttrType)
    insertAttrType(AttrTypes.TEXT_COLOR_LINK, TextColorLinkAttrType)
    insertAttrType(AttrTypes.DRAWABLE_LEFT, DrawableLeftAttrType)
    insertAttrType(AttrTypes.DRAWABLE_TOP, DrawableTopAttrType)
    insertAttrType(AttrTypes.DRAWABLE_RIGHT, DrawableRightAttrType)
    insertAttrType(AttrTypes.DRAWABLE_BOTTOM, DrawableBottomAttrType)
    insertAttrType(AttrTypes.BUTTON, ButtonAttrType)
    insertAttrType(AttrTypes.SRC, SrcAttrType)
    insertAttrType(AttrTypes.DIVIDER, DividerAttrType)
    insertAttrType(AttrTypes.LIST_SELECTOR, ListSelectorAttrType)
    insertAttrType(AttrTypes.INDITERMINATE_DRAWABLE, IndeterminateDrawableAttrType)
    insertAttrType(AttrTypes.PROGRESS_DRAWABLE, ProgressDrawableAttrType)
    insertAttrType(AttrTypes.THUMB, ThumbAttrType)
    insertAttrType(AttrTypes.POPUP_BACKGROUND, PopupBackgroundAttrType)
  }

}