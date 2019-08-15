package com.scu.holden.protectooth.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.adapter.MyHeaderPageAdapter;


import com.scu.holden.protectooth.adapter.PullToRefreshListAdapter;
import com.scu.holden.protectooth.adapter.ViewFlowAdapter;
import com.scu.holden.protectooth.component.CustomMyListView;
import com.scu.holden.protectooth.component.DepthPageTransformer;
import com.scu.holden.protectooth.component.EditTextWithDel;
import com.scu.holden.protectooth.component.ViewPagerScroller;
import com.scu.holden.protectooth.contentActivity;
import com.scu.holden.protectooth.utlis.PostUtils;
import com.scu.holden.protectooth.utlis.Tools;
import com.scu.holden.protectooth.utlis.UrlToList;
import com.scu.holden.protectooth.widget.CircleFlowIndicator;
import com.scu.holden.protectooth.widget.LayersLayout;
import com.scu.holden.protectooth.widget.PullToRefreshBase;
import com.scu.holden.protectooth.widget.ViewFlow;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mirrorssssssss on 5/18/17.
 */

@ContentView(R.layout.pull_to_refresh_list)
public class FragmentNews extends BaseFragment{


    @ViewInject(R.id.pulltorefreshlistview)
    PullToRefreshBase<?> pullToRefresh;

    @ViewInject(R.id.layerslayout)
    LayersLayout layersLayout;          // 自定义图层，用于对触屏事件进行重定向



    private ListView listView; // 下拉刷新的listview
    private ViewFlow viewFlow; // 进行图片轮播的viewFlow

    private int startNum = 0;
    private ArrayList<String> news;
    private Handler touchHandler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Intent intent = new Intent(getActivity(),contentActivity.class);

        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        LayoutInflater inflater = LayoutInflater.from(getContext().getApplicationContext());
        View header = inflater.inflate(R.layout.viewflow,null);

        CircleFlowIndicator indic=(CircleFlowIndicator)header.findViewById(R.id.viewflowindic);     // viewFlow下的indic

        listView = (ListView) pullToRefresh.getAdapterView();

        listView.addHeaderView(header); // 将viewFlow添加到listview中



        //以下与滑动图片新闻窗口有关
        viewFlow = (ViewFlow) header.findViewById(R.id.viewflow);// 获得viewFlow对象

        viewFlow.setAdapter(new ViewFlowAdapter(this.getContext())); // 对viewFlow添加图片

        viewFlow.setmSideBuffer(3);

        viewFlow.setFlowIndicator(indic);
        viewFlow.setTimeSpan(4500);//与窗口滑动时间相关
        viewFlow.setSelection(3 * 1000); // 设置初始位置
        viewFlow.startAutoFlowTimer(); // 启动自动播放
        // 处理轮播图片单击事件
        touchHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                int pageid = msg.arg1;
                String title = "经常口腔溃疡咋回事" ;
                switch (pageid){
                    case 0:
                        title = "经常口腔溃疡咋回事";
                        break;
                    case 1:
                        title = "男性比女性更易得牙周病！";
                        break;
                    case 2:
                        title = "导致牙痛的原因,巧治牙痛的常见偏方";
                        break;
                    default:
                        break;
                }
                Log.e("duolafang",title);
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            };
        };
        viewFlow.setTouchHandler(touchHandler);

        layersLayout.setView(viewFlow); // 将viewFlow对象传递给自定义图层，用于对事件进行重定向

        news = getNews("龋齿",startNum);
        PullToRefreshListAdapter ptr = new PullToRefreshListAdapter(getContext());
        ptr.setTexts(news);
        listView.setAdapter(ptr); // 绑定数据
        // 新闻列表单击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = news.get(position-1);
                //用Bundle携带数据
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp
                bundle.putString("title", title);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    // 获取新闻题目列表
    public ArrayList<String> getNews(final String label,final int start_num){
        ArrayList<String> news = new ArrayList<String>();
        final JSONObject[] return_Json={new JSONObject()};
        Thread workThread1=new Thread(){
            @Override
            public void run(){

                PostUtils postu = new PostUtils();
                String return_str = postu.get("http://119.29.246.19:7777/getNews/?label="+label+"&req=0&num="+start_num);
                return_Json[0] =JSONObject.fromObject(return_str);

            }
        };
        workThread1.start();
        //主线程等待workThread1执行完毕
        try {
            workThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray temp = (JSONArray) return_Json[0].get("titles");
        for(int i =0;i<temp.size();i++){
            news.add(i,(String) temp.get(i));
        }
        return news;
    }



}
