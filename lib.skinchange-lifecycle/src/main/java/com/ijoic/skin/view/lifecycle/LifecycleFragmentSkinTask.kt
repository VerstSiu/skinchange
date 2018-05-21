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
package com.ijoic.skin.view.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import com.ijoic.skin.SkinManager
import com.ijoic.skin.view.SkinTask
import java.lang.ref.WeakReference

/**
 * Lifecycle fragment skin task.
 *
 * @author verstsiu on 2018/5/21.
 * @version 2.0
 */
internal class LifecycleFragmentSkinTask: SkinTask<Fragment>, LifecycleObserver {

  private var stateActive = false
  private var refCompat: WeakReference<Fragment>? = null

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  internal fun onPause() {
    stateActive = false
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  internal fun onResume() {
    stateActive = true
    refCompat?.get()?.let { performSkinChange(it) }
  }

  override fun isActive() = stateActive

  override fun onAttach(compat: Fragment) {
    refCompat = WeakReference(compat)
    val lifecycle = compat.lifecycle
    lifecycle.addObserver(this)

    if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
      onResume()
    }
  }

  override fun onDetach(compat: Fragment) {
    refCompat = null
    stateActive = false
    val lifecycle = compat.lifecycle
    lifecycle.removeObserver(this)
  }

  override fun performSkinChange(compat: Fragment) {
    val contentView = compat.view ?: return
    SkinManager.injectSkin(contentView)
  }

}
