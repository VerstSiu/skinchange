/*
 *
 *  Copyright(c) 2020 VerstSiu
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
package com.ijoic.skin.extend.type.base

import android.graphics.drawable.Drawable
import android.view.View
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.attr.SkinAttrType

/**
 * Drawable attr type
 *
 * @author verstsiu created at 2020-10-26 21:11
 */
abstract class DrawableAttrType<T: Any>(
  private val viewType: Class<T>
) : SkinAttrType {

  override fun prepareResource(rm: ResourcesManager, resName: String): Any? {
    return rm.getDrawableByName(resName)
  }

  override fun apply(view: View, resource: Any) {
    if (!viewType.isInstance(view) || resource !is Drawable) {
      return
    }
    val castView = viewType.cast(view)!!
    applyDrawable(castView, resource)
  }

  protected abstract fun applyDrawable(view: T, drawable: Drawable)
}