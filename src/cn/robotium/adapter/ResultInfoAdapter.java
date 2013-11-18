package cn.robotium.adapter;

import java.util.List;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultInfoAdapter extends BaseAdapter{
	private Context context;
	private List<TestRecordBean> list;
	private LayoutInflater inflater;
	
	public ResultInfoAdapter(Context context, List<TestRecordBean> list){
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
		ViewCache cache;
		if(convertView == null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.result_info_item, null);
			cache = new ViewCache();
			cache.readIcon = (ImageView) convertView.findViewById(R.id.hasread);
			cache.notreadIcon = (ImageView) convertView.findViewById(R.id.notread);
			cache.app_test_type = (TextView) convertView.findViewById(R.id.app_test_type);
			cache.app_test_time = (TextView) convertView.findViewById(R.id.app_test_time);
			convertView.setTag(cache);
		}else {
			cache = (ViewCache) convertView.getTag();
		}
		//绑定数据
		TestRecordBean bean = list.get(position);
		switch (bean.getTest_type()) {
		case TestType.CPUTEST:
			cache.app_test_type.setText("CPU测试");
			break;
			
		case TestType.MEMORYTEST:
			cache.app_test_type.setText("内存测试");
			break;
			
		case TestType.NETTEST:
			cache.app_test_type.setText("流量测试");
			break;

		default:
			cache.app_test_type.setText("测试");
			break;
		}
		
		if(bean.getHas_read()==0){
			cache.readIcon.setVisibility(View.GONE);
			cache.notreadIcon.setVisibility(View.VISIBLE);
		}else {
			cache.readIcon.setVisibility(View.VISIBLE);
			cache.notreadIcon.setVisibility(View.GONE);
		}
		cache.app_test_time.setText(bean.getLast_test_time());
		return convertView;
	}
	
	private final class ViewCache{
		private ImageView readIcon;
		private ImageView notreadIcon;
		private TextView app_test_type;
		private TextView app_test_time;
	}

}
