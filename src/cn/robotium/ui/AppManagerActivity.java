package cn.robotium.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


import cn.robotium.AppInfo;
import cn.robotium.AppListInfo;
import cn.robotium.R;
import cn.robotium.adapter.ExAdapter;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class AppManagerActivity extends BaseActivity implements OnChildClickListener, OnClickListener{

	private ExpandableListView applist = null;
	private String testType;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private ExAdapter adapter;
	private HashMap<Integer, List<AppInfo>> hh;
	private List<AppInfo> sysList, userList;
	private TestRecordBean bean;
	private String Tag = "AppManagerActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);
		initTitleLayout();
		titleTxt.setText(getString(R.string.app_manager));
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleLeftbtn.setOnClickListener(this);
		titleRightBtn.setVisibility(View.GONE);
		Log.i(Tag, "this is a testing");

	}

	private void initTitleLayout(){
		titleTxt = (TextView) findViewById(R.id.title_txt);
		titleLeftbtn = (Button) findViewById(R.id.title_left_btn);
		titleRightBtn = (Button) findViewById(R.id.title_right_btn);
	}

	public void loadView() {
		hh = new HashMap<Integer, List<AppInfo>>();
		AppListInfo appListInfo = new AppListInfo(getBaseContext());
		userList = appListInfo.getUserApp();
		sysList = appListInfo.getSysApp();
		String userNumber = "(" + userList.size() + ")";
		String sysNumber ="(" + sysList.size() + ")";
		hh.put(0, userList);
		hh.put(1, sysList);
		applist = (ExpandableListView) findViewById(R.id.appList);
		applist.setOnChildClickListener(this);
		applist.setGroupIndicator(null);
		adapter = new ExAdapter(getBaseContext(),userNumber,sysNumber,hh);
		applist.setAdapter(adapter);
		applist.expandGroup(0);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_left_btn:
			onBackPressed();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		String pkgname = hh.get(groupPosition).get(childPosition).getPkgName();
		String label =  hh.get(groupPosition).get(childPosition).getAppLabel();
		Drawable icon = hh.get(groupPosition).get(childPosition).getAppIcon();
		//返回上个activity，并传出pkgname
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bean = (TestRecordBean) intent.getSerializableExtra("bean");
		bean.setApplabel(label);
		bean.setPkgname(pkgname);
		bundle.putSerializable("bean", (Serializable) bean);
		intent.putExtras(bundle);
		setResult(TestType.CHOOSE_APP,intent);
		finish();
		return false;
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		loadView();
		super.onResume();
	}

	


}
