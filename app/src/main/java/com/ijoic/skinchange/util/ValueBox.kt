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
package com.ijoic.skinchange.util

/**
 * Value box.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
class ValueBox<T>(vararg values: T) {

  private val elements: Array<out T>
  private val elementSize: Int
  private var elementIndex: Int

  init {
    if (values.isEmpty()) {
      throw IllegalArgumentException("invalid values: $values")
    }
    elements = values
    elementSize = elements.size
    elementIndex = 0
  }

  /**
   * Set current value.
   *
   * @param value value.
   */
  fun setCurrentValue(value: T) {
    elementIndex = elements.indexOfFirst { it == value }
  }

  /**
   * Toggle to next value.
   */
  fun toggle(): T {
    var index = ++elementIndex

    if (index >= elementSize) {
      index = 0
      elementIndex = 0
    }
    return elements[index]
  }
}