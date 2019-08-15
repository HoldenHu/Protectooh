package com.scu.holden.protectooth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.rong_chat.activity.ContactsActivity;
import com.scu.holden.protectooth.rong_chat.adapter.ContactsAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by mirrorssssssss on 11/6/17.
 */

@ContentView(R.layout.rong_contacts)
public class FragmentDoctorList extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener{
    @ViewInject(R.id.list)
    private ListView mListView;
    private ContactsAdapter mAdapter;
    @ViewInject(R.id.txt1)
    private TextView mTitle;

    /**
     * ids 收消息人的 id
     */
    String[] ids = {"张琳", "陆斌", "唐震", "史伟萍","李飞"};
    List mLists = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Holden","DoctorList->onCreateView");
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Holden", "DoctorList->onViewCreated");



        mAdapter = new ContactsAdapter(getActivity(), ids);
        mListView.setAdapter(mAdapter);

        for(int i = 0;i<ids.length;i++){
            mLists.add(ids[i]);
        }

        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (RongIM.getInstance() != null)
            RongIM.getInstance().startPrivateChat(getActivity(), ids[position], "title");
    }
    @Override
    public void onClick(View v) {

        if (RongIM.getInstance() != null)
        /**
         *创建讨论组时，mLists为要添加的讨论组成员，创建者一定不能在 mLists 中
         */
            RongIM.getInstance().getRongIMClient().createDiscussion("Hello Discussion", mLists, new RongIMClient.CreateDiscussionCallback() {
                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
    }

}
