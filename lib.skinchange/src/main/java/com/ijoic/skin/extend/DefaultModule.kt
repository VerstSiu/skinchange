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

import com.ijoic.skin.extend.type.*

/**
 * Default attr type module.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
object DefaultModule: AttrTypeModule("default") {
  override fun onInit() {

    // Config default attr types here.
    addAttrType(AttrTypes.BACKGROUND, BackgroundAttrType)
    addAttrType(AttrTypes.TEXT, TextAttrType)
    addAttrType(AttrTypes.TEXT_COLOR, TextColorAttrType)
    addAttrType(AttrTypes.TEXT_COLOR_HIGHLIGHT, TextColorHighlightAttrType)
    addAttrType(AttrTypes.TEXT_COLOR_HINT, TextColorHintAttrType)
    addAttrType(AttrTypes.TEXT_COLOR_LINK, TextColorLinkAttrType)
    addAttrType(AttrTypes.DRAWABLE_LEFT, DrawableLeftAttrType)
    addAttrType(AttrTypes.DRAWABLE_TOP, DrawableTopAttrType)
    addAttrType(AttrTypes.DRAWABLE_RIGHT, DrawableRightAttrType)
    addAttrType(AttrTypes.DRAWABLE_BOTTOM, DrawableBottomAttrType)
    addAttrType(AttrTypes.BUTTON, ButtonAttrType)
    addAttrType(AttrTypes.SRC, SrcAttrType)
    addAttrType(AttrTypes.DIVIDER, DividerAttrType)
    addAttrType(AttrTypes.LIST_SELECTOR, ListSelectorAttrType)
    addAttrType(AttrTypes.INDITERMINATE_DRAWABLE, IndeterminateDrawableAttrType)
    addAttrType(AttrTypes.PROGRESS_DRAWABLE, ProgressDrawableAttrType)
    addAttrType(AttrTypes.THUMB, ThumbAttrType)
    addAttrType(AttrTypes.POPUP_BACKGROUND, PopupBackgroundAttrType)
  }
}