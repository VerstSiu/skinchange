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

package com.ijoic.skin.view

import android.view.View

import com.ijoic.skin.SkinManager

/**
 * Keep view skin task.
 *
 *
 * Used for views which were hold to reuse, but outside of view tree.
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.12
 */
internal object KeepViewSkinTask: StateSkinTask<View> {

  override fun performSkinChange(compat: View) {
    if (compat.parent != null) {
      return
    }
    SkinManager.injectSkin(compat)
  }

}
