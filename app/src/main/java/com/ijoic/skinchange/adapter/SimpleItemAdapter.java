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
package com.ijoic.skinchange.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijoic.skin.SkinManager;
import com.ijoic.skinchange.R;
import com.ijoic.skinchange.adapter.holder.SimpleViewHolder;

/**
 * Simple item adapter.
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

  private final LayoutInflater inflater;

  private int itemCount;

  /**
   * Constructor.
   *
   * @param context context.
   */
  public SimpleItemAdapter(@NonNull Context context) {
    inflater = LayoutInflater.from(context);
  }

  @Override
  public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.simple_test_item, parent, false);
    SkinManager.getInstance().stickyInjectSkin(itemView);
    return new SimpleViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(SimpleViewHolder holder, int position) {
    // do nothing.
  }

  @Override
  public int getItemCount() {
    return itemCount;
  }

  /**
   * Append item count.
   *
   * @param count count.
   */
  public void appendItemCount(int count) {
    itemCount = Math.max(itemCount + count, 0);
  }
}
