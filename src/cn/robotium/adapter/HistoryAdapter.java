package cn.robotium.adapter;

import java.util.List;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.database.DBHelper;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {
	
	private Context context;
	private List<TestRecordBean> list;
	private LayoutInflater inflater;
	
	public HistoryAdapter(Context context, List<TestRecordBean> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		ImageView appIcon;
//		TextView appLabel;
//		TextView date;
		ViewCache cache;
		if(convertView == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.history_item, null);
			cache = new ViewCache();
			cache.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
			cache.notReadCount = (TextView) convertView.findViewById(R.id.not_read_count);
			cache.appLabel = (TextView) convertView.findViewById(R.id.app_label);
			cache.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(cache);
		}else {
			cache = (ViewCache) convertView.getTag();
		}
		//绑定数据
		TestRecordBean bean = list.get(position);
		try {
			cache.appIcon.setImageDrawable(context.getPackageManager().getApplicationIcon(bean.getPkgname()));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = DBHelper.getInstance(context).getNotReadRecord(bean.getPkgname());
		if(count>0){
			cache.notReadCount.setVisibility(View.VISIBLE);
			cache.notReadCount.setText(String.valueOf(count));
		}else {
			cache.notReadCount.setVisibility(View.GONE);
		}
		String applabel = bean.getApplabel();
		String newapplabel = null;
		if(applabel.length()>=4){
			newapplabel = new StringBuffer(applabel.substring(0, 4)).append("...").toString();
			cache.appLabel.setText(newapplabel);
		}else {
			cache.appLabel.setText(applabel);
		}
//		cache.appLabel.setText(bean.getApplabel());
		
		cache.date.setText(bean.getLast_test_time());
		return convertView;
	}
	
	private final class ViewCache{
		private ImageView appIcon;
		private TextView notReadCount;
		private TextView appLabel;
		private TextView date;
	}

}
