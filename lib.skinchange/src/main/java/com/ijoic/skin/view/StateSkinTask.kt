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
package com.ijoic.skin.view

/**
 * State skin task.
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */
interface StateSkinTask<in T>: SkinTask<T> {

  /**
   * Active status.
   */
  fun isActive() = true

  /**
   * Attach compat.
   *
   * @param compat compat.
   */
  fun onAttach(compat: T) {}

  /**
   * Detach compat.
   *
   * @param compat compat.
   */
  fun onDetach(compat: T) {}

  companion object {
    /**
     * Wrap task as state skin task.
     *
     * @param task task.
     */
    internal fun<T> wrap(task: SkinTask<T>): StateSkinTask<T> {
      return object: StateSkinTask<T> {
        override fun performSkinChange(compat: T) {
          task.performSkinChange(compat)
        }
      }
    }
  }
}