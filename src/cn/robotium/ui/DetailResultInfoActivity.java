package cn.robotium.ui;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import cn.robotium.database.DBHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailResultInfoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout titleLayout;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private ListView historyListView;
	private TestRecordBean bean;
	private Intent intent;
	
	private TextView app_label;
	private TextView test_duration;
	private TextView test_refresh_rate;
	private TextView cpu_avg_use;
	private TextView cpu_max_use;
	private TextView memory_avg_use;
	private TextView memory_max_use;
	private TextView net_total_use;
	private TextView test_result_report;
	
	private RelativeLayout avg_cpu_result;
	private RelativeLayout max_cpu_result;
	private RelativeLayout avg_memory_result;
	private RelativeLayout max_memory_result;
	private RelativeLayout net_total_result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		intent = getIntent();
		bean = (TestRecordBean) intent.getExtras().get("bean");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_result);
		initTitleLayout();
		initViewByType(bean);
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleRightBtn.setVisibility(View.VISIBLE);
		titleLeftbtn.setOnClickListener(this);
		titleRightBtn.setOnClickListener(this);
		String appName = bean.getApplabel();
		
		//设置已读
		if(bean.getHas_read()==0){
			DBHelper.getInstance(this).setHasRead(bean.getTest_id());
		}
		switch (bean.getTest_type()) {
		case TestType.CPUTEST:
			titleTxt.setText(getString(R.string.cpu_test_type));
			break;

		case TestType.MEMORYTEST:
			titleTxt.setText(getString(R.string.memory_test_type));
			break;

		case TestType.NETTEST:
			titleTxt.setText(getString(R.string.net_test_type));
			break;

		default:
			titleTxt.setText("");
			break;
		}
		
		

		if(!appName.equals("")){
			
		}
	}


	private void initTitleLayout(){
		titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		titleTxt = (TextView) findViewById(R.id.title_txt);
		titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
	}
	
	
	private void initViewByType(TestRecordBean bean){
		switch (bean.getTest_type()) {
		case TestType.CPUTEST:
			initFixedView(bean);
			avg_cpu_result = (RelativeLayout) findViewById(R.id.avg_cpu_result);
			max_cpu_result = (RelativeLayout) findViewById(R.id.max_cpu_result);
			avg_cpu_result.setVisibility(View.VISIBLE);
			max_cpu_result.setVisibility(View.VISIBLE);
			cpu_avg_use = (TextView) findViewById(R.id.cpu_avg_use);
			cpu_max_use = (TextView) findViewById(R.id.cpu_max_use);
			cpu_avg_use.setText(bean.getCpu_avg_use()+"%");
			cpu_max_use.setText(bean.getCpu_max_use()+"%");
			
			break;
			
		case TestType.MEMORYTEST:
			initFixedView(bean);
			avg_memory_result = (RelativeLayout) findViewById(R.id.avg_memory_result);
			max_memory_result = (RelativeLayout) findViewById(R.id.max_memory_result);
			avg_memory_result.setVisibility(View.VISIBLE);
			max_memory_result.setVisibility(View.VISIBLE);
			memory_avg_use = (TextView) findViewById(R.id.memory_avg_use);
			memory_max_use = (TextView) findViewById(R.id.memory_max_use);
			memory_avg_use.setText(bean.getMemory_avg_use()+"M");
			memory_max_use.setText(bean.getMemory_max_use()+"M");
			break;
			
		case TestType.NETTEST:
			initFixedView(bean);
			net_total_result = (RelativeLayout) findViewById(R.id.net_total_result);
			net_total_result.setVisibility(View.VISIBLE);
			net_total_use = (TextView) findViewById(R.id.net_total_use);
			net_total_use.setText(bean.getNet_total_use()+"KB");
			break;

		default:
			break;
		}
	}
	
	private void initFixedView(TestRecordBean bean){
		app_label = (TextView) findViewById(R.id.app_label);
		test_duration = (TextView) findViewById(R.id.test_duration);
		test_refresh_rate = (TextView) findViewById(R.id.test_refresh_rate);
		test_result_report = (TextView) findViewById(R.id.test_result_report);
		
		app_label.setText(bean.getApplabel());
		test_duration.setText(String.valueOf(bean.getTest_duration())+"毫秒");
		test_refresh_rate.setText(String.valueOf(bean.getTest_refresh_rate())+"毫秒");
		
		if(!(bean.getTest_result() + "").equals("")){
			test_result_report.setText(bean.getTest_result());
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_left_btn:
			onBackPressed();
			break;
		case R.id.title_right_btn:
			DBHelper.getInstance(this).updateTestResult(bean.getTest_id(), test_result_report.getText().toString());
			Toast.makeText(this, getString(R.string.success_save), 1).show();
			break;
		default:
			break;
		}
	}

}
