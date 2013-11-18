package cn.robotium.ui;


import java.io.Serializable;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import cn.robotium.utils.VerifyUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryParsActivity extends BaseActivity  implements OnClickListener {
	
	private LinearLayout titleLayout;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private RelativeLayout testTimeLayout,intervalTimeLayout;
	private EditText testTimeEditText, intervalTimeEditText;
	private String preMenu="";
	private long testTime, testInterval;
	private TestRecordBean bean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parameter_setting);
		init();
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleLeftbtn.setOnClickListener(this);
		titleRightBtn.setVisibility(View.VISIBLE);
		titleRightBtn.setOnClickListener(this);
		bean = (TestRecordBean) getIntent().getSerializableExtra("bean");
		if (bean.getTest_type() == TestType.MEMORYTEST) {
				titleTxt.setText(getString(R.string.memory_test_type));
		} else if (bean.getTest_type() == TestType.CPUTEST) {
				titleTxt.setText(getString(R.string.cpu_test_type));
		} else if (bean.getTest_type() == TestType.NETTEST) {
				titleTxt.setText(getString(R.string.net_test_type));
				intervalTimeLayout.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onResume() {
		if (bean.getTest_duration() != 0) {
			testTime = bean.getTest_duration();
			testInterval = bean.getTest_refresh_rate();
			testTimeEditText.setText(testTime + "");
			intervalTimeEditText.setText(testInterval + "");
		} 
		super.onResume();
	}
	
	
	private void init(){ 
		titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		titleTxt = (TextView) findViewById(R.id.title_txt);
		titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
		titleLeftbtn.setText(getString(R.string.cancel));
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
		testTimeLayout = (RelativeLayout) findViewById(R.id.testTimeLayout);
		intervalTimeLayout = (RelativeLayout) findViewById(R.id.intervalTimeLayout);
		testTimeEditText = (EditText) findViewById(R.id.settingTestTimeEditText);
		intervalTimeEditText = (EditText) findViewById(R.id.settingIntervalTimeEditText);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_left_btn:
			onBackPressed();
			break;
		case R.id.title_right_btn:
			if (bean.getTest_type() != 0 && bean.getTest_type() != 3) {
				if (VerifyUtil.isNullEditText(testTimeEditText,intervalTimeEditText, this, 
						getString(R.string.isNull_test_duration_or_refresh_rate))) {
				} else {
					onSave(bean);
					finish();
				}
			} else {
				if (VerifyUtil.isNullEditText(testTimeEditText,testTimeEditText, this,
						getString(R.string.isNull_test_duration_or_refresh_rate))) {
				} else {
					onSave(bean);;
					finish();
				}
			}
			break;	
		default:
			break;
		}
	}
	
	
	/**
	 * 根据TestType组装Intent
	 */
	public void onSave(TestRecordBean bean){
		Intent intent = new Intent(this,TestTypeActivity.class);
		if (bean.getTest_type() == TestType.MEMORYTEST) {
			setIntent(intent, false, bean);
			setResult(TestType.MEMORYTEST, intent);
		} else if (bean.getTest_type() == TestType.CPUTEST) {
			setIntent(intent, false, bean);
			setResult(TestType.CPUTEST, intent);
		} else if (bean.getTest_type() == TestType.NETTEST) {
			setIntent(intent, true, bean);
			setResult(TestType.NETTEST, intent);
		}
	}
	
	/**
	 * 把设置的参数存入bean中,如果选择的是流量，则不存testInterval
	 * @param intent
	 * @param isTestNet 测试类型是否是流量测试
	 */
	public void setIntent(Intent intent,  boolean isTestNet, TestRecordBean bean){
		testTime = Long.parseLong(testTimeEditText.getText().toString());
		bean.setTest_duration(testTime);
		if (isTestNet) {
		} else {
			testInterval = Integer.parseInt(intervalTimeEditText.getText().toString());
			bean.setTest_refresh_rate(testInterval);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", (Serializable) bean);
		intent.putExtras(bundle);
	}
	
	
}
