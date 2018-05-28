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
package com.ijoic.skinchange.test.base.fragment.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ijoic.frame_pager.instantlazy.InstantLazyFragment
import com.ijoic.skin.SkinManager
import com.ijoic.skin.view.SkinTask
import com.ijoic.skinchange.R
import kotlinx.android.synthetic.main.frg_base_simple.*

/**
 * Simple fragment.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
class SimpleFragment: InstantLazyFragment() {

  override fun onCreateInstantView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_base_simple, container, false)
  }

  override fun onInitInstantView(savedInstanceState: Bundle?) {
    SkinManager.register(this)
    initSkinTask()
  }

  private fun initSkinTask() {
    val skinEditor = SkinManager.edit(lifecycle)

    skinEditor.addTask(label_skin_task, object: SkinTask<TextView> {
      override fun performSkinChange(compat: TextView) {
        val resTool = SkinManager.resourcesTool

        compat.setBackgroundColor(resTool.getColor(R.color.base_simple_text_bg))
        compat.setTextColor(resTool.getColor(R.color.base_simple_text_color))
      }
    })
  }
}