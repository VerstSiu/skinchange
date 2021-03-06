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

import androidx.fragment.app.Fragment
import com.ijoic.skin.ChildSkinManager

/**
 * 碎片换肤任务
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
internal object FragmentSkinTask: SkinTask<Fragment> {

  override fun performSkinChange(manager: ChildSkinManager, compat: Fragment) {
    val contentView = compat.view ?: return
    manager.injectSkin(contentView)
  }

}
