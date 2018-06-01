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

import com.ijoic.skin.view.SkinTask

/**
 * Skin editor extension.
 *
 * @author verstsiu on 2018/6/1.
 * @version 2.0
 */

/**
 * Replace task.
 *
 * @param compat compat.
 * @param task task.
 */
fun<T> SkinEditor.replaceTask(compat: T, task: SkinTask<T>) {
  if (compat != null) {
    removeTask(compat)
  }
  addTask(compat, task)
}

/**
 * Replace sticky task.
 *
 * @param compat compat.
 * @param task task.
 */
fun<T> SkinEditor.replaceStickyTask(compat: T, task: SkinTask<T>) {
  if (compat != null) {
    removeTask(compat)
  }
  addStickyTask(compat, task)
}