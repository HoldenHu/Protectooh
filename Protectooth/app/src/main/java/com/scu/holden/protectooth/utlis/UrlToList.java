package com.scu.holden.protectooth.utlis;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.scu.holden.protectooth.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UrlToList {
	private static String urlstring;
	private static String imageCachePath;
	private  static boolean exists;
	// ==========url集合imagelist集合，viewpager使用
	public static List<ImageView> urlToImageList(
			List<Map<String, String>> list, final Context context) {
		ArrayList<ImageView> imagelist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			// 动态加载imageview
			ImageView imageView = new ImageView(context);
			imageView.setTag(i);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(layoutParams);
			imageView.setScaleType(ScaleType.FIT_XY);
			String discCachePath = context.getExternalCacheDir()
					.getAbsolutePath();
			ImageOptions build = new ImageOptions.Builder().
					setFadeIn(true).setConfig(Config.RGB_565)
					.setForceLoadingDrawable(true)
					.setLoadingDrawableId(R.drawable.homeback)
					.setImageScaleType(ScaleType.FIT_XY)
					.setUseMemCache(true)
					.build();
			x.image().bind(imageView, list.get(i).get("url"), build);
			imagelist.add(imageView);
		}
		return imagelist;

	}

	public static String  findImageUrl(Context context,ImageView key){
		SharedPreferences imageurl = context.getSharedPreferences("imageurl", 0);
		String string = imageurl.getString(key.getId()+"", null);
		return string;


	}
	// ==============根据url给控件设置图片
	public static void urlDisplayImage( ImageView image,Context context) {
		String imageUrl = findImageUrl(context, image);
		if (imageUrl != null) {

			ImageOptions build = new ImageOptions.Builder().
					setFadeIn(true).setConfig(Config.RGB_565)
					.setForceLoadingDrawable(true)
					.setLoadingDrawableId(R.drawable.homeback)
					.setImageScaleType(ScaleType.FIT_XY)
					.setUseMemCache(true)
					.build();
			x.image().bind(image, imageUrl, build);
		}
	}
		// ==============根据url给控件设置图片,加载成drawable再设置图片，应用于pullviewpager
	public static void urlDisplayImages( final ImageView image,Context context) {
		String imageUrl = findImageUrl(context, image);
		Log.i("==draw地址", imageUrl);
		if (imageUrl != null) {

			ImageOptions build = new ImageOptions.Builder().
					setFadeIn(true).setConfig(Config.RGB_565)
					.setForceLoadingDrawable(true)
					.setLoadingDrawableId(R.drawable.homeback)
					.setImageScaleType(ScaleType.FIT_XY)
					.setUseMemCache(true)
					.build();
			x.image().loadDrawable(imageUrl, build, new Callback.CacheCallback<Drawable>() {
				@Override
				public boolean onCache(Drawable result) {
					Log.i("==onCache下载成功", "xasa");
					image.setImageDrawable(result);
					return false;
				}

				@Override
				public void onSuccess(Drawable result) {
					Log.i("==下载成功", "xasa");
					image.setImageDrawable(result);
				}


				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
					Log.i("==下载成功", "xasa" + ex.toString());
				}

				@Override
				public void onCancelled(CancelledException cex) {
					Log.i("==oonCancelled下载成功", "xasa");

				}

				@Override
				public void onFinished() {
					Log.i("==onFinished", "xasa");

				}
			});

		}
	}





}

