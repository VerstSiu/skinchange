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
package com.ijoic.skinchange.test.base.fragment.cache

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.frame_pager.instantlazy.InstantLazyFragment
import com.ijoic.skin.SkinManager
import com.ijoic.skinchange.R

/**
 * Color list fragment.
 *
 * @author verstsiu created at 2020-10-27 11:33
 */
class ColorListFragment: InstantLazyFragment() {

  override fun onCreateInstantView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_cache_color_list, container, false)
  }

  override fun onInitInstantView(savedInstanceState: Bundle?) {
    SkinManager.register(this)
  }
}