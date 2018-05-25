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
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.ijoic.skin.SkinManager
import com.ijoic.skin.view.SkinCompat
import java.lang.ref.WeakReference

/**
 * Skin editor manager.
 *
 * @author verstsiu on 2018/5/24.
 * @version 2.0
 */
internal class SkinEditorManager {
  /**
   * Default editor.
   */
  internal val defaultEditor: CompatSkinEditor by lazy { CompatSkinEditorImpl() }

  private val editorItems by lazy { ArrayList<Pair<WeakReference<Lifecycle>, CompatSkinEditor>>() }

  /**
   * Returns editor instance.
   *
   * @param lifecycle lifecycle.
   */
  internal fun getEditor(lifecycle: Lifecycle): CompatSkinEditor {
    if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
      return CompatSkinEditor.BLANK
    }
    val itemPair = editorItems.firstOrNull { it.first.get() == lifecycle }

    if (itemPair != null) {
      return itemPair.second
    }
    val editor = CompatSkinEditorImpl()
    val pair = Pair(WeakReference(lifecycle), editor)
    val observer = object: LifecycleObserver {
      @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
      fun onResume() {
        val skinId = SkinManager.skinId

        getDisplayCompatItems(skinId, editor).forEach {
          performSkinChange(SkinManager.skinId, it)
        }
      }

      @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      fun onDestroy() {
        editor.clearCompatItems()
        editorItems.remove(pair)
      }
    }

    lifecycle.addObserver(observer)
    editorItems.add(pair)
    return editor
  }

  /**
   * Perform skin change.
   *
   * @param skinId skin id.
   */
  internal fun performSkinChange(skinId: String?) {
    trimEditorItems()
    val compatItems = getDisplayCompatItems(skinId)

    compatItems.forEach {
      performSkinChange(skinId, it)
    }
  }

  private fun performSkinChange(skinId: String?, compat: SkinCompat<*>) {
    compat.apply {
      this.skinInit = true
      this.skinId = skinId

      performSkinChange()
    }
  }

  private fun trimEditorItems() {
    val removedItems = editorItems.filter { isStateEmptyOrDestroyed(it.first.get()) }
    editorItems.removeAll(removedItems)
  }

  private fun getDisplayCompatItems(skinId: String?): List<SkinCompat<*>> {
    val items = ArrayList<SkinCompat<*>>()
    insertDisplayCompatItems(items, defaultEditor, skinId)

    editorItems.filter { isStateResumed(it.first.get()) }.forEach {
      insertDisplayCompatItems(items, it.second, skinId)
    }
    return items
  }

  private fun getDisplayCompatItems(skinId: String?, editor: CompatSkinEditor): List<SkinCompat<*>> {
    val items = ArrayList<SkinCompat<*>>()
    insertDisplayCompatItems(items, editor, skinId)
    return items
  }

  private fun insertDisplayCompatItems(items: MutableList<SkinCompat<*>>, editor: CompatSkinEditor, skinId: String?) {
    val compatItems = editor.getCompatItems() ?: return
    editor.trimCompatItems()
    items.addAll(compatItems.filter { !it.isEmpty && (!it.skinInit || it.skinId != skinId) })
  }

  private fun isStateResumed(lifecycle: Lifecycle?): Boolean {
    return lifecycle != null && lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
  }

  private fun isStateEmptyOrDestroyed(lifecycle: Lifecycle?): Boolean {
    return lifecycle == null || lifecycle.currentState == Lifecycle.State.DESTROYED
  }
}