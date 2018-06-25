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
import com.ijoic.skin.view.SkinCompat
import com.ijoic.skin.view.SkinTask

/**
 * Compat skin editor.
 *
 * @author verstsiu on 2018/5/23.
 * @version 2.0
 */
internal interface CompatSkinEditor: SkinEditor {

  /**
   * Add skin task.
   *
   * @param compat compat.
   * @param task task.
   * @param sticky sticky status.
   * @param forcePerform force perform status.
   */
  fun<T> addSkinTask(compat: T, task: SkinTask<T>, sticky: Boolean = false, forcePerform: Boolean = false)

  /**
   * Returns compat items.
   */
  fun getCompatItems(): List<SkinCompat<*>>

  /**
   * Returns sticky compat items.
   */
  fun getStickyCompatItems(): List<SkinCompat<*>>

  /**
   * Trim compat items.
   */
  fun trimCompatItems()

  /**
   * Clear compat items.
   */
  fun clearCompatItems()

  /**
   * Returns sticky compat item contain status.
   */
  fun containsStickyCompatItem(): Boolean = false

  companion object {
    /**
     * Blank editor.
     */
    internal val BLANK = object: CompatSkinEditor {
      override fun <T> addTask(compat: T, task: SkinTask<T>) {
        // do nothing.
      }

      override fun <T> addStickyTask(compat: T, task: SkinTask<T>) {
        // do nothing.
      }

      override fun <T> addSkinTask(compat: T, task: SkinTask<T>, sticky: Boolean, forcePerform: Boolean) {
        // do nothing.
      }

      override fun injectSkin(view: View) {
        // do nothing.
      }

      override fun stickyInjectSkin(view: View) {
        // do nothing.
      }

      override fun getCompatItems(): List<SkinCompat<*>> {
        return emptyList()
      }

      override fun getStickyCompatItems(): List<SkinCompat<*>> {
        return emptyList()
      }

      override fun removeTask(compat: Any) {
        // do nothing.
      }

      override fun trimCompatItems() {
        // do nothing.
      }

      override fun clearCompatItems() {
        // do nothing.
      }
    }
  }
}