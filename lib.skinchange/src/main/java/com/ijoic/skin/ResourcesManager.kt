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
import java.lang.ref.WeakReference

/**
 * 资源管理器
 *
 * NOTE：
 * 1. 普通资源，使用Applcaiont的Resources引用，当Application结束后，引用失效
 * 2. 插件资源，使用pluginResources保持引用，在必要的时候，主动释放资源
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
class ResourcesManager internal constructor() {

  private var refResources: WeakReference<Resources>? = null

  /**
   * 获取资源包名称
   *
   * @return 资源包名称
   */
  internal var packageName: String = ""
    private set

  private var suffix: String = ""

  // 插件资源
  private var pluginResources: Resources? = null

  internal var resources: Resources?
    get() = refResources?.get()
    set(res) { res?.let { setResources(it, false) } }

  /**
   * 设置资源
   *
   * @param res 资源
   * @param isPluginResources 是否是插件资源
   */
  internal fun setResources(res: Resources, isPluginResources: Boolean) {
    refResources = WeakReference(res)
    pluginResources = if (isPluginResources) res else null
  }

  /**
   * 设置皮肤信息
   *
   * @param packageName 包名称
   * @param suffix 资源后缀
   */
  internal fun setSkinInfo(packageName: String, suffix: String?) {
    this.packageName = packageName
    setSuffix(suffix)
  }

  /**
   * 获取皮肤资源名称
   *
   * @param resName 资源名称
   * @return 皮肤资源名称
   */
  private fun getSkinResName(resName: String): String {
    return appendSuffix(resName)
  }

  /**
   * 获取皮肤资源ID
   *
   * @param resName 资源名称
   * @param resType 资源类型
   * @return 皮肤资源ID
   */
  @Throws(Resources.NotFoundException::class)
  internal fun getSkinResId(resName: String, resType: String): Int {
    val res = resources ?: throw Resources.NotFoundException()
    val query = getSkinResName(resName)
    return res.getIdentifier(query, resType, packageName)
  }

  /**
   * 设置资源后缀
   *
   * @param suffix 资源后缀
   */
  private fun setSuffix(suffix: String?) {
    this.suffix = if (suffix.isNullOrEmpty()) "" else "_$suffix"
  }

  /**
   * 根据资源名称获取Drawable
   *
   * @param resName 资源名称
   * @param type 资源类型
   * @return Drawable
   */
  internal fun getDrawableByName(resName: String, type: String): Drawable? {
    val res = resources ?: return null
    val query = getSkinResName(resName)
    val resId = res.getIdentifier(query, type, packageName)

    if (resId != 0) {
      try {
        return res.getDrawable(resId)
      } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
      }
    }
    return null
  }

  /**
   * 根据资源名称获取Drawable
   *
   * @param resName 资源名称
   * @return Drawable
   */
  fun getDrawableByName(resName: String): Drawable? {
    val res = resources ?: return null
    val query = getSkinResName(resName)
    var resId = res.getIdentifier(query, TYPE_DRAWABLE, packageName)

    if (resId == 0) {
      resId = res.getIdentifier(query, TYPE_MIPMAP, packageName)
    }
    if (resId != 0) {
      try {
        return res.getDrawable(resId)
      } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
      }
    }
    return null
  }

  /**
   * 根据资源名称获取颜色值
   *
   * @param resName 资源名称
   * @param type 资源类型
   * @return 颜色值
   */
  @Throws(Resources.NotFoundException::class)
  internal fun getColor(resName: String, type: String): Int {
    val res = resources ?: throw Resources.NotFoundException()
    val query = getSkinResName(resName)
    return res.getColor(res.getIdentifier(query, type, packageName))
  }

  /**
   * 根据资源名称获取颜色值
   *
   * @param resName 资源名称
   * @return 颜色值
   */
  @Throws(Resources.NotFoundException::class)
  fun getColor(resName: String): Int {
    return getColor(resName, TYPE_COLOR)
  }

  /**
   * 根据资源名称获取颜色列表
   *
   * @param resName 资源名称
   * @param type 资源类型
   * @return 颜色列表
   */
  internal fun getColorStateList(resName: String, type: String): ColorStateList? {
    val res = resources ?: return null
    val query = getSkinResName(resName)
    val resId = res.getIdentifier(query, type, packageName)

    if (resId != 0) {
      try {
        return res.getColorStateList(resId)
      } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
      }
    }
    return null
  }

  /**
   * 根据资源名称获取颜色列表
   *
   * @param resName 资源名称
   * @return 颜色列表
   */
  fun getColorStateList(resName: String): ColorStateList? {
    val res = resources ?: return null
    val query = getSkinResName(resName)
    var resId = res.getIdentifier(query, TYPE_COLOR, packageName)

    if (resId == 0) {
      resId = res.getIdentifier(query, TYPE_DRAWABLE, packageName)
    }
    if (resId != 0) {
      try {
        return res.getColorStateList(resId)
      } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
      }
    }
    return null
  }

  /**
   * 根据资源名称获取字符串内容
   *
   * @param resName 资源名称
   * @param type 资源类型
   * @return 字符串内容
   */
  internal fun getString(resName: String, type: String): String? {
    val res = resources ?: return null
    val query = getSkinResName(resName)
    val resId = res.getIdentifier(query, type, packageName)

    if (resId != 0) {
      try {
        return res.getString(resId)
      } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
      }
    }
    return null
  }

  /**
   * 根据资源名称获取字符串内容
   *
   * @param resName 资源名称
   * @return 字符串内容
   */
  fun getString(resName: String): String? {
    return getString(resName, TYPE_STRING)
  }

  /**
   * 释放资源（如果存在）
   */
  fun free() {
    if (pluginResources != null) {
      pluginResources = null
    }
  }

  private fun appendSuffix(resName: String) = "$resName$suffix"

  companion object {

    private const val TYPE_DRAWABLE = "drawable"
    private const val TYPE_COLOR = "color"
    private const val TYPE_MIPMAP = "mipmap"
    private const val TYPE_STRING = "string"
  }

}