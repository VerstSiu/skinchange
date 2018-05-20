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

import android.support.v4.util.ArrayMap
import com.ijoic.skin.attr.SkinAttrType

/**
 * 皮肤属性工厂
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
object AttrTypeFactory {

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> module :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val defaultModule: AttrTypeModule = DefaultModule
  private val extraModules: MutableMap<String, Pair<AttrPrefix, AttrTypeModule>> = ArrayMap()

  private var extraModuleAttrs: Map<String, SkinAttrType>? = null

  init {
    defaultModule.init()
  }

  /**
   * Add attr module.
   *
   * @param prefix module prefix.
   * @param module module.
   */
  @JvmStatic
  fun addModule(prefix: AttrPrefix, module: AttrTypeModule): AttrTypeFactory {
    extraModules[module.name] = Pair(prefix, module)
    return this
  }

  /**
   * Load module attributes.
   */
  @JvmStatic
  fun loadModuleAttributes() {
    val attrs = ArrayMap<String, SkinAttrType>()

    extraModules.values.forEach {
      val prefix = it.first
      val module = it.second

      module.attrMap.forEach { resType, attr ->
        val resPrefix = prefix.getPrefix(resType)
        val query = if (resPrefix.isNullOrBlank()) resType else "${resPrefix}_$resType"
        attrs[query] = attr
      }
    }
    extraModuleAttrs = attrs
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> module :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> attr type :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * 注册属性类型
   *
   * @param typeName 类型名称
   * @param typeClazz 类型类
   */
  @JvmStatic
  @Deprecated(
      "Set attr type instance directly",
      ReplaceWith(
          "AttrTypeFactory.register(typeName,type)",
          imports = [
            "com.ijoic.skin.extend.AttrTypeFactory",
            "com.ijoic.skin.attr.SkinAttrType"
          ]
      )
  )
  fun register(typeName: String, typeClazz: Class<out SkinAttrType>) {
    try {
      val attrType = typeClazz.newInstance()
      defaultModule.addAttrType(typeName, attrType)

    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * 注册属性类型
   *
   * @param typeName 类型名称
   * @param type 类型实例
   */
  @JvmStatic
  fun register(typeName: String, type: SkinAttrType) {
    defaultModule.addAttrType(typeName, type)
  }

  /**
   * 获取属性类型
   *
   * @param typeName 属性类型名称
   * @return 属性类型
   */
  @JvmStatic
  internal fun obtainAttrType(typeName: String): SkinAttrType? {
    return defaultModule.attrMap[typeName] ?: extraModuleAttrs?.get(typeName)
  }

  /**
   * Returns expected attr type.
   *
   * @param module module.
   * @param typeName type name.
   */
  @JvmStatic
  internal fun obtainAttrType(module: String, typeName: String): SkinAttrType? {
    val pair = extraModules[module] ?: return null
    val attrMap = pair.second.attrMap
    return attrMap[typeName]
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> attr type :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}