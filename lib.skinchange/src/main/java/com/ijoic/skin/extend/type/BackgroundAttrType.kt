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

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.attr.SkinAttrType
import com.ijoic.skin.constant.SkinConfig

/**
 * 背景属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
internal object BackgroundAttrType : SkinAttrType {

  override fun apply(rm: ResourcesManager, view: View, resName: String) {
    val d = rm.getDrawableByName(resName)

    if (d != null) {
      hackSetBackground(view, d)
    } else {
      try {
        val color = rm.getColor(resName)
        view.setBackgroundColor(color)
      } catch (e: Resources.NotFoundException) {
        Log.i(SkinConfig.TAG, "resource color not found [$resName].")
      }

    }
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
