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
package com.ijoic.skinchange.test.base.wrap.skin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ijoic.skin.ChildSkinManager
import com.ijoic.skin.SkinManager
import com.ijoic.skinchange.R
import com.ijoic.skinchange.test.base.constants.SkinSuffix
import kotlinx.android.synthetic.main.act_base_wrap_skin.*

/**
 * Wrap skin activity.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
abstract class WrapChildSkinActivity: AppCompatActivity() {

  private val model: SkinViewModel by viewModels()
  protected var expectedSkinSuffix = SkinSuffix.RED

  private val skinManager: ChildSkinManager
      get() = SkinManager.getChildManager(skinTag)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_base_wrap_skin)
    initPageFrame()

    model.skinSuffix.observe(this, {
      if (it == expectedSkinSuffix) {
        action_toggle_skin.setImageResource(R.drawable.ic_base_skin_none)
        action_toggle_skin.setOnClickListener {
          skinManager.changeSkin(SkinSuffix.none)
          model.skinSuffix.value = SkinSuffix.none
        }
      } else {
        action_toggle_skin.setImageResource(R.drawable.ic_base_skin_night)
        action_toggle_skin.setOnClickListener {
          skinManager.changeSkin(expectedSkinSuffix)
          model.skinSuffix.value = expectedSkinSuffix
        }
      }
    })

    model.skinSuffix.value = skinManager.skinSuffix
  }

  private fun initPageFrame() {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.wrap_frame, createWrapFragmentInstance())
        .commit()
  }

  protected abstract fun createWrapFragmentInstance(): Fragment

  protected abstract val skinTag: String

}