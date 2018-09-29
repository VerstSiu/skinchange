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
package rd.ijoic.skin.tag

import android.view.View
import android.view.ViewGroup
import com.ijoic.skin.R
import rd.ijoic.skin.SkinPreference
import rd.ijoic.skin.attr.AttrFactory
import rd.ijoic.skin.res.ResFactory

private const val TYPE_SKIN = "skin"
private const val TYPE_STYLE = "style"
private const val TYPE_GROUP = "group"

/**
 * Returns skin info items.
 *
 * @param group parent group.
 * @param style parent style.
 */
internal fun View.getSkinInfo(style: String? = null, group: String? = null): SkinInfo {
  val cacheInfo = this.getTag(R.id.ijc_skinchange_map_id)

  if (cacheInfo != null && cacheInfo is SkinInfo) {
    cacheInfo.parentGroup = group
    cacheInfo.parentStyle = style
    return cacheInfo
  }
  val tag = this.tag?.toString()
  val info = SkinInfo()
  val segments = tag?.trim()?.split("|")

  if (segments != null) {
    val items = mutableListOf<SkinItem>()

    segments.forEach {
      val parts = it.split(":")
      val attrType = parts[0]

      when (attrType) {
        TYPE_SKIN -> {
          val resName = parts.getOrNull(1)
          val attrName = parts.getOrNull(2)

          if (resName != null && !resName.isEmpty() && attrName != null && !attrName.isEmpty()) {
            val attr = AttrFactory.getAttr(attrName)

            if (attr != null) {
              items.add(SkinItem(resName, attrName, attr))
            }
          }
        }
        TYPE_GROUP -> {
          val groupName = parts.getOrNull(1)

          if (groupName != null && !groupName.isEmpty()) {
            info.group = groupName
          }
        }
        TYPE_STYLE -> {
          val styleName = parts.getOrNull(1)

          if (styleName != null && !styleName.isEmpty()) {
            info.style = styleName
          }
        }
      }
    }

    info.items = items
  }

  info.parentGroup = group
  info.parentStyle = style
  setTag(R.id.ijc_skinchange_map_id, info)
  return info
}

/**
 * Apply skin.
 *
 * @param style parent style.
 * @param group parent group.
 */
internal fun View.applySkin(style: String? = null, group: String? = null) {
  applySkin(SkinPreference.getInstance(context), style, group)
}

/**
 * Apply skin.
 *
 * @param prefs skin preferences.
 * @param style parent style.
 * @param group parent group.
 */
internal fun View.applySkin(prefs: SkinPreference, style: String? = null, group: String? = null) {
  val info = getSkinInfo(style, group)
  val items = info.items

  if (items != null) {
    val resManager = ResFactory.getResManager(context, info.getFinalStyle(prefs))

    items.forEach { it.attr.apply(resManager, this, it.resName) }
  }

  if (this is ViewGroup) {
    val childCount = this.childCount

    if (childCount > 0) {
      val displayStyle = info.displayStyle
      val displayGroup = info.displayGroup

      for (i in 0 until childCount) {
        getChildAt(i)?.applySkin(prefs, displayStyle, displayGroup)
      }
    }
  }
}