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

package com.ijoic.skinchange.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ijoic.skin.SkinManager;
import com.ijoic.skinchange.R;
import com.ijoic.skinchange.TestConfig;
import com.ijoic.skinchange.adapter.SimpleItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Recycler view skin activity.
 *
 * @author ijoic verstlim@126.com
 * @version 1.1
 */
public class RecyclerViewSkinActivity extends Activity {

  @BindView(R.id.test_recycler_list)
  RecyclerView itemsView;

  private SimpleItemAdapter itemAdapter;

  private String currSuffix = TestConfig.SUFFIX_DEFAULT;

  private static final int INCREASE_ITEM_COUNT = 2;

  @OnClick(R.id.button_add_view)
  void onAddView() {
    itemAdapter.appendItemCount(INCREASE_ITEM_COUNT);
    itemAdapter.notifyDataSetChanged();
  }

  @OnClick(R.id.button_toggle)
  void onToggleSkin() {
    currSuffix = TestConfig.SUFFIX_DEFAULT.equals(currSuffix) ? TestConfig.SUFFIX_RED : TestConfig.SUFFIX_DEFAULT;
    SkinManager.getInstance().changeSkin(currSuffix);
  }

  @OnClick(R.id.button_reset)
  void onResetSkin() {
    currSuffix = TestConfig.SUFFIX_DEFAULT;
    SkinManager.getInstance().removeAnySkin();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SkinManager.getInstance().register(this);
    setContentView(R.layout.act_recycler_view_skin);
    ButterKnife.bind(this);

    itemAdapter = new SimpleItemAdapter(this);
    itemAdapter.appendItemCount(INCREASE_ITEM_COUNT);

    itemsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    itemsView.setAdapter(itemAdapter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    SkinManager.getInstance().unregister(this);
  }

}
