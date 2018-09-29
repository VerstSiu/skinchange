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
package rd.ijoic.skin

import android.content.Context
import com.ijoic.ktx.content.preference.AccessPreference
import com.ijoic.ktx.content.preference.PrefsChild
import com.ijoic.ktx.content.preference.bindGroup
import com.ijoic.ktx.content.preference.bindOptionalString
import com.ijoic.ktx.util.getOrCreate
import java.util.*

/**
 * Skin preference.
 *
 * @author verstsiu on 2018/9/28
 * @version 1.1
 */
internal class SkinPreference private constructor(context: Context): AccessPreference(context, "ijc_skin_change") {

  /**
   * Style map.
   */
  private val styleMap by bindGroup("style") { SkinConfig() }

  /**
   * Group map.
   */
  private val groupMap by bindGroup("group") { TextConfig() }

  /**
   * Returns skin config instance.
   *
   * @param style skin style.
   */
  fun getSkinConfig(style: String): SkinConfig {
    return styleMap.getChild(style)
  }

  /**
   * Returns group related style.
   *
   * @param group group.
   */
  fun getStyle(group: String): String? {
    return groupMap.getChild(group).value
  }

  /**
   * Set group related style.
   *
   * @param group group.
   * @param style style.
   */
  fun setStyle(group: String, style: String?) {
    groupMap.getChild(group).value = style
  }

  /**
   * Simple text config.
   */
  private class TextConfig: PrefsChild() {
    /**
     * Text value.
     */
    var value by bindOptionalString("value")
  }

  companion object {
    private val instanceCache by lazy { WeakHashMap<Context, SkinPreference>() }

    /**
     * Returns skin manager instance.
     *
     * @param context context.
     */
    fun getInstance(context: Context): SkinPreference {
      val appContext = context.applicationContext

      return instanceCache.getOrCreate(appContext) { SkinPreference(appContext) }
    }
  }
}