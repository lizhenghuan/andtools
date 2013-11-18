package cn.robotium.adapter;

import java.util.HashMap;
import java.util.List;
import cn.robotium.AppInfo;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ExAdapter extends BaseExpandableListAdapter{
	
	String[] generalsTypes = null;
	LayoutInflater infater = null;
	List<AppInfo> userAppList;
	List<AppInfo> sysAppList;
	HashMap<Integer, List<AppInfo>> hh;
	Context context;
	//设置组视图的显示文字


	public ExAdapter(Context context,String userNumber, String sysNumber,HashMap<Integer, List<AppInfo>> hh ){
		this.context = context;
		generalsTypes = new String[] { "我的应用 "+userNumber, "系统应用 "+sysNumber};
		this.hh= hh;
		
	}




	//自己定义一个获得文字信息的方法
	TextView getTextView() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView textView = new TextView(
				context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(36, 0, 0, 0);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}


	//重写ExpandableListAdapter中的各个方法
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
//		return generalsTypes.length;
		return hh.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
//		return generalsTypes[groupPosition];
		return hh.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
//		return generals[groupPosition].length;
		return hh.get(groupPosition).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
//		return generals[groupPosition][childPosition];
		return hh.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout ll = new LinearLayout(
				context);
		ll.setOrientation(0);
//		ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 70));
		ll.setBackgroundColor(Color.parseColor("#EDEDED"));
		TextView textView = getTextView();
		textView.setTextColor(Color.parseColor("#ADADAD"));
		textView.setTextSize(17);
		textView.setText(generalsTypes[groupPosition]);
		ll.addView(textView);
		return ll;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout ll = new LinearLayout(
				context);
//		ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		ll.setOrientation(0);
		ll.setPadding(12, 12, 0, 12);
		ImageView generallogo = new ImageView(
				context);
		generallogo.setAdjustViewBounds(true);
		generallogo.setScaleType(ScaleType.CENTER);
		generallogo.setMaxHeight(70);
		generallogo.setMaxWidth(70);
		generallogo.setImageDrawable((hh.get(groupPosition).get(childPosition)).getAppIcon());
		ll.addView(generallogo);
		TextView textView = getTextView();
		textView.setText((hh.get(groupPosition).get(childPosition)).getAppLabel());
		textView.setTextSize(18);
		textView.setTextColor(Color.parseColor("#5B5B5B"));
//		TextView.setGravity(Gravity.CENTER);//居中
//		TextView.setGravity(Gravity.CENTER_HORIZONTAL);//水平居中
		textView.setGravity(Gravity.CENTER_VERTICAL);//垂直居中
		ll.addView(textView);
		return ll;
	}

	@Override
	public boolean isChildSelectable(int groupPosition,
			int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
