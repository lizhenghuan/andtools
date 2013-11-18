package cn.robotium.ui;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import cn.robotium.utils.VerifyUtil;

public class MonkeyParsActivity extends BaseActivity implements OnClickListener {
	
	private LinearLayout titleLayout;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private RelativeLayout monekyLayout;
	private EditText testBoutsEditText, testThrottleEditeText;
	private RelativeLayout ignoreCrashesLayout, ignoreTimeOutLayout;
	private TestRecordBean bean;
	private CheckBox ignoreCrhChk, ignoreTimeoutChk;
	private String isIgnoreCrhChk = "", isIgnoreTimeoutChk = "";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parameter_monkey);
		init();
		initData();
		setListener();
		
	}
	
	private void init(){ 
		titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		titleTxt = (TextView) findViewById(R.id.title_txt);
		titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleRightBtn.setVisibility(View.VISIBLE);
		monekyLayout = (RelativeLayout) findViewById(R.id.monekyLayout);
		testBoutsEditText = (EditText) findViewById(R.id.settingTestBoutsEditText);
		testThrottleEditeText = (EditText) findViewById(R.id.settingThrottleEditText);
		ignoreCrashesLayout = (RelativeLayout) findViewById(R.id.ignoreCrashesLayout);
		ignoreTimeOutLayout = (RelativeLayout) findViewById(R.id.ignoreTimeOutLayout);
		ignoreCrhChk = (CheckBox) findViewById(R.id.ignore_crashes_chk);
		ignoreTimeoutChk = (CheckBox) findViewById(R.id.ignore_timeout_chk);
	}
	
	private void initData(){
		titleLeftbtn.setText(getString(R.string.cancel));
		titleTxt.setText(getString(R.string.monkey_test));
	}
	
	private void setListener(){
		titleLeftbtn.setOnClickListener(this);
		titleRightBtn.setOnClickListener(this);
		ignoreCrashesLayout.setOnClickListener(this);
		ignoreTimeOutLayout.setOnClickListener(this);
	}


	@Override
	protected void onResume() {
		bean = (TestRecordBean) getIntent().getSerializableExtra("bean");
		if (bean.getTest_bouts() != null) {
			testBoutsEditText.setText(bean.getTest_bouts());
			testThrottleEditeText.setText(bean.getThrottle());
			if (!bean.getIgnoreCrashes().equals("")) {
				ignoreCrhChk.setChecked(true);
			}
			if (!bean.getIgnoreTimouts().equals("")) {
				ignoreTimeoutChk.setChecked(true);
			}
		} 
		super.onResume();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_left_btn:
			onBackPressed();
			break;
		case R.id.ignoreCrashesLayout:
			ignoreCrhChk.setChecked(!ignoreCrhChk.isChecked());
			break;
		case R.id.ignoreTimeOutLayout:
			ignoreTimeoutChk.setChecked(!ignoreTimeoutChk.isChecked());
			break;
		case R.id.title_right_btn:
			if (VerifyUtil.isNullEditText(testBoutsEditText, testThrottleEditeText, this,
					getString(R.string.isNull_test_bouts_or_throttle))) {
			} else {
				onSave(bean);
				finish();
			}
			break;
        default:
            break;
		}
	}
	
		/**
		 * 存储数据
		 * @param bean
		 */
		public void onSave(TestRecordBean bean){
			Intent intent = new Intent(this,TestTypeActivity.class);
			Bundle bundle = new Bundle();
			bean.setTest_bouts(testBoutsEditText.getText().toString());
			bean.setThrottle(testThrottleEditeText.getText().toString());
			if (ignoreCrhChk.isChecked()) {
				bean.setIgnoreCrashes("--ignore-crashes");
			} else {
				bean.setIgnoreCrashes("");
			}
			if (ignoreTimeoutChk.isChecked()) {
				bean.setIgnoreTimouts("--ignore-timeouts");
			} else {
				bean.setIgnoreTimouts("");
			}
			bundle.putSerializable("bean", (Serializable) bean);
			intent.putExtras(bundle);
			setResult(TestType.MEMORYTEST, intent);
		}
		
}


