/*
 *
 *  Copyright(c) 2020 VerstSiu
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
package com.ijoic.skin.cache

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import java.lang.ref.WeakReference

/**
 * Resource cache
 *
 * @author verstsiu created at 2020-10-27 09:33
 */
internal class ResourceCache {

  val drawable = ResCache(RefValueWrapper<Drawable>())
  val colorList = ResCache(RefValueWrapper<ColorStateList>())
  val color = ResCache(SimpleValueWrapper<Int>())

  class ResCache<VALUE, WRAP>(private val wrapper: ValueWrapper<VALUE, WRAP>) {
    private val map = mutableMapOf<String, CacheState<WRAP>>()

    fun getOrUpdate(resName: String, getValue: () -> VALUE?): VALUE? {
      var state = map[resName]
      if (state == null) {
        val value = this.runCatching { getValue() }.getOrNull()
        state = when(value) {
          null -> CacheState(isNotFound = true)
          else -> CacheState(data = wrapper.writeValue(value))
        }
        map[resName] = state
        return value
      }
      if (state.isNotFound) {
        return null
      }
      var data = state.data
      val value: VALUE?
      if (data == null) {
        value = this.runCatching { getValue() }.getOrNull()
        data = when(value) {
          null -> null
          else -> wrapper.writeValue(value)
        }
        state.data = data
      } else {
        value = wrapper.readValue(data)
      }
      return value
    }
  }

  private data class CacheState<T>(
    val isNotFound: Boolean = false,
    var data: T? = null
  )

  /* Wrapper */

  open class ValueWrapper<VALUE, WRAP>(
    val readValue: (WRAP) -> VALUE?,
    val writeValue: (VALUE) -> WRAP
  )

  class RefValueWrapper<VALUE> : ValueWrapper<VALUE, WeakReference<VALUE>>(
    readValue = { it.get() },
    writeValue = { WeakReference(it) }
  )

  class SimpleValueWrapper<VALUE> : ValueWrapper<VALUE, VALUE>(
    readValue = { it },
    writeValue = { it }
  )

  /* Wrapper :END */

}