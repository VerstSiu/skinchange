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
package com.ijoic.skinchange.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijoic.skinchange.R
import com.ijoic.skinchange.test.dynamic.LinearDynamicTest
import com.ijoic.skinchange.test.dynamic.RecyclerDynamicTest
import com.ijoic.skinchange.test.pager.PagerTestActivity
import com.ijoic.skinchange.util.routeTo
import kotlinx.android.synthetic.main.act_dynamic_test.*

/**
 * Dynamic test activity.
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */
class DynamicTestActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_dynamic_test)

    case_dynamic_linear.setOnClickListener { routeTo(LinearDynamicTest::class.java) }
    case_dynamic_recycler.setOnClickListener { routeTo(RecyclerDynamicTest::class.java) }
    case_dynamic_pager.setOnClickListener { routeTo(PagerTestActivity::class.java) }
  }

}