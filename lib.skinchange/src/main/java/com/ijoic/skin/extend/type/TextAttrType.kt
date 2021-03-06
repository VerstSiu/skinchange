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

package com.ijoic.skin.extend.type

import android.view.View
import android.widget.TextView
import com.ijoic.skin.ResourcesManager
import com.ijoic.skin.attr.SkinAttrType

/**
 * 文本内容属性类型
 *
 * @author ijoic verstlim@126.com
 * @version 1.0.5
 */
internal object TextAttrType : SkinAttrType {

  override fun prepareResource(rm: ResourcesManager, resName: String): Any? {
    return rm.getString(resName)
  }

  override fun apply(view: View, resource: Any) {
    if (view !is TextView || resource !is String) {
      return
    }
    view.text = resource
  }

}
