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

package com.ijoic.skin.attr;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 皮肤视图
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
public class SkinView {

  private final @NonNull WeakReference<View> viewRef;
  private final @NonNull List<SkinAttr> attrs;

  /**
   * 构造函数
   *
   * @param view 视图
   * @param attrs 皮肤属性列表
   */
  SkinView(@NonNull View view, @NonNull List<SkinAttr> attrs) {
    viewRef = new WeakReference<>(view);
    this.attrs = attrs;
  }

  /**
   * 应用皮肤
   */
  public void apply() {
    View view = viewRef.get();

    if (view == null) {
      return;
    }
    for (SkinAttr attr : attrs) {
      if (attr != null) {
        attr.apply(view);
      }
    }
  }

}
