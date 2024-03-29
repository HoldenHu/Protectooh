package com.scu.holden.protectooth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.holden.protectooth.R;


/**
 * 父ListView适配器
 * 
 * @author zihao
 * 
 */
public class GroupAdapter extends BaseAdapter {

	Context mContext;// 上下文对象
	String[] mGroupNameArr;// 父item标题数组
	int mPosition = 0;// 选中的位置

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            // 上下文对象
	 * @param groupArr
	 *            // item标题数组
	 */
	public GroupAdapter(Context context, String[] groupArr) {
		this.mContext = context;
		this.mGroupNameArr = groupArr;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		// 初始化布局控件
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.group_item_layout, null);
			holder.groupName = (TextView) convertView
					.findViewById(R.id.group_textView);
			holder.imageView=(ImageView)convertView.findViewById(R.id.group_photo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置控件内容
		holder.groupName.setText(mGroupNameArr[position]);
		if (mPosition == position) {
			holder.groupName.setTextColor(mContext.getResources().getColor(
					R.color.colorPrimary));
			holder.imageView.setImageResource(R.drawable.small_tooth);
			convertView.setBackgroundColor(mContext.getResources().getColor(
					R.color.group_item_pressed_bg));
		} else {
			holder.groupName.setTextColor(mContext.getResources().getColor(
					android.R.color.black));
			holder.imageView.setImageResource(R.drawable.small_tooth_gray);
			convertView.setBackgroundColor(mContext.getResources().getColor(
					R.color.group_item_bg));
		}

		return convertView;
	}

	/**
	 * 获取item总数
	 */
	@Override
	public int getCount() {
		return mGroupNameArr.length;
	}

	/**
	 * 获取某一个Item的内容
	 */
	@Override
	public Object getItem(int position) {
		return mGroupNameArr[position];
	}

	/**
	 * 获取当前item的ID
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		/** 父Item名称 **/
		ImageView imageView;
		TextView groupName;
	}

	/**
	 * 设置选择的位置
	 * 
	 * @param position
	 */
	public void setSelectedPosition(int position) {
		this.mPosition = position;
	}

}
