package com.scu.holden.protectooth.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.utlis.PostUtils;

import java.util.ArrayList;

public class PullToRefreshListAdapter extends BaseAdapter {
	private String[] titles = { };
	private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
	private ArrayList<String> contents = new ArrayList<String>();
	private Context context;
	Handler handler;
	private ImageView ivNews;
	private View layout;
	private TextView tvIntro;
	private String[] imgUrls = {};
	private ArrayList<String> img_urls = new ArrayList<>();
	public void setTexts(ArrayList<String> content){
		titles = content.toArray(new String[content.size()]);
		getNewsContent();
		getNewsPicUrls();
		Log.e("duolafang2",titles[2]);
	}
	private void getNewsPicUrls(){
		Thread workThread1=new Thread(){
			@Override
			public void run(){
				PostUtils postu = new PostUtils();
				for(int i=0;i<titles.length;i++){
					img_urls.add(postu.get("http://119.29.246.19:7777/getNewsPic/?title=" + titles[i]));
				}
				imgUrls = img_urls.toArray(new String[img_urls.size()]);
			}
		};
		workThread1.start();
		//主线程等待workThread1执行完毕
		try {
			workThread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void getNewsContent(){
		Thread workThread1=new Thread(){
			@Override
			public void run(){
				PostUtils postu = new PostUtils();
				for(int i=0;i<titles.length;i++) {
					contents.add(postu.get("http://119.29.246.19:7777/getNewsIntro/?title=" + titles[i]));
				}
			}
		};
		workThread1.start();
		//主线程等待workThread1执行完毕
		try {
			workThread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public PullToRefreshListAdapter(Context context) {
		this.context = context;
		for(int i =0;i<10;i++) {
			bitmaps.add(null);
		}
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//拿到ListViewItem的布局，转换为View类型的对象
		layout = View.inflate(context, R.layout.newslist_item, null);
		//找到显示新闻图片的ImageView
		ivNews = (ImageView) layout.findViewById(R.id.ivThumb);
		final int pos = position;
		//找到显示新闻名称的TextView
		final TextView[] tvName = {(TextView) layout.findViewById(R.id.tvName)};
		//显示新闻图片
		// 设置默认图片（未显示网络图片时）
		ivNews.setImageResource(R.drawable.loading);
		//显示新闻题目
		tvName[0].setText(titles[position]);
		//找到显示新闻简介的TextView
		tvIntro = (TextView) layout.findViewById(R.id.tvIntro);
		tvIntro.setText(contents.get(pos));
		if (bitmaps.get(pos) != null) {//如果图片已经存在缓存中
			ivNews.setImageBitmap(bitmaps.get(pos));
		} else {
			// 消息加线程机制通知主界面更新图片
			handler = new Handler() {
				public void handleMessage(Message msg) {
					ivNews.setImageBitmap((Bitmap) msg.obj);
//					bitmaps.add((int) msg.arg1, (Bitmap) msg.obj);
				};
			};
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Bitmap bm = PostUtils.getInternetPicture(imgUrls[pos]);
					Message msg = new Message();
					// 把bm存入消息中,发送到主线程
					bitmaps.add(pos, bm);//缓存图片
					msg.obj = bm;
					msg.arg1 = pos;
					handler.sendMessage(msg);
				}
			}).start();

		}
		return layout;
	}
}
