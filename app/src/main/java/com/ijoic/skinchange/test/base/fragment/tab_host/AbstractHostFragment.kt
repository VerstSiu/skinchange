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
package com.ijoic.skinchange.test.base.fragment.tab_host

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.frame_pager.instant.InstantFragment
import com.ijoic.skinchange.R
import kotlinx.android.synthetic.main.frg_base_tab_host.*

/**
 * Pager fragment.
 *
 * @author verstsiu on 2018/5/28.
 * @version 2.0
 */
abstract class AbstractHostFragment: InstantFragment() {

  override fun onCreateInstantView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_base_tab_host, container, false)
  }

  override fun onInitInstantView(savedInstanceState: Bundle?) {
    val context = this.context ?: return
    val tabTitles = createTabTitles()
    val fragmentTypes = createFragmentTypes()

    tabhost.apply {
      setup(context, childFragmentManager, R.id.content_frame)

      tabTitles.forEachIndexed { index, title ->
        val fragmentType = fragmentTypes.getOrNull(index) ?: Fragment::class.java
        addTab(newTabSpec(title).setIndicator(title), fragmentType, null)
      }
    }
  }

  /**
   * Returns created fragment types.
   */
  protected abstract fun createFragmentTypes(): Array<Class<*>>

  /**
   * Returns created tab titles.
   */
  protected abstract fun createTabTitles(): Array<String>

}