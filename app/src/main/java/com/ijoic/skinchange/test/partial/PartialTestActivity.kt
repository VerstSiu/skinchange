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
package com.ijoic.skinchange.test.partial

import com.ijoic.skinchange.test.base.constants.SkinChild
import com.ijoic.skinchange.test.base.constants.SkinSuffix
import com.ijoic.skinchange.test.base.fragment.simple.ChildSimpleFragment
import com.ijoic.skinchange.test.base.wrap.skin.WrapChildSkinActivity

/**
 * Partial test activity.
 *
 * @author verstsiu on 2018/6/25.
 * @version 2.0
 */
class PartialTestActivity: WrapChildSkinActivity() {

  init {
    expectedSkinSuffix = SkinSuffix.GREEN
  }

  override val skinTag: String = SkinChild.PARTIAL

  override fun createWrapFragmentInstance() = ChildSimpleFragment()

}