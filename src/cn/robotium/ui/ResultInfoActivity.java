package cn.robotium.ui;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import cn.robotium.R;
import cn.robotium.adapter.HistoryAdapter;
import cn.robotium.adapter.ResultInfoAdapter;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.database.DBHelper;
import cn.robotium.utils.AndTimeUtils;
import cn.robotium.utils.FileUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResultInfoActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener, OnClickListener {
	private LinearLayout titleLayout;
	private RelativeLayout managerLayout;
	private RelativeLayout testLayout;
	private RelativeLayout historyLayout;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private Intent intent;
	private TestRecordBean bean;
	private String title;
	private List<TestRecordBean> list;
	private ListView reinfo_listView;
	private ResultInfoAdapter adapter;
	private String path = "mnt/sdcard/AndTools";
	private String data="";
	private ProgressDialog dialogsss_export;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_info);
		initTitleLayout();
		reinfo_listView = (ListView) findViewById(R.id.reinfo_listView);
		reinfo_listView.setOnItemClickListener(this);
		reinfo_listView.setOnItemLongClickListener(this);
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleLeftbtn.setOnClickListener(this);
		intent = getIntent();
		bean = (TestRecordBean) intent.getExtras().get("bean");
		titleTxt.setText(bean.getApplabel());
//		Log.i("debug", "包名："+bean.getPkgname());
//		list = DBHelper.getInstance(this).selectDataByPkgname(bean.getPkgname());
//		Log.i("debug", bean.toString());
//		Log.i("debug", "size: "+list.size());
//		adapter = new ResultInfoAdapter(this, list);
//		
//		if(list.size()>0){
//			reinfo_listView.setAdapter(adapter);
//		}
	}
	
    private void initTitleLayout(){
        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        titleTxt = (TextView) findViewById(R.id.title_txt);
        titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
        titleRightBtn = (Button) findViewById(R.id.title_right_btn);
    }
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ListView listView = (ListView) parent;
		bean = (TestRecordBean) parent.getItemAtPosition(position);
		
		intent = new Intent(this, DetailResultInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", (Serializable) bean);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		ListView listView = (ListView) parent;
		bean = (TestRecordBean) parent.getItemAtPosition(position);
		AlertDialog.Builder build = new Builder(this);
		
		build.setTitle(getString(R.string.delete_record));
		build.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ProgressDialog dialogsss = new ProgressDialog(ResultInfoActivity.this);
				dialogsss.setMessage(getString(R.string.deleting));
				dialogsss.setIndeterminate(true);
				dialogsss.setCancelable(false);
				dialogsss.show();
				
				if(DBHelper.getInstance(ResultInfoActivity.this).deleteDataById(bean.getTest_id())){
					list = DBHelper.getInstance(ResultInfoActivity.this).selectDataByPkgname(bean.getPkgname());
					Log.i("debug", "size: "+list.size());
					if(list.size()==0){
						dialogsss.dismiss();
						onBackPressed();
					}else {
						adapter = new ResultInfoAdapter(ResultInfoActivity.this, list);
						reinfo_listView.setAdapter(adapter);
						dialogsss.dismiss();
					}
//					notifDataReflesh();
				}
			}
		});
		build.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		build.show();
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.resinfo, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int item_id = item.getItemId(); 
		 switch (item_id) { 
		    case R.id.export: 
				if (Environment.getExternalStorageState().equals(  
		                Environment.MEDIA_MOUNTED)) {
					dialogsss_export = new ProgressDialog(ResultInfoActivity.this);
			    	dialogsss_export.setMessage(getString(R.string.exportting));
			    	dialogsss_export.setIndeterminate(true);
			    	dialogsss_export.setCancelable(false);
			    	dialogsss_export.show();
					Thread t = new Thread(export);
					t.start();
					} else {
						Toast.makeText(this, getString(R.string.sdcard_miss), 0).show();
					}
		        break; 
		    default: 
		        return false; 
		    } 
		    return true; 
	}
	
	final Handler handler  = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialogsss_export.dismiss();
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.export_success), 0).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
    //将要执行的操作写在线程对象的run方法当中
    Runnable export =  new Runnable() {
		@Override
		public void run() {
			saveToXLS();
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("debug", "onResume()");
		list = DBHelper.getInstance(this).selectDataByPkgname(bean.getPkgname());
		Log.i("debug", bean.toString());
		Log.i("debug", "size: "+list.size());
		adapter = new ResultInfoAdapter(this, list);
		if(list.size()>0){
			reinfo_listView.setAdapter(adapter);
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
			
			break;
		default:
			break;
		}
	}
	
	public void notifDataReflesh(){
		list = DBHelper.getInstance(this).selectDataByPkgname(bean.getPkgname());
		Log.i("debug", "size: "+list.size());
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 把当前页面记录导出为xls
	 */
	public void saveToXLS(){
		//如果name中包含冒号则会出现java.io.IOException: open failed: EINVAL (Invalid argument)。
		AndTimeUtils timeUtils = new AndTimeUtils();
		data = timeUtils.getCurrentTimeForFile();
		String appName = bean.getApplabel();
			FileUtil fileUtil = new FileUtil();
			fileUtil.exportXLS(list, path + "/"	+ appName + data, this);
	}

	
	
}
