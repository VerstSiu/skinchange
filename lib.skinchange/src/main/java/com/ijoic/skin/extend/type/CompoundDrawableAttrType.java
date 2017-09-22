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

package com.ijoic.skin.extend.type;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ijoic.skin.ResourcesManager;
import com.ijoic.skin.SkinManager;
import com.ijoic.skin.attr.SkinAttrType;

/**
 * 边缘图片属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.2
 */
abstract class CompoundDrawableAttrType implements SkinAttrType {

  static final int INDEX_LEFT = 0;
  static final int INDEX_TOP = 1;
  static final int INDEX_RIGHT = 2;
  static final int INDEX_BOTTOM = 3;

  @Override
  public void apply(@NonNull View view, @NonNull String resName) {
    if (!(view instanceof TextView)) {
      return;
    }
    ResourcesManager rm = SkinManager.getInstance().getResourcesManager();
    Drawable d = rm.getDrawableByName(resName);

    if (d != null) {
      TextView castView = (TextView) view;
      Drawable[] drawables = castView.getCompoundDrawables();

      d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
      drawables[getCompoundIndex()] = d;

      castView.setCompoundDrawables(
        drawables[INDEX_LEFT],
        drawables[INDEX_TOP],
        drawables[INDEX_RIGHT],
        drawables[INDEX_BOTTOM]
      );
    }
  }

  /**
   * 获取边缘图片索引位置
   *
   * @return 边缘图片索引位置
   *
   * @see #INDEX_LEFT
   * @see #INDEX_TOP
   * @see #INDEX_RIGHT
   * @see #INDEX_BOTTOM
   */
  protected abstract int getCompoundIndex();

}
