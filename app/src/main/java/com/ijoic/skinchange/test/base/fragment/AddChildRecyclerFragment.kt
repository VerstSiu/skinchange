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

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijoic.frame_pager.instant.InstantFragment
import com.ijoic.skin.SkinManager
import com.ijoic.skin.edit.SkinEditor
import com.ijoic.skinchange.R
import com.ijoic.skinchange.util.ValueBox
import kotlinx.android.synthetic.main.frg_base_add_child_recycler.*

/**
 * Add child recycler fragment.
 *
 * @author verstsiu on 2018/5/26.
 * @version 2.0
 */
class AddChildRecyclerFragment: InstantFragment() {

  private val skinBox = ValueBox(null, "red")

  override fun onCreateInstantView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.frg_base_add_child_recycler, container, false)
  }

  override fun onInitInstantView(savedInstanceState: Bundle?) {
    SkinManager.register(this)
    val context = this.context ?: return
    val skinEditor = SkinManager.edit(lifecycle)
    skinBox.setCurrentValue(SkinManager.skinSuffix)

    val adapter = TestAdapter(context, skinEditor)
    recycler_parent.apply {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    add_view_button.setOnClickListener {
      val position = ++adapter.displayCount
      adapter.notifyItemInserted(position)
    }
  }

  private class TestAdapter(context: Context, private val skinEditor: SkinEditor): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    var displayCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      val itemView = inflater.inflate(R.layout.item_simple_hello_world, parent, false)
      skinEditor.stickyInjectSkin(itemView)
      return SimpleHolder(itemView)
    }

    override fun getItemCount() = displayCount

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      // do nothing.
    }

  }

  private class SimpleHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}