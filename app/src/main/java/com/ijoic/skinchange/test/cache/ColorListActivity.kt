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
package com.ijoic.skinchange.test.cache

import com.ijoic.skinchange.test.base.constants.SkinSuffix
import com.ijoic.skinchange.test.base.fragment.cache.ColorListFragment
import com.ijoic.skinchange.test.base.wrap.skin.WrapSkinActivity

/**
 * Color list activity.
 *
 * @author verstsiu created at 2020-10-27 11:32
 */
class ColorListActivity: WrapSkinActivity() {

  override var expectedSkinSuffix = SkinSuffix.GREEN

  override fun createWrapFragmentInstance() = ColorListFragment()

}