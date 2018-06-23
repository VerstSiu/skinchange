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

import android.os.Build
import android.view.View
import android.widget.Spinner
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.attr.SkinAttrType

/**
 * 下拉背景属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.8
 */
internal object PopupBackgroundAttrType : SkinAttrType {

  override fun apply(rm: ResourcesManager, view: View, resName: String) {
    if (view !is Spinner) {
      return
    }
    val d = rm.getDrawableByName(resName)

    if (d != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      view.setPopupBackgroundDrawable(d)
    }
  }
}
