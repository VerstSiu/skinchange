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
package com.ijoic.skinchange.test.base.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.skinchange.R
import kotlinx.android.synthetic.main.frg_base_pager.*

/**
 * Pager fragment.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
abstract class AbstractPagerFragment: Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_base_pager, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val adapter = ContentAdapter(childFragmentManager, createFragments(), createTabTitles())
    content_pager.adapter = adapter
    sliding_tabs.setupWithViewPager(content_pager)
    sliding_tabs.tabMode = TabLayout.MODE_FIXED
  }

  /**
   * Returns created fragment instances.
   */
  protected abstract fun createFragments(): Array<Fragment>

  /**
   * Returns created tab titles.
   */
  protected abstract fun createTabTitles(): Array<String>

  private class ContentAdapter(
      fm: FragmentManager,
      private val fragments: Array<Fragment>,
      private val titles: Array<String>): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
      return fragments.getOrElse(position, { Fragment() })
    }

    override fun getCount(): Int {
      return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return titles.getOrElse(position, { "Tab $position" })
    }
  }
}