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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ijoic.skinchange.sample.DynamicSkinActivity;
import com.ijoic.skinchange.sample.OutSkinActivity;
import com.ijoic.skinchange.sample.PaddingItemSkinActivity;
import com.ijoic.skinchange.sample.SimpleSkinActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.button_case_simple).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, SimpleSkinActivity.class));
      }
    });
    findViewById(R.id.button_case_plugin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, OutSkinActivity.class));
      }
    });
    findViewById(R.id.button_case_dynamic).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, DynamicSkinActivity.class));
      }
    });
    findViewById(R.id.button_case_padding_item).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, PaddingItemSkinActivity.class));
      }
    });
  }
}
