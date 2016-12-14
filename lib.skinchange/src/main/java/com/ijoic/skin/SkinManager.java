package com.ijoic.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.ijoic.skin.attr.SkinAttrSupport;
import com.ijoic.skin.attr.SkinView;
import com.ijoic.skin.callback.SkinChangeCallback;
import com.ijoic.skin.callback.SkinTask;
import com.ijoic.skin.view.ViewContainerPool;
import com.ijoic.skin.view.compat.ActivityViewContainer;
import com.ijoic.skin.view.compat.FragmentViewContainer;
import com.ijoic.skin.view.ViewContainer;
import com.ijoic.skin.view.compat.SkinTaskViewContainer;

import org.jetbrains.annotations.Contract;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 皮肤管理器
 *
 * @author ijoic verstlim@126.com
 * @version 1.0
 */
public class SkinManager {

  @Contract(pure = true)
  public static @NonNull SkinManager getInstance() {
    return SingletonHolder.instance;
  }

  private interface SingletonHolder {
    SkinManager instance = new SkinManager();
  }

  private static final @NonNull Handler handler = new Handler();

  private final @NonNull ViewContainerPool containerPool = new ViewContainerPool();

  private @Nullable WeakReference<Context> refContext;
  private @Nullable SkinPreference skinPrefs;

  private final @NonNull ResourcesManager resourcesManager;
  private final @NonNull ResourcesTool resourcesTool;

  private static final String TAG_ACTIVITY = "activity";
  private static final String TAG_FRAGMENT = "fragment";
  private static final String TAG_SKIN_TASK = "skintask";

  private SkinManager() {
    resourcesManager = new ResourcesManager();
    resourcesTool = new ResourcesTool(resourcesManager);
  }

  /**
   * 初始化
   *
   * @param context 上下文
   */
  public void init(@NonNull Context context) {
    context = context.getApplicationContext();
    refContext = new WeakReference<>(context);
    resourcesTool.setContext(context);

    if (skinPrefs == null) {
      skinPrefs = new SkinPreference(context);
    }
    String pluginPath = skinPrefs.getPluginPath();
    String pluginPackage = skinPrefs.getPluginPackageName();
    String suffix = skinPrefs.getPluginSuffix();
    containerPool.trim();

    if (!initPlugin(pluginPath, pluginPackage, suffix)) {
      resetResourcesManager();
    }
  }

  private boolean initPlugin(String pluginPath, String pluginPackage, String suffix) {
    if (!isValidPluginParams(pluginPath, pluginPackage)) {
      return false;
    }

    try {
      loadPlugin(pluginPath, pluginPackage, suffix);
      return true;

    } catch (Exception e) {
      e.printStackTrace();

      if (skinPrefs != null) {
        skinPrefs.clear();
      }
    }
    return false;
  }

  /**
   * 检查插件参数是否正确
   *
   * <ul>通过条件：
   * <li>1. 插件路径、包名称不为空；</li>
   * <li>2. 插件包的内部名称，运提供的包名称一致</li></ul>
   *
   * @param pluginPath 插件路径
   * @param pluginPackage 插件包名称
   * @return 检查结果
   */
  private boolean isValidPluginParams(@Nullable String pluginPath, @Nullable String pluginPackage) {
    // check path format
    if (TextUtils.isEmpty(pluginPath) || TextUtils.isEmpty(pluginPackage)) {
      return false;
    }
    // check plugin file exist
    if (!checkFileExist(pluginPath)) {
      return false;
    }

    // check package info
    return checkPackageExist(pluginPath, pluginPackage);
  }

  private static boolean checkFileExist(@NonNull String filePath) {
    File file = new File(filePath);
    return file.exists();
  }

  private boolean checkPackageExist(@NonNull String pluginPath, @NonNull String packageName) {
    Context context = getContext();

    if (context == null) {
      return false;
    }
    PackageManager pm = context.getPackageManager();

    PackageInfo info = pm.getPackageArchiveInfo(pluginPath, PackageManager.GET_ACTIVITIES);
    return packageName.equals(info.packageName);
  }

