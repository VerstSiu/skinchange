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

import android.arch.lifecycle.Lifecycle
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
  private var lifecycle: Lifecycle? = null

  private val stickyItems by lazy { ArrayList<SkinCompat<*>>() }

  /**
   * Attach lifecycle.
   */
  internal fun attachLifecycle(lifecycle: Lifecycle) {
    this.lifecycle = lifecycle
  }

  /**
   * Detach lifecycle.
   */
  internal fun detachLifecycle() {
    this.lifecycle = null
  }

  override fun <T> addTask(compat: T, task: SkinTask<T>) {
    addSkinTask(compat, task)
  }

  override fun <T> addStickyTask(compat: T, task: SkinTask<T>) {
    addSkinTask(compat, task, sticky = true)
  }

  override fun <T> addSkinTask(compat: T, task: SkinTask<T>, sticky: Boolean, forcePerform: Boolean) {
    val lifecycle = this.lifecycle

    if (!forcePerform && (lifecycle == null || !lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))) {
      onAddTask(compat, task, sticky)
    } else {
      onAddAndPerformTask(compat, task, sticky)
    }
  }

  private fun <T> onAddTask(compat: T, task: SkinTask<T>, sticky: Boolean = false) {
    val skinCompat = SkinCompat(compat, task)
    compatItems.add(skinCompat)

    if (sticky) {
      stickyItems.add(SkinCompat(compat, task))
    }
  }

  private fun <T> onAddAndPerformTask(compat: T, task: SkinTask<T>, sticky: Boolean = false) {
    val skinCompat = SkinCompat(compat, task)
    skinCompat.skinInit = true
    skinCompat.skinId = SkinManager.skinId

    compatItems.add(skinCompat)

    if (sticky) {
      stickyItems.add(SkinCompat(compat, task))
    }
    task.performSkinChange(compat)
  }

  override fun stickyInjectSkin(view: View) {
    onAddTask(view, KeepViewSkinTask)
    injectSkin(view)
  }

  override fun getCompatItems(): List<SkinCompat<*>> {
    return compatItems
  }

  override fun getStickyCompatItems(): List<SkinCompat<*>> {
    return stickyItems
  }

  override fun removeTask(compat: Any) {
    val removedItems = compatItems.filter { it.compat == compat }
    compatItems.removeAll(removedItems)
  }

  override fun trimCompatItems() {
    trimCompatItems(compatItems)
    trimCompatItems(stickyItems)
  }

  private fun trimCompatItems(items: MutableList<SkinCompat<*>>) {
    if (items.isEmpty()) {
      return
    }
    val removedItems = items.filter { it.compat == null }
    items.removeAll(removedItems)
  }

  override fun clearCompatItems() {
    compatItems.clear()
    stickyItems.clear()
  }

  override fun containsStickyCompatItem(): Boolean {
    return stickyItems.isNotEmpty()
  }
}