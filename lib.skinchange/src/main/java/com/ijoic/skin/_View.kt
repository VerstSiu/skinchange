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
internal fun View.getSkinItemMap(): SkinItemMap? {
  val oldMap = getTag(R.id.ijc_skinchange_map_id)

  if (oldMap != null && oldMap is SkinItemMap) {
    return oldMap
  }
  val tag = this.tag?.toString() ?: return null
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

/**
 * Returns cached or new created skin item map.
 */
internal fun View.getOrCreateSkinItemMap(): SkinItemMap {
  val oldMap = getSkinItemMap()

  if (oldMap != null) {
    return oldMap
  }
  val map = SkinItemMap()
  setTag(R.id.ijc_skinchange_map_id, map)
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