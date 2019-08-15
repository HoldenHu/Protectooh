package com.scu.holden.protectooth.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.holden.protectooth.R;

import com.scu.holden.protectooth.localdatbase.User;
import com.scu.holden.protectooth.localdatbase.UserDatabaseManager;
import com.scu.holden.protectooth.utlis.Tools;
import com.scu.holden.protectooth.utlis.UrlToList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;


@ContentView(R.layout.activity_scrolling)
public class FragmentHomepage extends BaseFragment  {
	@ViewInject(R.id.app_bar)
	AppBarLayout appBarLayout;
	@ViewInject(R.id.progressbar)
	ProgressBar progressBar;
	@ViewInject(R.id.toolbar)
	Toolbar toolbar;
	@ViewInject(R.id.home_content)
	LinearLayout home_content;
	@ViewInject(R.id.toolbar_layout)
	CollapsingToolbarLayout toolBarLayout;
	@ViewInject(R.id.fab)
	FloatingActionButton fab;
	@ViewInject(R.id.hello_user)
	TextView hello_user;
	@ViewInject(R.id.hotelone_ivone)
	ImageView hotelone_ivone;
	@ViewInject(R.id.hotelone_ivtwo)
	ImageView hotelone_ivtwo;
	@ViewInject(R.id.hotelone_ivthree)
	ImageView hotelone_ivthree;
	@ViewInject(R.id.hoteltwo_ivone)
	ImageView hoteltwo_ivone;
	@ViewInject(R.id.hoteltwo_ivtwo)
	ImageView hoteltwo_ivtwo;
	@ViewInject(R.id.hoteltwo_ivthree)
	ImageView hoteltwo_ivthree;
	@ViewInject(R.id.hotelthree_ivone)
	ImageView hotelthree_ivone;
	@ViewInject(R.id.hotelthree_ivtwo)
	ImageView hotelthree_ivtwo;
	@ViewInject(R.id.hotelthree_ivthree)
	ImageView hotelthree_ivthree;
	@ViewInject(R.id.home_scrollview)
	NestedScrollView home_scrollview;


	private String filepath11="/storage/emulated/0/MOVIEW/Photos/20171026224443.jpg";
	private String filepath12="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath13="/storage/emulated/0/MOVIEW/Photos/20171026224453.jpg";
	private String filepath21="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath22="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath23="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath31="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath32="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";
	private String filepath33="/storage/emulated/0/MOVIEW/Photos/20171026224446.jpg";


	String phone;
	private Boolean isColose = false;
	private int flag = 0;
	private Float firstTouch;
	private Float secondTouch;
	private Handler handler =new Handler(){};
	private UserDatabaseManager mManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("====sa","home->onCreateView");
		return x.view().inject(this,inflater,container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		int i = Log.i("====sa", "home->onViewCreated");
		initContent();
		initView();
	}


	private void initView() {

		appBarLayout.setExpanded(false);
		progressBar.setVisibility(View.VISIBLE);
		home_content.setVisibility(View.INVISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				appBarLayout.setExpanded(true);
				Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.pop_up_in);
				home_content.setAnimation(animation);
				progressBar.setVisibility(View.GONE);
				home_content.setVisibility(View.VISIBLE);
			}
		},5000);
		//getActivity().setSupportActionBar(toolbar);
		toolBarLayout.setTitle("Protectooth");
		toolBarLayout.setExpandedTitleColor(getResources().getColor(R.color.bg_Black));
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				boolean enter_detect=true;
				try{
//					Intent intent0 = new Intent(Intent.ACTION_VIEW);
//					String packageName0 = "com.scu.holden.protectooth";
//					String className0 = "com.scu.holden.protectooth.PhotoUploadActivity";
//					intent0.setClassName(packageName0, className0);
//					startActivity(intent0);

					Intent intent = new Intent(Intent.ACTION_VIEW);
					String packageName = "com.tony.molink.moview";
					String className = "com.qihoo.util.StartActivity";
					intent.setClassName(packageName, className);
					startActivity(intent);

				}catch (Exception e){
					showSnackar(view, "未检测到Pt扫描设备");
					enter_detect=false;
				}

				if(enter_detect){
					Intent intent0 = new Intent(Intent.ACTION_VIEW);
					String packageName0 = "com.scu.holden.protectooth";
					String className0 = "com.scu.holden.protectooth.PhotoUploadActivity";
					intent0.setClassName(packageName0, className0);
					startActivity(intent0);

					Intent intent = new Intent(Intent.ACTION_VIEW);
					String packageName = "com.tony.molink.moview";
					String className = "com.qihoo.util.StartActivity";
					intent.setClassName(packageName, className);
					startActivity(intent);

				}


			}
		});

		hotelone_ivone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				String packageName = "com.scu.holden.protectooth";
				String className = "com.scu.holden.protectooth.LookHistoryActivity";
				intent.setClassName(packageName, className);
				startActivity(intent);

			}
		});

	}

	public void initContent() {

		Log.i("====当前的线程",Thread.currentThread()+"");
		SharedPreferences userinfo = getActivity().getSharedPreferences("userinfo", 0);
		phone = userinfo.getString("username", null);



		//切换线程执行。切记
//		String username=mManager.getRecent().getNickname();
		String username="Holden";

		hello_user.setText(username+",你好");
		loadpic();
	}

	public void loadpic() {
		setmitmap(filepath11,hotelone_ivone);
		setmitmap(filepath12,hotelone_ivtwo);
		setmitmap(filepath13,hotelone_ivthree);
		setmitmap(filepath21,hoteltwo_ivone);
		setmitmap(filepath22,hoteltwo_ivtwo);
		setmitmap(filepath23,hoteltwo_ivthree);
		setmitmap(filepath31,hotelthree_ivone);
		setmitmap(filepath32,hotelthree_ivtwo);
		setmitmap(filepath33,hotelthree_ivthree);
	}

	public void setmitmap(String filepath, ImageView imageview) {
		File file = new File(filepath);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(filepath);
			//将图片显示到ImageView中
			imageview.setImageBitmap(bm);
		}
	}



}
