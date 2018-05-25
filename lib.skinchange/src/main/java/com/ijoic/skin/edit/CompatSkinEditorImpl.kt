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
package com.ijoic.skin.edit

import android.view.View
import com.ijoic.skin.SkinManager
import com.ijoic.skin.view.KeepViewSkinTask
import com.ijoic.skin.view.SkinCompat
import com.ijoic.skin.view.SkinTask

/**
 * Compat skin editor impl.
 *
 * @author verstsiu on 2018/5/23.
 * @version 2.0
 */
internal class CompatSkinEditorImpl: CompatSkinEditor {

  private val compatItems by lazy { ArrayList<SkinCompat<*>>() }

  override fun <T> addTask(compat: T, task: SkinTask<T>) {
    compatItems.add(SkinCompat(compat, task))
  }

  override fun <T> addAndPerformTask(compat: T, task: SkinTask<T>) {
    val skinCompat = SkinCompat(compat, task)
    skinCompat.skinInit = true
    skinCompat.skinId = SkinManager.skinId

    compatItems.add(skinCompat)
    task.performSkinChange(compat)
  }

  override fun stickyInjectSkin(view: View) {
    addTask(view, KeepViewSkinTask)
    injectSkin(view)
  }

  override fun getCompatItems(): List<SkinCompat<*>>? {
    return compatItems
  }

  override fun removeTask(compat: Any) {
    val removedItems = compatItems.filter { it.compat == compat }
    compatItems.removeAll(removedItems)
  }

  override fun trimCompatItems() {
    val removedItems = compatItems.filter { it.compat == null }
    compatItems.removeAll(removedItems)
  }

  override fun clearCompatItems() {
    compatItems.clear()
  }
}