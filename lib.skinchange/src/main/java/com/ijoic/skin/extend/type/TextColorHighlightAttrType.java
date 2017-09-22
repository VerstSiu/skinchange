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

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ijoic.skin.ResourcesManager;
import com.ijoic.skin.SkinManager;
import com.ijoic.skin.attr.SkinAttrType;

/**
 * 高亮文字颜色属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.5
 */
public class TextColorHighlightAttrType implements SkinAttrType {
  @Override
  public void apply(@NonNull View view, @NonNull String resName) {
    if (!(view instanceof TextView)) {
      return;
    }
    ResourcesManager rm = SkinManager.getInstance().getResourcesManager();

    try {
      int highlightColor = rm.getColor(resName);
      ((TextView) view).setHighlightColor(highlightColor);

    } catch (Resources.NotFoundException e) {
      e.printStackTrace();
    }
  }
}
