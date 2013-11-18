package cn.robotium.ui;

import java.io.Serializable;
import java.util.List;

import cn.robotium.R;
import cn.robotium.adapter.HistoryAdapter;
import cn.robotium.adapter.ResultInfoAdapter;
import cn.robotium.bean.TestRecordBean;
import cn.robotium.database.DBHelper;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener, OnClickListener{
	private LinearLayout titleLayout;
	private TextView titleTxt;
	private Button titleLeftbtn;
	private Button titleRightBtn;
	private ListView historyListView;
	private TestRecordBean bean;
	private Intent intent;
	private List<TestRecordBean> list;
	private HistoryAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("debug", "onCreate()");
		setContentView(R.layout.history);
		initTitleLayout();
		titleTxt.setText("历史记录");
		titleLeftbtn.setVisibility(View.VISIBLE);
		titleLeftbtn.setOnClickListener(this);
//		titleRightBtn.setVisibility(View.VISIBLE);
		historyListView = (ListView) findViewById(R.id.history_list);
		historyListView.setOnItemClickListener(this);
		historyListView.setOnItemLongClickListener(this);
		
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
		bean = (TestRecordBean) parent.getItemAtPosition(position);
		intent = new Intent(this, ResultInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", (Serializable) bean);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
//		ListView listView = (ListView) parent;
		bean = (TestRecordBean) parent.getItemAtPosition(position);
		AlertDialog.Builder build = new Builder(this);
		build.setTitle(getString(R.string.delete_record));
		build.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				final ProgressDialog dialogsss = new ProgressDialog(HistoryActivity.this);
				dialogsss.setMessage(getString(R.string.deleting));
				dialogsss.setIndeterminate(true);
				dialogsss.setCancelable(false);
				dialogsss.show();
				if(DBHelper.getInstance(HistoryActivity.this).deleteDataByPkgname(bean.getPkgname())){
					list = DBHelper.getInstance(HistoryActivity.this).selectDataGroupByName();
					Log.i("debug", "size: "+list.size());
					if(list.size()==0){
						dialogsss.dismiss();
						historyListView.setAdapter(null);
//						onBackPressed();
					}else {
						adapter = new HistoryAdapter(HistoryActivity.this, list);
						historyListView.setAdapter(adapter);
						dialogsss.dismiss();
					}
//					notifDataReflesh();
				}
			}
		});
		build.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//
			}
		});
		build.show();
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("debug", "onResume()");
		list = DBHelper.getInstance(this).selectDataGroupByName();
//		List<TestRecordBean> list = DBHelper.getInstance(this).selectAllData();
		Log.i("debug", "list: "+list.size());
		adapter = new HistoryAdapter(this, list);
		if(list.size()==0){
			historyListView.setAdapter(null);
		}else {
			historyListView.setAdapter(adapter);
		}
		super.onResume();
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

}
