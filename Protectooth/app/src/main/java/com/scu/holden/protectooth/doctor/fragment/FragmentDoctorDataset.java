package com.scu.holden.protectooth.doctor.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.fragment.BaseFragment;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import com.scu.holden.protectooth.adapter.ChildAdapter;
import com.scu.holden.protectooth.adapter.GroupAdapter;

/**
 * Created by mirrorssssssss on 11/3/17.
 */

@ContentView(R.layout.fragment_doctor_dataset)
public class FragmentDoctorDataset extends BaseFragment {

    @ViewInject(R.id.listView1)
    ListView groupListView;
    @ViewInject(R.id.listView2)
    ListView childListView;
    GroupAdapter groupAdapter = null;
    ChildAdapter childAdapter = null;

    /** 一级菜单名称数组 **/
    String[] GroupNameArray = { "牙周病", "牙髓病", "口腔菌病", "龋病", "口腔溃疡", "四环素牙"};
    /** 二级菜单名称数组 **/
    String[][] childNameArray = {
            { "龈炎", "牙周炎", "牙周创伤", "青少年牙周炎", "牙周萎缩"},
            { "可复性牙髓炎", "急性牙髓炎", "慢性牙髓炎", "逆行性牙髓炎", "牙髓坏死", "牙髓钙化", "牙内吸收" },
            { "急性假膜型", "急性红斑型", "慢性肥厚型", "慢性萎缩型", "黏膜皮肤念珠菌病"},
            { "典型中龋处理案例","典型深龋处理案例","湿性龋处理案例","浅龋诊断标准","龋洞消毒方法","术区隔离方法","深龋手术案例","龋病术后护理方法"},
            { "轻型口疮", "疱疹样口疮", "腺周口疮", "白塞综合征"},
            { "轻度四环素牙", "中度四环素牙", "重度四环素牙"} };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Holden","doctorhome->onCreateView");
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int i = Log.i("Holden", "doctorhome->onViewCreated");

        groupAdapter = new GroupAdapter(getActivity(), GroupNameArray);
        groupListView.setAdapter(groupAdapter);


        groupListView.setOnItemClickListener(new MyItemClick());

        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 20:
                    childAdapter.setChildData(childNameArray[msg.arg1]);
                    childAdapter.notifyDataSetChanged();
                    groupAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }

        };
    };

    class MyItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            groupAdapter.setSelectedPosition(position);

            if (childAdapter == null) {
                childAdapter = new ChildAdapter(getActivity());
                childListView.setAdapter(childAdapter);
            }

            Message msg = new Message();
            msg.what = 20;
            msg.arg1 = position;
            handler.sendMessage(msg);
        }

    }

}
