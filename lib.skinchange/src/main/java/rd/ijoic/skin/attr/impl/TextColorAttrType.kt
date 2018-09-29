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
package rd.ijoic.skin.attr.impl

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import rd.ijoic.skin.attr.AttrType
import rd.ijoic.skin.res.ResManager

/**
 * Text color attribute type.
 *
 * @author verstsiu on 2018/9/27
 * @version 2.2
 */
internal object TextColorAttrType: AttrType {
  override fun apply(resManager: ResManager, view: View, resName: String) {
    if (view !is TextView) {
      return
    }

    try {
      view.setTextColor(resManager.getColor(resName))

    } catch (e: Resources.NotFoundException) {
      e.printStackTrace()
    }
  }
}