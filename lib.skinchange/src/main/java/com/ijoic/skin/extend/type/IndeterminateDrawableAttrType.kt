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
import android.widget.ProgressBar
import com.ijoic.skin.extend.type.base.DrawableAttrType

/**
 * 不定进度图片属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.5
 */
internal object IndeterminateDrawableAttrType : DrawableAttrType<ProgressBar>(ProgressBar::class.java) {

  override fun applyDrawable(view: ProgressBar, drawable: Drawable) {
    view.indeterminateDrawable = drawable
  }

}
