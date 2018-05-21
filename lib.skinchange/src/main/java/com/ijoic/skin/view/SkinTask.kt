/*
 *
 *  Copyright(c) 2017 VerstSiu
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
 * 换肤任务
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.3
 */
interface SkinTask<in T> {

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

  /**
   * 执行换肤
   *
   * @param compat 组件
   */
  fun performSkinChange(compat: T)

}
