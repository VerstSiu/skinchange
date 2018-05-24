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
package com.ijoic.skinchange.test.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ijoic.skin.SkinManager
import com.ijoic.skinchange.R
import com.ijoic.skinchange.util.ValueBox
import kotlinx.android.synthetic.main.act_simple_register.*

/**
 * Simple test activity.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
class RegisterActivityTest: AppCompatActivity() {

  private val skinBox = ValueBox(null, "red")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_simple_register)
    SkinManager.registerEdit(this)
    skinBox.setCurrentValue(SkinManager.skinSuffix)
    toggle_skin_button.setOnClickListener { SkinManager.changeSkin(skinBox.toggle()) }
  }

}