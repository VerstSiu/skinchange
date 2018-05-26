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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.frame_pager.instant.InstantFragment
import com.ijoic.skin.SkinManager
import com.ijoic.skinchange.R
import com.ijoic.skinchange.util.ValueBox
import kotlinx.android.synthetic.main.frg_base_add_child_group.*

/**
 * Add child group fragment.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
class AddChildGroupFragment: InstantFragment() {

  private val skinBox = ValueBox(null, "red")

  override fun onCreateInstantView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_base_add_child_group, container, false)
  }

  override fun onInitInstantView(savedInstanceState: Bundle?) {
    SkinManager.register(this)
    skinBox.setCurrentValue(SkinManager.skinSuffix)

    add_view_button.setOnClickListener {
      val inflater = LayoutInflater.from(context)
      val child = inflater.inflate(R.layout.item_simple_hello_world, linear_parent, false)
      SkinManager.injectSkin(child)
      linear_parent.addView(child)
    }
  }
}