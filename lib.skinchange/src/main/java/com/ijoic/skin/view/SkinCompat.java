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
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 皮肤组件
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.4
 */
public class SkinCompat<T> {

  private @NonNull final WeakReference<T> refCompat;
  private @Nullable SkinTask<T> skinTask;

  /**
   * 构造函数
   *
   * @param compat 组件
   * @param skinTask 换肤任务
   */
  public SkinCompat(@NonNull T compat, @Nullable SkinTask<T> skinTask) {
    refCompat = new WeakReference<>(compat);
    this.skinTask = skinTask;
  }

  /**
   * 判断皮肤组件是否为空
   *
   * @return 判断结果
   */
  boolean isEmpty() {
    return refCompat.get() == null || skinTask == null;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null || !(other instanceof SkinCompat)) {
      return false;
    }
    T compat = refCompat.get();
    Object otherCompat = ((SkinCompat) other).refCompat.get();

    return (compat == null && otherCompat == null) || (compat != null && compat.equals(otherCompat));
  }

  /**
   * 执行换肤
   */
  public void performSkinChange() {
    T compat = refCompat.get();
    SkinTask<T> skinTask = this.skinTask;

    if (compat == null || skinTask == null) {
      return;
    }
    skinTask.performSkinChange(compat);
  }

}
