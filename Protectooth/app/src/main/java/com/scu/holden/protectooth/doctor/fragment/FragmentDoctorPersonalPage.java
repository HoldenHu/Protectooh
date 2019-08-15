package com.scu.holden.protectooth.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scu.holden.protectooth.R;
import com.scu.holden.protectooth.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import io.rong.imkit.RongIM;

/**
 * Created by mirrorssssssss on 11/3/17.
 */

@ContentView(R.layout.fragment_doctor_page)
public class FragmentDoctorPersonalPage extends BaseFragment {
    @ViewInject(R.id.my_patient)
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Holden","doctorhome->onCreateView");
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int i = Log.i("Holden", "doctorhome->onViewCreated");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startConversationList(getActivity());
            }
        });
    }
}
