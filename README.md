# skinchange

[![](https://jitpack.io/v/VerstSiu/skinchange.svg)](https://jitpack.io/#VerstSiu/skinchange)

一种完全无侵入的换肤方式，支持插件式和应用内，无需重启Activity。

在[AndroidChangeSkin](https://github.com/hongyangAndroid/AndroidChangeSkin)的基础上，增加自定义属性扩展的灵活性，并且增加碎片换肤支持。

## 特点

* 插件内换肤
* 应用内换肤
* 支持插件或者应用内多套皮肤
* 支持动态增加视图的换肤
* 无需重启Activity

## 引入

1.  在根`build.gradle`中添加`jitpack`仓库：
    
    ```gradle
    allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    ```
    
2. 添加库项目依赖：
    
    ```gradle
    dependencies {
      implementation 'com.github.VerstSiu.skinchange:lib.skinchange:2.1'
      implementation 'com.github.VerstSiu.skinchange:lib.skinchange-carbon:2.1'
    }
    ```

## 支持属性

目前支持的属性包括：

* background
* text
* textColor
* textColorHighlight
* textColorHint
* textColorLink
* drawableLeft
* drawableTop
* drawableRight
* drawableBottom
* button
* src
* divider
* listSelector
* indeterminateDrawable
* progressDrawable
* thumb
* popupBackground


## 使用

### 基本配置

1. 在`Application`中初始化皮肤管理器。

    ```kotlin
    class MyApplication : Application() {

      override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
      }

    }
    ```

2. 在`Activity`中注册、监听换肤操作。

    ```kotlin
    class MyActivity : Activity() {

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)
        SkinManager.register(this)
        ...
      }

    }
    ```

3. 在`Fragment`中注册、监听换肤操作。

    ```kotlin
    class MyFragment : Fragment() {

      override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SkinManager.register(this)
        ...
      }

    }
    ```

4. 布局文件配置

    布局文件中添加支持，主要依赖于tag属性：

    例如：

    ```xml
    <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:textColor="@color/item_text_color"
      android:background="@color/item_text_bg"
      android:tag="skin:item_text_color:textColor|skin:item_text_bg:background"/>
    ```
    tag属性分为3部分组成：

    * skin
    * 资源名称，插件包中的资源名称，需要于app内使用的资源名称一致。
    * 支持的属性，目前支持`src`、`background`、`textColor`等基本样式属性，可自由扩展。

    3部分，必须以:分隔符拼接。

    对于一个 `View`需要多个换肤属性的，`android:tag="skin:item_text_color:textColor|skin:icon:src"`同样使用|进行分隔。

    简言之：需要换肤的视图，按上述格式在xml文件中添加`tag`就可以了。

5. 切换皮肤

    插件式：

    ```kotlin
    SkinManager.changeSkin(
      skinPath,
      "com.ijoic.skin_plugin",
      object: SkinChangeCallback {
        override fun onStart() {
        }

        override fun onError(errorMessage: String?) {
        }

        override fun onComplete() {
        }
      }
    )
    ```

    应用内：

    ```kotlin
    SkinManager.changeSkin(suffix)
    ```

    应用内多套皮肤以后缀区别就可以，比如：`main_bg`，皮肤资源可以为：`main_bg_red`、`main_bg_green`等。

    换肤时，直接传入后缀，例如上面的`red`、`green`。


### 动态创建视图换肤

对于动态创建的视图，在视图创建完成后，必须手动调用一次皮肤替换操作。

```kotlin
val view = inflater.inflate(R.id.my_layout, parent, false)
...
SkinManager.injectSkin(view)
```


### 动态更改皮肤关联属性

更改皮肤关联属性，填充tag的同时，会立即执行一次对应属性的换肤操作。

```kotlin
val view = findViewById<TextView>(R.id.my_text);

SkinTool.fillTag(view, R.color.my_new_text_color, AttrTypes.TEXT_COLOR)
```


### 自定义视图换肤

自定义视图，由于关联的皮肤属性可能比较多，手动配置麻烦，因此使用皮肤任务的形式换肤。

```kotlin
val view: CustomView
val skinEditor = SkinManager.edit(lifecycle)

skinEditor.addTask(view, new SkinTask<CustomView>() {
  override fun performSkinChange(compat: CustomView) {
    val resTool = SkinManager.resourcesTool

    compat.setColor(resTool.getColor(R.id.my_custom_color))
    ...
  }
})
```

`RecyclerView`列表项动态换肤适配：

```kotlin
class MyAdapter<VH: RecyclerView.ViewHolder>(
    context: Context,
    private val skinEditor: SkinEditor): RecyclerView.Adapter<VH> {

  private val inflater = LayoutInflater.from(context)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val itemView = inflater.inflate(R.layout.item_my_adapter, parent, false)
    skinEditor.stickyInjectView(itemView)
    return ...
  }
  ...
}
```


### 自定义属性支持

1. 创建属性类型：

    ```kotlin
    class MyAttrType : SkinAttrType {

      override fun apply(view: View, resName: String) {
        if (view !is MyCustomView) {
          return
        }
        val rm = SkinManager.resourcesManager
        val d = rm.getDrawableByName(resName)

        if (d != null) {
          // ..
        }
      }

    }
    ```

2. 在`Application#onCreate()`方法中，配置属性类型：

    ```kotlin
    override fun onCreate() {
      super.onCreate()

      AttrTypeFactory.register("my_custom_attr", MyAttrType())
      // ..
      SkinManager.init(this)
    }
    ```

3. 在`xml`或者动态创建的视图中使用：

    ```kotlin
    val view = findViewById<MyCustomView>(R.id.my_custom_view)
    // ..

    SkinTool.fillTag(view, R.drawable.my_drawable, "my_custom_attr")
    ```
    
## License

```

   Copyright(c) 2018 VerstSiu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
