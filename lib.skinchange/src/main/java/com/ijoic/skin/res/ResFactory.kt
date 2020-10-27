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
package com.ijoic.skin.res

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.ijoic.ktx.guava.cache.WeakCacheMap
import com.ijoic.ktx.util.getOrCreate
import com.ijoic.skin.ResourcesManager
import java.util.concurrent.TimeUnit

/**
 * Resources factory.
 *
 * @author verstsiu on 2018/6/25.
 * @version 2.0
 */
internal object ResFactory {

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val resMap by lazy { WeakCacheMap<String, Resources>(20, TimeUnit.SECONDS) }

  /**
   * Returns resources instance.
   *
   * @param context context.
   * @param pluginPath plugin path.
   * @param packageName package name.
   * @param onError load error: fun().
   */
  internal fun getResources(context: Context, pluginPath: String? = null, packageName: String? = null, onError: (() -> Unit)? = null): Resources {
    val resPackage = packageName ?: context.packageName

    return resMap.getOrCreate(resPackage) {
      var pluginResources: Resources? = null

      if (pluginPath != null) {
        try {
          pluginResources = loadPluginResources(context, pluginPath)
        } catch (e: Throwable) {
          e.printStackTrace()
          onError?.invoke()
        }
      }
      pluginResources ?: context.resources
    }
  }

  /**
   * Load plugin resources.
   *
   * @param context context.
   * @param pluginPath plugin path.
   * @return plugin resources.
   */
  @Throws(Throwable::class)
  private fun loadPluginResources(context: Context, pluginPath: String): Resources {
    val manager = AssetManager::class.java.newInstance()
    val addAssetPath = manager.javaClass.getMethod("addAssetPath", String::class.java)
    addAssetPath.invoke(manager, pluginPath)

    val superRes = context.resources
    return Resources(manager, superRes.displayMetrics, superRes.configuration)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources manager :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  private val resManagerMap by lazy { WeakCacheMap<String, ResourcesManager>(10, TimeUnit.SECONDS) }

  /**
   * Returns res manager.
   *
   * @param resources resources.
   * @param packageName package name.
   * @param suffix suffix.
   */
  internal fun getResManager(resources: Resources, packageName: String, suffix: String?): ResourcesManager {
    return resManagerMap.getOrCreate("$packageName:${suffix.orEmpty()}") {
      ResourcesManager().apply {
        this.resources = resources
        setSkinInfo(packageName, suffix)
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> resources manager :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

}