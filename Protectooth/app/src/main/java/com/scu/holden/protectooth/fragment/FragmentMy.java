package com.scu.holden.protectooth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.rong_chat.activity.MainActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.rong.imkit.RongIM;

@ContentView(R.layout.activity_personpage)
public class FragmentMy extends BaseFragment  {
    @ViewInject(R.id.my_doctor)
    LinearLayout layout;
    @ViewInject(R.id.verify_to_doctor)
    LinearLayout layout_verify;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Holden","home->onCreateView");
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int i = Log.i("Holden", "home->onViewCreated");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
            }
        });

        layout_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String packageName = "com.scu.holden.protectooth";
                String className = "com.scu.holden.protectooth.VerifyActivity";
                intent.setClassName(packageName, className);
                startActivity(intent);
            }
        });
    }
}
	
