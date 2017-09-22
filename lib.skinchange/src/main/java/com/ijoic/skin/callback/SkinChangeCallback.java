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

package com.ijoic.skin.callback;

/**
 * 皮肤更改回调
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
public interface SkinChangeCallback {

  /**
   * 皮肤替换开始
   */
  void onStart();

  /**
   * 皮肤替换出错
   *
   * @param errorMessage 错误信息
   */
  void onError(String errorMessage);

  /**
   * 皮肤替换完成
   */
  void onComplete();

  /**
   * 默认回调（空对象）
   */
  SkinChangeCallback DEFAULT_CALLBACK = new SkinChangeCallback() {
    @Override
    public void onStart() {
    }

    @Override
    public void onError(String errorMessage) {
    }

    @Override
    public void onComplete() {
    }
  };

}
