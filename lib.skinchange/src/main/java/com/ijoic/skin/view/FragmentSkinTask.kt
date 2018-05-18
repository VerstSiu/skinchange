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

import android.support.v4.app.Fragment
import com.ijoic.skin.SkinManager

/**
 * 碎片换肤任务
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
object FragmentSkinTask: SkinTask<Fragment> {

  override fun performSkinChange(compat: Fragment) {
    val contentView = compat.view ?: return
    SkinManager.injectSkin(contentView)
  }

}
