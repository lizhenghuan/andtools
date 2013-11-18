package cn.robotium.ui;


import java.io.IOException;
import java.io.Serializable;

import cn.robotium.AppInfo;
import cn.robotium.AppListInfo;
import cn.robotium.CpuInfo;
import cn.robotium.MemoryInfo;
import cn.robotium.MonkeyTest;
import cn.robotium.NetInfo;
import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import cn.robotium.database.DBHelper;
import cn.robotium.utils.AndTimeUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends BaseActivity implements OnClickListener{

	private LinearLayout titleLayout;
	private RelativeLayout managerLayout;
	private RelativeLayout testLayout;
	private RelativeLayout historyLayout;
	private TextView titleTxt;
	private TextView appManagerTextView,testTypeTextView;
	private long exitTime = 0;
	private String pkgName = "", label="";
	private Button startTesting;
	private long testTime, testInterval;
	private int testType;
	private TestRecordBean bean;
	

	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initTitleLayout();
        titleTxt.setText(R.string.homepage);
        managerLayout = (RelativeLayout) findViewById(R.id.app_manager);
        testLayout = (RelativeLayout) findViewById(R.id.test_class);
        historyLayout = (RelativeLayout) findViewById(R.id.history);
        managerLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);
        historyLayout.setOnClickListener(this);
        startTesting.setOnClickListener(this);
        bean = new TestRecordBean();
        
    }
    
    private void initTitleLayout(){
        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        titleTxt = (TextView) findViewById(R.id.title_txt);
        appManagerTextView = (TextView) findViewById(R.id.appManagerTextView);
        testTypeTextView = (TextView) findViewById(R.id.testTypeTextView);
        startTesting = (Button) findViewById(R.id.start_test);
    }
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.app_manager:
			Intent intent = new Intent(this, AppManagerActivity.class);
			bundle.putSerializable("bean", (Serializable) bean);
			intent.putExtras(bundle);
			startActivityForResult(intent, TestType.CHOOSE_APP);
			break;
		case R.id.test_class:
			Intent intent2 = new Intent(this, TestTypeActivity.class);
