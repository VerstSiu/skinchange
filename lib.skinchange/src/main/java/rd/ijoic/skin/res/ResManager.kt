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

import android.content.res.Resources
import android.support.annotation.ColorRes

/**
 * Resources manager.
 *
 * @author verstsiu on 2018/9/27
 * @version 2.2
 */
internal abstract class ResManager {
  /**
   * Resources.
   */
  protected abstract val res: Resources

  /**
   * Package name.
   */
  protected abstract val packageName: String?

  /**
   * Skin suffix.
   */
  protected abstract val suffix: String?

  /**
   * Returns color value.
   *
   * @param resId res id.
   */
  @Throws(Resources.NotFoundException::class)
  fun getColor(@ColorRes resId: Int): Int {
    val suffix = this.suffix

    return when {
      suffix.isNullOrEmpty() -> res.getColor(resId)
      else -> getColor(res.getResourceName(resId))
    }
  }

  /**
   * Returns color value.
   *
   * @param resName res name.
   */
  @Throws(Resources.NotFoundException::class)
  fun getColor(resName: String): Int {
    val resId = fetchSkinResId(resName, ResType.COLOR)

    return res.getColor(resId)
  }

  /**
   * Fetch skin res id or throws [Resources.NotFoundException].
   *
   * <p>This method will auto append current skin suffix to resources name.</p>
   *
   * @param resName res name.
   * @param resType res type.
   */
  @Throws(Resources.NotFoundException::class)
  private fun fetchSkinResId(resName: String, resType: String): Int {
    return fetchResId(resName.appendSkinSuffix(suffix), resType)
  }

  /**
   * Fetch res id or throws [Resources.NotFoundException].
   *
   * @param resName res name.
   * @param resType res type.
   */
  @Throws(Resources.NotFoundException::class)
  private fun fetchResId(resName: String, resType: String): Int {
    val resId = res.getIdentifier(resName, ResType.COLOR, packageName)

    if (resId == 0) {
      throw Resources.NotFoundException("identifier of R.$resType.$resName not found")
    }
    return resId
  }

  private fun String.appendSkinSuffix(suffix: String?): String {
    return when {
      suffix == null || suffix.isEmpty() -> this
      else -> "${this}_$suffix"
    }
  }
}