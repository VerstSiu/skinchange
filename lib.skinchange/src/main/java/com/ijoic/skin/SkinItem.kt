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

import android.support.v4.util.Pools
import com.ijoic.skin.attr.SkinAttrType

/**
 * Skin item.
 *
 * @author verstsiu on 2018/5/18.
 * @version 2.0
 */
internal class SkinItem private constructor(
    var resName: String? = null,
    var resType: String? = null,
    var skinId: String? = null,
    var attr: SkinAttrType? = null) {

  companion object {
    private val instancePool = Pools.SynchronizedPool<SkinItem>(200)

    /**
     * Obtain skin item instance.
     */
    fun obtain(): SkinItem {
      return instancePool.acquire() ?: SkinItem()
    }

    /**
     * Release skin item instance.
     */
    fun release(item: SkinItem) {
      item.apply {
        resName = null
        skinId = null
        attr = null
      }
      instancePool.release(item)
    }

    /**
     * Release items.
     *
     * @param items items.
     */
    fun releaseItems(items: Collection<SkinItem>?) {
      items?.forEach { release(it) }
    }
  }
}