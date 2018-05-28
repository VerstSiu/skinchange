/*
 *
 *  Copyright(c) 2018 VerstSiu
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
package com.ijoic.skin

import android.view.View
import com.ijoic.skin.constant.SkinConfig
import com.ijoic.skin.extend.AttrTypeFactory

/**
 * View extension.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */

/**
 * Returns skin item map.
 */
internal fun View.getSkinInfo(): SkinInfo {
  val oldInfo = getTag(R.id.ijc_skinchange_map_id)

  if (oldInfo != null && oldInfo is SkinInfo) {
    return oldInfo
  }
  val skinInfo = SkinInfo()
  skinInfo.items = getSkinItemMap(this.tag?.toString())

  setTag(R.id.ijc_skinchange_map_id, skinInfo)
  return skinInfo
}

/**
 * Returns skin item map.
 */
private fun getSkinItemMap(tag: String?): SkinItemMap? {
  if (tag == null || tag.isBlank()) {
    return null
  }
  var map: SkinItemMap? = null

  tag.forEachAttr { resName, resType ->
    val attrType = AttrTypeFactory.obtainAttrType(resType)

    if (attrType != null) {
      val itemMap = map ?: SkinItemMap()
      val item = itemMap.insertOrCached(resType)

      item.apply {
        this.resName = resName
        this.attr = attrType
      }
      map = itemMap
    }
  }
  return map
}

private fun String.forEachAttr(action: (String, String) -> Unit) {
  val segments = split("|")

  segments.forEach {
    if (it.startsWith(SkinConfig.SKIN_PREFIX)) {
      val parts = it.split(":")

      if (parts.size == 3) {
        val resName = parts[1]
        val resType = parts[2]

        if (!resName.isEmpty() && !resType.isEmpty()) {
          action.invoke(resName, resType)
        }
      }
    }
  }
}