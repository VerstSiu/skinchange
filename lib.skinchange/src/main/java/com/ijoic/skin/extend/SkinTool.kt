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

package com.ijoic.skin.extend

import android.support.annotation.AnyRes
import android.util.Log
import android.view.View
import com.ijoic.skin.constant.SkinConfig
import com.ijoic.skin.getSkinInfo

/**
 * 皮肤工具
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
object SkinTool {

  /**
   * 填充皮肤TAG
   *
   * @param view 视图
   * @param resId 资源ID
   * @param type 属性类型
   */
  @JvmStatic
  fun fillTag(view: View, @AnyRes resId: Int, type: String) {
    val context = view.context

    if (context == null) {
      Log.i(SkinConfig.TAG, "invalid skin view [" + view.toString() + "], context not found")
      return
    }
    val resName = context.resources.getResourceEntryName(resId) ?: return
    fillTag(view, resName, type)
  }

  /**
   * 填充皮肤TAG
   *
   * @param view 视图
   * @param resName 资源名称
   * @param type 属性类型
   */
  @JvmStatic
  fun fillTag(view: View, resName: String, type: String) {
    val attrType = AttrTypeFactory.obtainAttrType(type) ?: return
    val info = view.getSkinInfo()
    val item = info.getOrCreateItems().insertOrCached(type)

    item.apply {
      this.resName = resName
      this.attr = attrType
    }
  }

  /**
   * 填充皮肤TAG
   *
   * @param view 视图
   * @param module module.
   * @param resName 资源名称
   * @param type 属性类型
   */
  @JvmStatic
  fun fillTag(view: View, module: String, resName: String, type: String) {
    val attrType = AttrTypeFactory.obtainAttrType(module, type) ?: return
    val info = view.getSkinInfo()
    val item = info.getOrCreateItems().insertOrCached(type)

    item.apply {
      this.resName = resName
      this.attr = attrType
    }
  }

}
