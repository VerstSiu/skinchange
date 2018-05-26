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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
abstract class WrapSkinActivity: AppCompatActivity() {

  private var expectedSkinSuffix = SkinSuffix.RED

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_base_wrap_skin)
    SkinManager.register(this)
    initPageFrame()

    val model = ViewModelProviders.of(this).get(SkinViewModel::class.java)

    model.skinSuffix.observe(this, Observer {
      if (it == expectedSkinSuffix) {
        action_toggle_skin.setImageResource(R.drawable.ic_base_skin_none)
        action_toggle_skin.setOnClickListener {
          SkinManager.changeSkin(SkinSuffix.none)
          model.skinSuffix.value = SkinSuffix.none
        }
      } else {
        action_toggle_skin.setImageResource(R.drawable.ic_base_skin_night)
        action_toggle_skin.setOnClickListener {
          SkinManager.changeSkin(expectedSkinSuffix)
          model.skinSuffix.value = expectedSkinSuffix
        }
      }
    })

    model.skinSuffix.value = SkinManager.skinSuffix
  }

  private fun initPageFrame() {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.wrap_frame, createWrapFragmentInstance())
        .commit()
  }

  protected abstract fun createWrapFragmentInstance(): Fragment

}