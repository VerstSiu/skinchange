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

import androidx.collection.ArrayMap
import com.ijoic.skin.attr.SkinAttrType

/**
 * Attr type module.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
abstract class AttrTypeModule(val name: String) {

  internal val attrMap = ArrayMap<String, SkinAttrType>()

  /**
   * Initialize.
   */
  internal fun init() = onInit()

  /**
   * Initialize callback.
   */
  protected abstract fun onInit()

  /**
   * 注册属性类型
   *
   * @param typeName 类型名称
   * @param type 类型实例
   */
  fun addAttrType(typeName: String, type: SkinAttrType) {
    attrMap[typeName] = type
  }

}