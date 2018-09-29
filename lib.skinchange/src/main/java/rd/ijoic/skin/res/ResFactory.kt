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
package rd.ijoic.skin.res

import android.content.Context
import com.ijoic.ktx.util.getOrCreate
import rd.ijoic.skin.SkinConfig
import rd.ijoic.skin.SkinPreference
import rd.ijoic.skin.res.impl.DirectResManager
import java.util.*

/**
 * Resources factory.
 *
 * @author verstsiu on 2018/9/29
 * @version 1.1
 */
internal object ResFactory {

  private val resManagerMap by lazy { WeakHashMap<Context, MutableMap<String, ResManager>>() }

  /**
   * Returns res manager instance.
   *
   * @param context context.
   * @param style skin style.
   */
  fun getResManager(context: Context, style: String? = null): ResManager {
    val config = SkinPreference.getInstance(context).getSkinConfig(style.orEmpty())

    return getResManager(context, config)
  }

  /**
   * Returns res manager instance.
   *
   * @param context context.
   * @param config style config.
   */
  private fun getResManager(context: Context, config: SkinConfig): ResManager {
    // TODO Handle plugin resources manager
    val map = resManagerMap.getOrCreate(context) { mutableMapOf() }

    return map.getOrCreate(config.skinId) { DirectResManager(context, config.suffix) }
  }
}