//			if (!testTypeTextView.getText().equals("")) {
//				bean.setTest_duration(testTime);
//				bean.setTest_refresh_rate(testInterval);
//				bean.setTest_type(testType);
//			}
			bundle.putSerializable("bean", (Serializable) bean);
			intent2.putExtras(bundle);
			startActivityForResult(intent2, TestType.CHOOSE_TEST_TYPE);
			break;
		case R.id.history:
			intent2Choose(HistoryActivity.class, getString(R.string.history));
			break;
		case R.id.start_test:
			if (pkgName.length() == 0) {
				Toast.makeText(this, getString(R.string.choose_app_toast), 0).show();
			} else if (testType == 4 || testType == 0){
				Toast.makeText(this, getString(R.string.choose_testType_toast), 0).show();
			} else {
				startTesting.setText(getString(R.string.testing));
				startTesting.setClickable(false);
				Thread t = new Thread(startThread);
				t.start();
			}
			break;
		default:
			break;
		}
	}

		Handler handler  = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					//更新按钮
					startTesting.setText(R.string.begin_test);
					startTesting.setClickable(true);
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	    //将要执行的操作写在线程对象的run方法当中
	    Runnable startThread =  new Runnable() {
			@Override
			public void run() {
				Intent startApp = getPackageManager().getLaunchIntentForPackage(pkgName);
				startActivity(startApp);
				startCalc(testTime, testInterval,testType);
				//添加通知
				showNotification(label);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		};
	    
	    
	    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (null == data) {
	            return;
	        }
	        switch (requestCode) {
	            case TestType.CHOOSE_APP:
	            	receiveFromApp(data);
	                break;
	            case TestType.CHOOSE_TEST_TYPE:
	            	receviceFromTest(data);
	            	break;
	            default:
	                break;
	        }
	}
	
	
	
	 /**
     * 接收从AppList页面所传过来的参数,并赋值给当前页面接收的appManagerTextView
     */
    private void receiveFromApp(Intent data) {
    	bean = (TestRecordBean) data.getSerializableExtra("bean");
        pkgName = bean.getPkgname();
        label = bean.getApplabel();
        appManagerTextView.setText(label);
    }
	
    /**
     * 接收从TestType页面所传过来的参数,并赋值给当前页面接收的testTypeTextView
     */
    public void receviceFromTest(Intent data){
    	bean = (TestRecordBean) data.getSerializableExtra("bean");
    	System.out.println("我是bean" + bean);
		testTime = bean.getTest_duration();
		testInterval = bean.getTest_refresh_rate();
		testType = bean.getTest_type();
		if (testType == TestType.CPUTEST) {
			testTypeTextView.setText(getString(R.string.cpu_test_type));
		} else if (testType == TestType.MEMORYTEST) {
			testTypeTextView.setText(getString(R.string.memory_test_type));
			} else if (testType == TestType.NETTEST) {
				testTypeTextView.setText(getString(R.string.net_test_type));
				}else if (testType == TestType.MONKEYTEST) {
					testTypeTextView.setText(getString(R.string.monkey_test));
				}
	}

    /**
     * 自定义页面跳转
     * @param classname
     * @param str
     */
	public void intent2Choose(Class classname, String str){
		Intent intent = new Intent(this, classname);
		intent.putExtra("test", str);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClassName(this, classname.getName());
		startActivity(intent);
	}
	
	
	/**
	 * 系统通知
	 * @param appLabel
	 */
	public void showNotification(String appLabel){
		//创建一个notification调用
		NotificationManager notificationManager = (NotificationManager)
				this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		//定义notification的属性
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.defaults= Notification.DEFAULT_SOUND;
		 // 设置通知的事件消息  
		// 通知栏标题、通知栏内容  
		CharSequence contentTitle =getString(R.string.contentTitle);   
		CharSequence contentText = appLabel + getString(R.string.contentText); 
		// 点击该通知后要跳转的Activity  
		Intent notificationIntent =new Intent(this, HistoryActivity.class); 
		PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);  
		notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);  
		// 把Notification传递给NotificationManager 
		notificationManager.notify(0, notification);
	}
	
		//删除通知   
	    private void clearNotification(){
	    // 启动后删除之前我们定义的通知  
	    NotificationManager notificationManager = (NotificationManager) this
	                .getSystemService(NOTIFICATION_SERVICE);  
	        notificationManager.cancel(0); 
	    }

	
	
	/**
	 * 应用启动后，开始计算相应的测试
	 */
	public void startCalc(long time, long interval, int testTpye){
		
		AppListInfo appListInfo = new AppListInfo(this);
		AppInfo appInfo = appListInfo.getAppInfoBypkgName(pkgName);
//		TestRecordBean bean = new TestRecordBean();
		String testData;
		AndTimeUtils timeUtils = new AndTimeUtils();
		testData = timeUtils.getCurrentTimeToString();
//		bean.setApplabel(label);
//		bean.setPkgname(pkgName);
//		bean.setTest_duration(time);
//		bean.setLast_test_time(testData);
//		bean.setTest_refresh_rate(interval);
		if (testTpye == TestType.CPUTEST) {
			bean.setTest_type(testTpye);
			CpuInfo cpuInfo = new CpuInfo(appInfo.getPid(), time, interval);
			cpuInfo.getCpuUsage(bean);
			DBHelper.getInstance(this).insertData(bean);
		} else if (testTpye == TestType.MEMORYTEST) {
			bean.setTest_type(testTpye);
			MemoryInfo memoryInfo = new MemoryInfo();
			memoryInfo.calcMemory(appInfo.getPid(), time, interval, this, bean);
			DBHelper.getInstance(this).insertData(bean);
		} else if (testTpye == TestType.NETTEST) {
			bean.setTest_type(testTpye);
			NetInfo netInfo = new NetInfo(appInfo.getUid(), time);
			netInfo.calcAppNetInfo(bean);
			DBHelper.getInstance(this).insertData(bean);
		} else if (testTpye == TestType.MONKEYTEST) {
			bean.setTest_type(testTpye);
//			MonkeyTest monkeyTest = new MonkeyTest("monkey -v " + bean.getIgnoreCrashes() + " " + bean.getIgnoreTimouts()
//					+ " " + "--throttle" + " " + bean.getThrottle() + " " +"-p " + bean.getPkgname() + " " + bean.getTest_bouts());
		}
	}
	
	
	
	/**
	 * 返回键退出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), getString(R.string.back_key), 0).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
