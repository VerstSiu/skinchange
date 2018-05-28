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
package com.ijoic.skinchange.test.base.fragment.pager

import android.support.v4.app.Fragment
import com.ijoic.skinchange.test.base.fragment.simple.RecyclerFragment
import com.ijoic.skinchange.test.base.fragment.simple.SimpleFragment

/**
 * Simple pager fragment.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
class SimplePagerFragment: AbstractPagerFragment() {

  override fun createFragments() = arrayOf<Fragment>(
      SimpleFragment(),
      SimpleFragment(),
      RecyclerFragment(),
      RecyclerFragment()
  )

  override fun createTabTitles() = arrayOf(
      "S1",
      "S2",
      "R1",
      "R2"
  )
}