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

package com.ijoic.skin.attr

import android.view.View
import android.view.ViewGroup
import com.ijoic.skin.getSkinItemMap
import java.util.*

/**
 * 皮肤属性支持
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
internal object SkinAttrSupport {

  /**
   * 获取皮肤视图列表
   *
   * @param rootView 根视图
   * @return 皮肤视图列表
   */
  internal fun getSkinViews(rootView: View): List<SkinView> {
    val skinViews = ArrayList<SkinView>()
    addSkinViews(rootView, skinViews)
    return skinViews
  }

  private fun addSkinViews(view: View, skinViews: MutableList<SkinView>) {
    val skinView = getSkinView(view)

    if (skinView != null) {
      skinViews.add(skinView)
    }

    if (view is ViewGroup) {

      var i = 0
      val size = view.childCount
      while (i < size) {
        addSkinViews(view.getChildAt(i), skinViews)
        ++i
      }
    }
  }

  private fun getSkinView(view: View): SkinView? {
    val itemMap = view.getSkinItemMap()

    if (itemMap != null) {
      return SkinView(view, itemMap)
    }
    return null
  }

}
