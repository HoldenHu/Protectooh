package com.scu.holden.protectooth.rong_chat.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scu.holden.protectooth.R;

import java.security.PrivateKey;

/**
 * Created by Bob on 15/8/21.
 */
public class ContactsAdapter extends BaseAdapter {

    private String[] mNameArray;
    private LayoutInflater mLayoutInflater;
    private String[] Doctor_position={"口腔颌面科  医师","口腔颌面科  副主任医师","口腔颌面科  副主任医师","口腔颌面科  医师","口腔颌面科  医师"};
    private String[] Doctor_hospital={"哈尔滨医科大学附属第一医院","中国人民解放军第四军医大学口腔医院","第二军医大学附属长海医院","浙江大学医学院附属第二医院","上海市第九人民医院"};
    private String[] details={"擅长：牙齿矫正、儿童牙病、牙龈炎、牙髓炎、口腔异味、镶牙","擅长：牙龈出血、智齿冠周炎、唇腭裂、牙痛、口臭、口腔溃疡","擅长：种植牙、口腔颌面部肿瘤、外伤、牙齿美白、洗牙、根管治疗","擅长：美容修复、种植牙、洗牙、蛀牙、牙龈炎、口腔溃疡","擅长：美容修复、种植牙、洗牙、蛀牙、牙龈炎、口腔溃疡","擅长：口腔疾病、洗牙、根管治疗、蛀牙、牙周病、烤瓷牙"};
    private int[] doctor_head={R.drawable.zhanglin,R.drawable.lubin,R.drawable.tangzheng,R.drawable.shiweiping,R.drawable.lifei};

    public ContactsAdapter(Context context, String[] names) {
        mNameArray = names;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mNameArray == null)
            return 0;
        return mNameArray.length;
    }

    @Override
    public Object getItem(int position) {
        if (mNameArray == null || mNameArray.length >= position)
            return null;
        return mNameArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {

            convertView = mLayoutInflater.inflate(R.layout.rong_item_contacts,
                    parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.titleTextView = (TextView) convertView
//                    .findViewById(R.id.txt1);
//            viewHolder.view_line=(View)convertView
//                    .findViewById(R.id.divide_line);
            viewHolder.linearLayout=(LinearLayout) convertView
                    .findViewById(R.id.item_list_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextView textView=(TextView) convertView.findViewById(R.id.txt1);
        textView.setText(mNameArray[position]);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.chat_doctor_head);
        imageView.setImageResource(doctor_head[position]);
        TextView textView_position=(TextView) convertView.findViewById(R.id.txt_position);
        textView_position.setText(Doctor_position[position]);
        TextView textView_hospital=(TextView) convertView.findViewById(R.id.txt_hospital);
        textView_hospital.setText(Doctor_hospital[position]);
        TextView textView_details=(TextView) convertView.findViewById(R.id.txt_details);
        textView_details.setText(details[position]);

        return convertView;
    }

    static class ViewHolder {
        LinearLayout linearLayout;
//        TextView titleTextView;
//        View view_line;
    }
}
