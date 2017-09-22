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

package com.ijoic.skin.view;

import android.support.annotation.NonNull;

/**
 * 皮肤任务
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.3
 *
 * @param <T> 视图类型
 */
public interface SkinTask<T> {

  /**
   * 执行皮肤替换
   *
   * @param compat 组件
   */
  void performSkinChange(@NonNull T compat);

}
