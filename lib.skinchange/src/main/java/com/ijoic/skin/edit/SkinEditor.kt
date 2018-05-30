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
import com.ijoic.skin.view.SkinTask

/**
 * Skin editor.
 *
 * @author verstsiu on 2018/5/23.
 * @version 2.0
 */
interface SkinEditor {
  /**
   * Add task.
   *
   * @param compat compat.
   * @param task task.
   */
  fun<T> addTask(compat: T, task: SkinTask<T>)

  /**
   * Add sticky task.
   *
   * <p>This method will perform skin change even if lifecycle is stopped.</p>
   * <p>Make sure you really need to do skin change at that case.</p>
   *
   * @param compat compat.
   * @param task task.
   */
  fun<T> addStickyTask(compat: T, task: SkinTask<T>)

  /**
   * Remove task.
   *
   * @param compat compat.
   */
  fun removeTask(compat: Any)

  /**
   * Inject skin.
   *
   * @param view target view.
   */
  fun injectSkin(view: View) {
    SkinManager.injectSkin(view)
  }

  /**
   * Sticky inject skin.
   *
   * @param view target view.
   */
  fun stickyInjectSkin(view: View)
}