  @Nullable
  private Context getContext() {
    return refContext == null ? null : refContext.get();
  }

  private void loadPlugin(@NonNull String pluginPath, @NonNull String pluginPackage, @Nullable String suffix) throws Exception {
    Context context = getContext();

    if (context == null) {
      return;
    }
    AssetManager assetManager = AssetManager.class.newInstance();
    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
    addAssetPath.invoke(assetManager, pluginPath);

    Resources superRes = context.getResources();
    Resources res = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    resourcesManager.setResources(res, true);
    resourcesManager.setSkinInfo(pluginPackage, suffix);
  }

  /**
   * 获取资源管理器
   *
   * @return 资源管理器
   */
  public @NonNull ResourcesManager getResourcesManager() {
    return resourcesManager;
  }

  /**
   * 获取资源工具
   *
   * @return 资源工具
   */
  public @NonNull ResourcesTool getResourcesTool() {
    return resourcesTool;
  }

  /**
   * 注册
   *
   * <p>在{@link Activity#onCreate(Bundle)}方法中调用</p>
   *
   * @param activity 活动
   */
  public void register(@NonNull Activity activity) {
    register(TAG_ACTIVITY, new ActivityViewContainer(activity));
  }

  /**
   * 取消注册
   *
   * <p>在{@link Activity#onDestroy()}方法中调用</p>
   *
   * @param activity 活动
   */
  public void unregister(@NonNull Activity activity) {
    unregister(TAG_ACTIVITY, new ActivityViewContainer(activity));
  }

  /**
   * 注册
   *
   * <p>在{@link Fragment#onActivityCreated(Bundle)}方法中调用</p>
   *
   * @param fragment 碎片
   */
  public void register(@NonNull Fragment fragment) {
    register(TAG_FRAGMENT, new FragmentViewContainer(fragment));
  }

  /**
   * 取消注册
   *
   * <p>在{@link Fragment#onDestroy()}方法中调用</p>
   *
   * @param fragment 碎片
   */
  public void unregister(@NonNull Fragment fragment) {
    unregister(TAG_FRAGMENT, new FragmentViewContainer(fragment));
  }

  /**
   * 注册换肤任务
   *
   * <p>在自定义视图中使用，结合{@link ResourcesTool}使用</p>
   *
   * @param view 视图
   * @param skinTask 换肤任务
   *
   * @see ResourcesTool#getColor(int)
   * @see ResourcesTool#getDrawable(int)
   * @see ResourcesTool#getColorStateList(int)
   */
  public<T extends View> void registerSkinTask(@NonNull T view, @NonNull SkinTask<T> skinTask) {
    register(TAG_SKIN_TASK, new SkinTaskViewContainer<T>(view, skinTask));
  }

  /**
   * 取消注册换肤任务
   *
   * <p>在自定义视图中使用，{@link ResourcesTool}使用</p>
   *
   * @param view 视图
   *
   * @see ResourcesTool#getColor(int)
   * @see ResourcesTool#getDrawable(int)
   * @see ResourcesTool#getColorStateList(int)
   */
  public<T extends View> void unregisterSkinTask(@NonNull T view) {
    unregister(TAG_SKIN_TASK, new SkinTaskViewContainer<T>(view, null));
  }

  private void register(@NonNull String tag, @NonNull final ViewContainer containerItem) {
    containerPool.add(tag, containerItem);

    handler.post(new Runnable() {
      @Override
      public void run() {
        applySkin(containerItem);
      }
    });
  }

  private void unregister(@NonNull String tag, @NonNull ViewContainer containerItem) {
    containerPool.remove(tag, containerItem);
  }

  private void applySkin(@NonNull ViewContainer viewContainer) {
    applySkin(viewContainer.getView());
  }

