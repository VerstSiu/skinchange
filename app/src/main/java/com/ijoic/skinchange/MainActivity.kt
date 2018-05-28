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
package com.ijoic.skinchange

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijoic.skinchange.test.SimpleTestActivity
import com.ijoic.skinchange.test.RecyclerTestActivity
import com.ijoic.skinchange.test.host.HostTestActivity
import com.ijoic.skinchange.test.pager.NestedPagerTestActivity
import com.ijoic.skinchange.test.pager.PagerTestActivity
import com.ijoic.skinchange.util.routeTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    case_simple_test.setOnClickListener { routeTo(SimpleTestActivity::class.java) }
    case_dynamic_recycler.setOnClickListener { routeTo(RecyclerTestActivity::class.java) }
    case_dynamic_pager.setOnClickListener { routeTo(PagerTestActivity::class.java) }
    case_dynamic_nested_pager.setOnClickListener { routeTo(NestedPagerTestActivity::class.java) }
    case_dynamic_host.setOnClickListener { routeTo(HostTestActivity::class.java) }
  }
}
