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

package com.ijoic.skin.attr

import android.view.View
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.SkinInfo
import java.lang.ref.WeakReference

/**
 * 皮肤视图
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
internal class SkinView internal constructor(view: View, private val info: SkinInfo) {

  private val viewRef: WeakReference<View> = WeakReference(view)

  internal fun prepareResource(skinId: String?, rm: ResourcesManager) {
    info.apply {
      this.skinId = skinId
      items?.forEachValue {
        val resName = it.resName
        val attr = it.attr

        if (resName != null && attr != null) {
          it.resource = attr.runCatching { prepareResource(rm, resName) }.getOrNull()
        }
      }
    }
  }

  /**
   * 应用皮肤
   */
  internal fun applyResource() {
    val view = viewRef.get() ?: return

    info.apply {
      this.skinId = skinId
      items?.forEachValue {
        val resource = it.resource
        val attr = it.attr

        if (resource != null && attr != null) {
          it.resource = null
          attr.apply(view, resource)
        }
      }
    }
  }

}