  private void applySkin(@Nullable View rootView) {
    if (rootView == null) {
      return;
    }
    injectSkin(rootView);
  }

  /**
   * 移除所有皮肤
   */
  public void removeAnySkin() {
    clearPluginInfo();
    resetResourcesManager();
    notifyChangedListeners();
  }

  private void clearPluginInfo() {
    if (skinPrefs == null) {
      return;
    }
    skinPrefs.clear();
  }

  private void resetResourcesManager() {
    Context context = getContext();

    if (context == null) {
      return;
    }
    resourcesManager.setResources(context.getResources());
    resourcesManager.setSkinInfo(context.getPackageName(), skinPrefs == null ? null : skinPrefs.getPluginSuffix());
  }

  private void notifyChangedListeners() {
    List<ViewContainer> containerItems = containerPool.getContainerItemsAll();
    View view;

    for (ViewContainer containerItem : containerItems) {
      view = containerItem.getView();

      if (view != null) {
        applySkin(view);
      }
    }
  }

  /**
   * 更改皮肤（应用内换肤）
   *
   * @param suffix 资源后缀
   */
  public void changeSkin(@Nullable String suffix) {
    clearPluginInfo(); // clear before

    if (skinPrefs != null) {
      skinPrefs.setPluginSuffix(suffix);
      resetResourcesManager();
    }
    notifyChangedListeners();
  }

  /**
   * 更改皮肤（应用外）
   *
   * @param pluginPath 皮肤插件路径
   * @param pluginPackage 皮肤插件包名称
   * @param callback 更改回调
   */
  public void changeSkin(@Nullable String pluginPath, @Nullable String pluginPackage, @Nullable SkinChangeCallback callback) {
    changeSkin(pluginPath, pluginPackage, null, callback);
  }

  /**
   * 更改皮肤（应用外）
   *
   * @param pluginPath 皮肤插件路径
   * @param pluginPackage 皮肤插件包名称
   * @param suffix 资源后缀
   * @param callback 更改回调
   */
  public void changeSkin(@Nullable final String pluginPath, @Nullable final String pluginPackage, @Nullable final String suffix, @Nullable SkinChangeCallback callback) {
    final SkinChangeCallback skinCallback = callback == null ? SkinChangeCallback.DEFAULT_CALLBACK : callback;
    skinCallback.onStart();

    if (pluginPath == null || pluginPackage == null || !isValidPluginParams(pluginPath, pluginPackage)) {
      skinCallback.onError("invalid plugin path or package");
      return;
    }

    new AsyncTask<Void, Void, Boolean>() {
      @Override
      protected Boolean doInBackground(Void... params) {
        try {
          loadPlugin(pluginPath, pluginPackage, suffix);
          return true;

        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }

      @Override
      protected void onPostExecute(Boolean result) {
        if (result == null || !result) {
          skinCallback.onError("loadPlugin occur error");
          return;
        }

        try {
          updatePluginInfo(pluginPath, pluginPackage, suffix);
          notifyChangedListeners();
          skinCallback.onComplete();
        } catch (Exception e) {
          e.printStackTrace();
          skinCallback.onError(e.getMessage());
        }
      }
    }.execute();
  }

  private void updatePluginInfo(@NonNull String pluginPath, @NonNull String pluginPackage, @Nullable String suffix) {
    if (skinPrefs == null) {
      throw new IllegalArgumentException("skin preference not found");
    }
    skinPrefs.setPluginPath(pluginPath);
    skinPrefs.setPluginPackageName(pluginPackage);
    skinPrefs.setPluginSuffix(suffix);
  }

  /**
   * 注入皮肤
   *
   * <p>用于动态生成的视图</p>
   *
   * @param view 视图
   */
  public void injectSkin(@NonNull View view) {
    List<SkinView> skinViews = SkinAttrSupport.getSkinViews(view);

    for (SkinView skinView : skinViews) {
      skinView.apply();
    }
  }

}
