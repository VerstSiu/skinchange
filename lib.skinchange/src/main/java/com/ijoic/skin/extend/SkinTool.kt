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

import android.util.Log
import android.view.View
import androidx.annotation.AnyRes
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.SkinManager
import com.ijoic.skin.constant.SkinConfig
import com.ijoic.skin.getSkinInfo

/**
 * 皮肤工具
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
object SkinTool {

  /* -- fill tag :begin -- */

  /**
   * 填充皮肤TAG
   *
   * @param skinTag skin tag.
   * @param view 视图
   * @param resId 资源ID
   * @param type 属性类型
   */
  @JvmStatic
  @JvmOverloads
  fun fillTag(skinTag: String? = null, view: View, @AnyRes resId: Int, type: String) {
    val context = view.context

    if (context == null) {
      Log.i(SkinConfig.TAG, "invalid skin view [$view], context not found")
      return
    }
    val resName = context.resources.getResourceEntryName(resId) ?: return
    fillTag(skinTag, view, resName, type)
  }

  /**
   * 填充皮肤TAG
   *
   * @param skinTag skin tag.
   * @param view 视图
   * @param resName 资源名称
   * @param type 属性类型
   */
  @JvmStatic
  fun fillTag(skinTag: String? = null, view: View, resName: String, type: String) {
    val attrType = AttrTypeFactory.obtainAttrType(type) ?: return
    val info = view.getSkinInfo()
    val item = info.getOrCreateItems().insertOrCached(type)

    item.apply {
      this.resName = resName
      this.attr = attrType
    }
    val resource = attrType.runCatching { prepareResource(getResManager(skinTag), resName) }.getOrNull()
    if (resource != null) {
      attrType.apply(view, resource)
    }
  }

  /**
   * 填充皮肤TAG
   *
   * @param skinTag skin tag.
   * @param view 视图
   * @param module module.
   * @param resName 资源名称
   * @param type 属性类型
   */
  @JvmStatic
  fun fillTag(skinTag: String? = null, view: View, module: String, resName: String, type: String) {
    val attrType = AttrTypeFactory.obtainAttrType(module, type) ?: return
    val info = view.getSkinInfo()
    val item = info.getOrCreateItems().insertOrCached(type)

    item.apply {
      this.resName = resName
      this.attr = attrType
    }
    val resource = attrType.runCatching { prepareResource(getResManager(skinTag), resName) }.getOrNull()
    if (resource != null) {
      attrType.apply(view, resource)
    }
  }

  /* -- fill tag :end -- */

  /* -- remove tag :begin -- */

  /**
   * 移除皮肤TAG
   *
   * @param view 视图
   * @param type 属性类型
   *
   * @see AttrTypes
   */
  @JvmStatic
  fun removeTag(view: View, type: String) {
    val context = view.context

    if (context == null) {
      Log.i(SkinConfig.TAG, "invalid skin view [$view], context not found")
      return
    }
    val info = view.getSkinInfo()
    info.getOrCreateItems().remove(type)
  }

  /* -- remove tag :end -- */

  private fun getResManager(skinTag: String?): ResourcesManager {
    return when(skinTag) {
      null -> SkinManager.resourcesManager
      else -> SkinManager.getChildManager(skinTag).resourcesManager
    }
  }

}
