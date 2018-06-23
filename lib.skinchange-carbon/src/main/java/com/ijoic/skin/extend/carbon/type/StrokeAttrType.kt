/*
 *
 *  Copyright(c) 2018 VerstSiu
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
package com.ijoic.skin.extend.carbon.type

import android.view.View
import carbon.view.StrokeView
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.attr.SkinAttrType

/**
 * Stroke attr type.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
internal class StrokeAttrType: SkinAttrType {
  override fun apply(rm: ResourcesManager, view: View, resName: String) {
    if (view !is StrokeView) {
      return
    }
    val castView: StrokeView = view
    val colorList = rm.getColorStateList(resName)

    if (colorList != null) {
      castView.stroke = colorList
    } else {
      try {
        val color = rm.getColor(resName)

        castView.setStroke(color)

      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}