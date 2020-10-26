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

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import com.ijoic.skin.extend.type.base.DrawableOrColorAttrType

/**
 * 背景属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
internal object BackgroundAttrType : DrawableOrColorAttrType<View>(View::class.java) {

  override fun applyDrawable(view: View, drawable: Drawable) {
    hackSetBackground(view, drawable)
  }

  override fun applyColor(view: View, color: Int) {
    view.setBackgroundColor(color)
  }

  private fun hackSetBackground(view: View, background: Drawable) {
    // FIX: view padding lost, after set background(use background inset padding instead).
    val paddingLeft = view.paddingLeft
    val paddingTop = view.paddingTop
    val paddingRight = view.paddingRight
    val paddingBottom = view.paddingBottom

    val viewPaddingExist = paddingLeft != 0 || paddingTop != 0 || paddingRight != 0 || paddingBottom != 0

    ViewCompat.setBackground(view, background)

    if (viewPaddingExist) {
      view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }
  }
}
