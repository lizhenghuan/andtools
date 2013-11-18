package cn.robotium.test;

import cn.robotium.bean.TestRecordBean;
import cn.robotium.bean.TestType;
import cn.robotium.database.DBHelper;
import cn.robotium.utils.AndTimeUtils;
import android.test.AndroidTestCase;
import android.util.Log;

public class AssistantJunitTest extends AndroidTestCase {
	public void testCreateDatabase(){
		DBHelper.getInstance(getContext());
	}

	public void testInsertData(){
		TestRecordBean bean1 = new TestRecordBean();
		bean1.setPkgname("com.sohu.inputmethod.sogou");
		bean1.setLast_test_time(String.valueOf(System.currentTimeMillis()));
		bean1.setApplabel("搜狗输入法");

		for(int j=0; j<=10; j++){
			for(int i=0; i<=2; i++){
//				DBHelper.getInstance(getContext()).insertData(new TestRecordBean(i, "com.sohu.inputmethod.sogou", "搜狗输入法", System.currentTimeMillis(), AndTimeUtils.getCurrentTimeToString(), i+1, "", "", "", 0));
//				DBHelper.getInstance(getContext()).insertData(new TestRecordBean(i, "com.sohu.inputmethod.sogou", "搜狗输入法", System.currentTimeMillis(), (long) 1.1, AndTimeUtils.getCurrentTimeToString(), i+1, "", "", "", "", "", "", 0,"","","",""));
				
			}
			
			for(int i=3; i<=5; i++){
//				DBHelper.getInstance(getContext()).insertData(new TestRecordBean(i, "com.megafon.ums", "UMS", System.currentTimeMillis(), AndTimeUtils.getCurrentTimeToString(), 6-i, "", "", "", 1));
//				DBHelper.getInstance(getContext()).insertData(new TestRecordBean(i, "com.megafon.ums", "UMS", System.currentTimeMillis(), (long) 1.1, AndTimeUtils.getCurrentTimeToString(), 6-i, "", "", "", "", "", "", 1,"","","",""));
			}
		}
	}

	public void testGetData(){
		Log.i("debug", ""+DBHelper.getInstance(getContext()).selectAllData().size());
		//		Log.i("debug", "Bean: "+DBHelper.getInstance(getContext()).selectDataById(1).toString());
	}

	public void testDeleteData(){
		DBHelper.getInstance(getContext()).deleteDataById(1);
	}

	public void testGetDataByPkgName(){
		//		Log.i("debug", "size: "+DBHelper.getInstance(getContext()).selectDataByPkgname("com.sohu.inputmethod.sogou").size());
		Log.i("debug", "size: "+DBHelper.getInstance(getContext()).selectDataGroupByName().size());
		for(TestRecordBean bean : DBHelper.getInstance(getContext()).selectDataGroupByName()){
			Log.i("debug", bean.toString());
		}
	}
	
	public void testGetTimeToString(){
		Log.i("debug", AndTimeUtils.getCurrentTimeToString());
	}

}
