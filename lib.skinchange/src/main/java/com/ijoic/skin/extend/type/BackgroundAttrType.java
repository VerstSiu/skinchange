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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.ijoic.skin.ResourcesManager;
import com.ijoic.skin.SkinManager;
import com.ijoic.skin.attr.SkinAttrType;
import com.ijoic.skin.constant.SkinConfig;

/**
 * 背景属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
public class BackgroundAttrType implements SkinAttrType {

  @Override
  public void apply(@NonNull View view, @NonNull String resName) {
    ResourcesManager rm = SkinManager.getInstance().getResourcesManager();
    Drawable d = rm.getDrawableByName(resName);

    if (d != null) {
      hackSetBackground(view, d);
    } else {
      try {
        int color = rm.getColor(resName);
        view.setBackgroundColor(color);
      } catch (Resources.NotFoundException e) {
        Log.i(SkinConfig.TAG, "resource color not found [" + resName + "].");
      }
    }
  }

  private void hackSetBackground(@NonNull View view, @NonNull Drawable background) {
    // FIX: view padding lost, after set background(use background inset padding instead).
    int paddingLeft = view.getPaddingLeft();
    int paddingTop = view.getPaddingTop();
    int paddingRight = view.getPaddingRight();
    int paddingBottom = view.getPaddingBottom();

    boolean viewPaddingExist = paddingLeft != 0 || paddingTop != 0 || paddingRight != 0 || paddingBottom != 0;

    if (Build.VERSION.SDK_INT >= 16) {
      setBackgroundV16(view, background);
    } else {
      setBackgroundDefault(view, background);
    }

    if (viewPaddingExist) {
      view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
  }

  @SuppressLint("Deprecation")
  private static void setBackgroundDefault(@NonNull View view, @NonNull Drawable background) {
    view.setBackgroundDrawable(background);
  }

  @TargetApi(16)
  private static void setBackgroundV16(@NonNull View view, @NonNull Drawable background) {
    view.setBackground(background);
  }

}
