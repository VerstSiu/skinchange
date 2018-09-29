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

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.View
import rd.ijoic.skin.tag.applySkin

/**
 * Skin manager.
 *
 * @author verstsiu on 2018/9/28
 * @version 1.1
 */
class SkinManager(context: Context) {
  private val prefs = SkinPreference.getInstance(context)
  private val activityItems by lazy { mutableListOf<Activity>() }

  /**
   * Returns skin config instance.
   *
   * @param style skin style.
   */
  fun getConfig(style: String? = null): SkinConfig {
    return prefs.getSkinConfig(style.orEmpty())
  }

  /**
   * Initialize.
   *
   * @param activity activity.
   */
  fun init(activity: FragmentActivity) {
    activityItems.add(activity)

    activity.lifecycle.addObserver(object: LifecycleObserver {
      @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
      fun onResume() {
        activity.applySkin(null)
      }

      @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      fun onDestroy() {
        activityItems.remove(activity)
      }
    })
  }

  /**
   * Attach style.
   *
   * @param group group.
   * @param style style.
   */
  fun attachStyle(group: String, style: String?) {
    prefs.setStyle(group, style)
  }

  /**
   * Change skin.
   *
   * @param suffix skin suffix.
   * @param style skin style.
   */
  fun changeSkin(suffix: String, style: String? = null) {
    val config = prefs.getSkinConfig(style.orEmpty())

    if (config.pluginEnabled || config.suffix != suffix) {
      config.pluginEnabled = false
      config.suffix = suffix

      activityItems.forEach { it.applySkin(style) }
    }
  }

  private fun Activity.applySkin(style: String?) {
    val root = findViewById<View>(android.R.id.content)

    root?.applySkin(style)
  }

}