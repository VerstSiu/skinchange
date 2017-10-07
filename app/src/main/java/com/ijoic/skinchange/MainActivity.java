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

package com.ijoic.skinchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ijoic.skinchange.sample.DynamicSkinActivity;
import com.ijoic.skinchange.sample.OutSkinActivity;
import com.ijoic.skinchange.sample.PaddingItemSkinActivity;
import com.ijoic.skinchange.sample.RecyclerViewSkinActivity;
import com.ijoic.skinchange.sample.SimpleSkinActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @OnClick(R.id.button_case_simple)
  void onCaseSimple() {
    performAction(SimpleSkinActivity.class);
  }

  @OnClick(R.id.button_case_plugin)
  void onCasePlugin() {
    performAction(OutSkinActivity.class);
  }

  @OnClick(R.id.button_case_dynamic)
  void onCaseDynamic() {
    performAction(DynamicSkinActivity.class);
  }

  @OnClick(R.id.button_case_padding_item)
  void onCasePaddingItem() {
    performAction(PaddingItemSkinActivity.class);
  }

  @OnClick(R.id.button_case_recycler_view)
  void onCaseRecyclerView() {
    performAction(RecyclerViewSkinActivity.class);
  }

  private void performAction(@NonNull Class<?> clazz) {
    startActivity(new Intent(this, clazz));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }
}
