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

package com.ijoic.skin

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.Log
import com.ijoic.skin.constant.SkinConfig

/**
 * 资源工具
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.3
 */
class ResourcesTool internal constructor(private val resManager: ResourcesManager) {

  /**
   * 获取颜色
   *
   *
   * 返回皮肤中包含的颜色，若不存在，返回当前主题中的颜色
   *
   * @param resId 资源
   * @return 颜色
   */
  @ColorInt
  @Throws(Resources.NotFoundException::class)
  fun getColor(@ColorRes resId: Int): Int {
    val res = resManager.resources ?: throw Resources.NotFoundException()

    try {
      return getSkinColor(res, resId)
    } catch (e: Resources.NotFoundException) {
      e.printStackTrace()
    }

    return res.getColor(resId)
  }

  /**
   * 获取图片
   *
   *
   * 返回皮肤中包含的图片，若不存在，返回当前主题中的图片
   *
   * @param resId 资源ID
   * @return 图片
   */
  fun getDrawable(@DrawableRes resId: Int): Drawable? {
    val res = resManager.resources ?: throw Resources.NotFoundException()
    var d = getSkinDrawable(res, resId)

    if (d == null) {
      d = res.getDrawable(resId)
    }
    return d
  }

  /**
   * 获取颜色列表
   *
   *
   * 返回皮肤中包含的颜色列表，若不存在，返回当前主题中的颜色列表
   *
   * @param resId 资源ID
   * @return 颜色列表
   */
  fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
    val res = resManager.resources ?: throw Resources.NotFoundException()
    var colorStateList = getSkinColorStateList(res, resId)

    if (colorStateList == null) {
      colorStateList = res.getColorStateList(resId)
    }
    return colorStateList
  }

  /**
   * 获取字符串内容
   *
   *
   * 返回皮肤中包含的字符串内容，若不存在，返回当前主题中的字符串内容
   *
   * @param resId 资源ID
   * @return 字符串内容
   */
  fun getString(@StringRes resId: Int): String? {
    val res = resManager.resources ?: throw Resources.NotFoundException()
    var text = getSkinText(res, resId)

    if (text == null) {
      text = res.getString(resId)
    }
    return text
  }

  /**
   * 获取资源URI
   *
   * @param resId 资源ID
   * @return 资源URI
   */
  fun getResourcesUri(resId: Int): String {
    val res = resManager.resources ?: throw Resources.NotFoundException()
    val resName = res.getResourceEntryName(resId)
    val resType = res.getResourceTypeName(resId)
    val skinResId = resManager.getSkinResId(resName, resType)

    if (skinResId == 0) {
      Log.e(SkinConfig.TAG, "resource R." + resType + "." + resName + " not found in package [" + resManager.packageName + "]")
      return PREFIX_RES + res.getResourcePackageName(resId) + "/" + resId
    }
    return PREFIX_RES + resManager.packageName + "/" + skinResId
  }

  @ColorInt
  @Throws(Resources.NotFoundException::class)
  private fun getSkinColor(res: Resources, @ColorRes resId: Int): Int {
    val resName = res.getResourceEntryName(resId)
    val type = res.getResourceTypeName(resId)

    return resManager.getColor(resName, type)
  }

  private fun getSkinDrawable(res: Resources, @DrawableRes resId: Int): Drawable? {
    val resName = res.getResourceEntryName(resId)
    val type = res.getResourceTypeName(resId)

    return resManager.getDrawableByName(resName, type)
  }

  private fun getSkinColorStateList(res: Resources, @ColorRes resId: Int): ColorStateList? {
    val resName = res.getResourceEntryName(resId)
    val type = res.getResourceTypeName(resId)

    return resManager.getColorStateList(resName, type)
  }

  private fun getSkinText(res: Resources, @StringRes resId: Int): String? {
    val resName = res.getResourceEntryName(resId)
    val type = res.getResourceTypeName(resId)

    return resManager.getString(resName, type)
  }

  companion object {
    private const val PREFIX_RES = "res://"
  }

}
