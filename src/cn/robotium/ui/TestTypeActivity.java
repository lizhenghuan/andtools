package cn.robotium.ui;

import java.io.Serializable;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestTypeActivity extends BaseActivity implements OnClickListener{
	
	private TextView titleTxt,memoryTxt,cpuTxt,netTxt,monkeyTxt;
	private Button titleLeftbtn, titleRightBtn;
	private RelativeLayout memoryLayout,cpuLayout,netLayout, monkeyLayout;
	private long testTime, intervalTime;
	private int  testType = 0;
	private String memoryPars,cpuPars,netPars,monkeyPars,test_bouts;
	private Intent intentForNextPage;
	private TestRecordBean bean;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applist_item);
		init();
		titleTxt.setText(getString(R.string.test_class));
		titleLeftbtn.setText(getString(R.string.cancel));
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleRightBtn.setVisibility(View.VISIBLE);
		titleRightBtn.setOnClickListener(this);
		
	}
	
	private void init(){
		
		titleTxt = (TextView) findViewById(R.id.title_txt);
		titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
		memoryLayout = (RelativeLayout) findViewById(R.id.memoryLayout);
		cpuLayout = (RelativeLayout) findViewById(R.id.cpuLayout);
		netLayout = (RelativeLayout) findViewById(R.id.netLayout);
		monkeyLayout = (RelativeLayout) findViewById(R.id.monkeyLayout);
		monkeyLayout.setVisibility(View.GONE);
		memoryTxt = (TextView) findViewById(R.id.memory);
		cpuTxt = (TextView) findViewById(R.id.cpu);
		netTxt = (TextView) findViewById(R.id.net);
		monkeyTxt = (TextView) findViewById(R.id.monkey);
		titleLeftbtn.setOnClickListener(this);
		memoryLayout.setOnClickListener(this);
		netLayout.setOnClickListener(this);
		cpuLayout.setOnClickListener(this);
		monkeyLayout.setOnClickListener(this);
		bean = (TestRecordBean) getIntent().getSerializableExtra("bean");
		if (0 == bean.getTest_type()) {
		} else {
			receiver(bean);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intentPars = new Intent(this, MemoryParsActivity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.title_left_btn:
			onBackPressed();
			break;
		case R.id.memoryLayout:
				if (!memoryTxt.getText().equals("")) {
					bean.setTest_duration(testTime);
					bean.setTest_refresh_rate(intervalTime);
				} 
				bean.setTest_type(TestType.MEMORYTEST);
				bundle.putSerializable("bean", (Serializable) bean);
				intentPars.putExtras(bundle);
				startActivityForResult(intentPars, TestType.MEMORYTEST);
			break;
		case R.id.cpuLayout:
				if (!cpuTxt.getText().equals("")) {
					bean.setTest_duration(testTime);
					bean.setTest_refresh_rate(intervalTime);
				} 
				bean.setTest_type(TestType.CPUTEST);
				bundle.putSerializable("bean", (Serializable) bean);
				intentPars.putExtras(bundle);
				startActivityForResult(intentPars, TestType.CPUTEST);
			break;
		case R.id.netLayout:
				if (!netTxt.getText().equals("")) {
					bean.setTest_duration(testTime);
					bean.setTest_refresh_rate(intervalTime);
				} 
				bean.setTest_type(TestType.NETTEST);
				bundle.putSerializable("bean", (Serializable) bean);
				intentPars.putExtras(bundle);
				startActivityForResult(intentPars, TestType.NETTEST);
			break;
		case R.id.monkeyLayout:
			Intent intentMonkey = new Intent(this, MonkeyParsActivity.class);
			bean.setTest_type(TestType.MONKEYTEST);
			bundle.putSerializable("bean", (Serializable) bean);
			intentMonkey.putExtras(bundle);
			startActivityForResult(intentMonkey, TestType.MONKEYTEST);
			break;
		case R.id.title_right_btn:
			Intent intentSave = new Intent(this, MainActivity.class);
			if (memoryTxt.getText().equals("") &&
					cpuTxt.toString().equals("") &&
					netTxt.toString().equals("") &&
					monkeyTxt.toString().equals("")) {
				Toast.makeText(this, getString(R.string.choose_testType_toast), 0).show();
			} else {
				bundle.putSerializable("bean", (Serializable) bean);
				intentSave.putExtras(bundle);
				setResult(TestType.CHOOSE_TEST_TYPE, intentSave);
				finish();
			}
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null == data) {
            return;
        }
		bean = (TestRecordBean) data.getSerializableExtra("bean");
        switch (requestCode) {
            case TestType.MEMORYTEST:
            	receiver(bean);
                break;
            case TestType.CPUTEST:
            	receiver(bean);
            	break;
            case TestType.NETTEST:
            	receiver(bean);
            	break;
            case TestType.MONKEYTEST:
            	receiver(bean);
            	break;
            default:
                break;
        }
	}
	
	
    
    /**
     * 解析从下级页面接收到的Intent，并解析出所需的信息
     * @param data
     */
    public void receiver(TestRecordBean bean){
    	if (bean.getTest_duration() != 0 || bean.getTest_bouts() != null) {
    		testType = bean.getTest_type();
			testTime = bean.getTest_duration();
			test_bouts = bean.getTest_bouts();
			intervalTime = bean.getTest_refresh_rate();
			setTextVaule();
		}
    }
    
    public void setTextVaule(){
    	if (testType == TestType.CPUTEST) {
			cpuPars = getString(R.string.detail_test_duration) +testTime+getString(R.string.test_refresh_rate) +intervalTime;
			cpuTxt.setText(cpuPars);
			memoryTxt.setText("");
			netTxt.setText("");
			monkeyTxt.setText("");
		} else if (testType == TestType.MEMORYTEST) {
			memoryPars = getString(R.string.detail_test_duration) + testTime +getString(R.string.test_refresh_rate) +intervalTime;
			memoryTxt.setText(memoryPars);
			cpuTxt.setText("");
			netTxt.setText("");
			monkeyTxt.setText("");
		} else if (testType == TestType.NETTEST) {
		    netPars = getString(R.string.detail_test_duration) +testTime;
		    netTxt.setText(netPars);
		    memoryTxt.setText("");
		    cpuTxt.setText("");
		    monkeyTxt.setText("");
		} else if (testType == TestType.MONKEYTEST) {
			monkeyPars = getString(R.string.test_bouts) + test_bouts;
			monkeyTxt.setText(monkeyPars);
			memoryTxt.setText("");
			cpuTxt.setText("");
			netTxt.setText("");
		}
    }
    
}
