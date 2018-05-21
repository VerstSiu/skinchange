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
package com.ijoic.skinchange.test.dynamic

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.skin.SkinManager
import com.ijoic.skinchange.R
import com.ijoic.skinchange.util.ValueBox
import kotlinx.android.synthetic.main.act_dynamic_recycler.*

/**
 * recycler dynamic test.
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */
class RecyclerDynamicTest: AppCompatActivity() {

  private val skinBox = ValueBox(null, "red")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_dynamic_recycler)
    SkinManager.register(this)
    skinBox.setCurrentValue(SkinManager.skinSuffix)

    val adapter = TestAdapter(this)
    recycler_parent.apply {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(this@RecyclerDynamicTest, LinearLayoutManager.VERTICAL, false)
    }

    add_view_button.setOnClickListener {
      val position = ++adapter.displayCount
      adapter.notifyItemInserted(position)
    }
    toggle_skin_button.setOnClickListener { SkinManager.changeSkin(skinBox.toggle()) }
  }

  override fun onDestroy() {
    super.onDestroy()
    SkinManager.unregister(this)
  }

  private class TestAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    var displayCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      val itemView = inflater.inflate(R.layout.item_simple_hello_world, parent, false)
      SkinManager.stickyInjectSkin(itemView)
      return SimpleHolder(itemView)
    }

    override fun getItemCount() = displayCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      // do nothing.
    }

  }

  private class SimpleHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}