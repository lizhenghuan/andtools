package cn.robotium.database;

import java.util.ArrayList;
import java.util.List;

import cn.robotium.bean.TestRecordBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static DBHelper dbhelper;
	private SQLiteDatabase database;
	private TestRecordBean bean;
	public DBHelper(Context context) {
		super(context, "andtools.db", null, 1);
		// TODO Auto-generated constructor stub
		database = getWritableDatabase();
	}

	/**
	 * 单例模式，取得DBHelper对象
	 */
	public static DBHelper getInstance(Context context){
		if(dbhelper==null){
			dbhelper = new DBHelper(context);
		}
		return dbhelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// CREATE TABLE history(test_id primary key autoincrement, pkgname , applabel, test_duration, last_test_time, test_type, cpu_result, memory_result, net_result)
		Log.i("debug", "onCreate");
//		db.execSQL("CREATE TABLE history(test_id integer primary key autoincrement, pkgname , applabel, test_duration, last_test_time, test_type, cpu_result, memory_result, net_result, has_read)");
		db.execSQL("CREATE TABLE history(test_id integer primary key autoincrement, pkgname , "
				+ "applabel, test_duration,test_refresh_rate, last_test_time, test_type, test_result, cpu_max_use, "
				+ "cpu_avg_use, memory_max_use, memory_avg_use, net_total_use, has_read, test_bouts, throttle, ignoreCrashes, ignoreTimouts)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	/**
	 * 插入记录
	 * @param bean
	 */
	public void insertData(TestRecordBean bean){
		ContentValues values = new ContentValues();
		values.put("pkgname", bean.getPkgname());
		values.put("applabel", bean.getApplabel());
		values.put("test_duration", bean.getTest_duration());
		values.put("test_refresh_rate", bean.getTest_refresh_rate());
		values.put("last_test_time", bean.getLast_test_time());
		values.put("test_type", bean.getTest_type());
		values.put("test_result", bean.getTest_result());
		values.put("cpu_max_use", bean.getCpu_max_use());
		values.put("cpu_avg_use", bean.getCpu_avg_use());
		values.put("memory_max_use", bean.getMemory_max_use());
		values.put("memory_avg_use", bean.getMemory_avg_use());
		values.put("net_total_use", bean.getNet_total_use());
		values.put("has_read", bean.getHas_read());
		values.put("test_bouts", bean.getTest_bouts());
		values.put("throttle", bean.getThrottle());
		values.put("ignoreCrashes", bean.getIgnoreCrashes());
		values.put("ignoreTimouts", bean.getIgnoreTimouts());
		database.insert("history", null, values);
	}


	/**
	 * 返回所有的测试记录
	 * @return
	 */
	public List<TestRecordBean> selectAllData(){
		List<TestRecordBean> testRecordList = new ArrayList<TestRecordBean>();
		Cursor cursor = database.rawQuery("select * from history", null);
		TestRecordBean tBean = null;
		if(cursor.moveToFirst()){
			do {
				tBean = new TestRecordBean();
				setBean(tBean, cursor);
				testRecordList.add(tBean);
				tBean = null;
			} while (cursor.moveToNext());
		}
		return testRecordList;
	}


	/**
	 * 根据test_id返回测试记录
	 * @param test_id
	 * @return
	 */
	public TestRecordBean selectDataById(int test_id){
		Cursor cursor = database.rawQuery("select * from history where test_id=?", new String[]{String.valueOf(test_id)});
		if(cursor.moveToFirst()){
			bean = new TestRecordBean();
			setBean(bean, cursor);
		}
		return bean;
	}

	/**
	 * 根据包名返回记录
	 * @param pkgname
	 * @return
	 */
	public  List<TestRecordBean> selectDataByPkgname(String pkgname){
		List<TestRecordBean> testRecordList = new ArrayList<TestRecordBean>();
		Cursor cursor = database.rawQuery("select * from history where pkgname=? order by last_test_time DESC", new String[]{pkgname});
		TestRecordBean tBean = null;
		if(cursor.moveToFirst()){
			do {
				tBean = new TestRecordBean();
				setBean(tBean, cursor);
				testRecordList.add(tBean);
				tBean = null;
			} while (cursor.moveToNext());
		}
		return testRecordList;
	}


	/**
	 * 去掉相同包名的行，根据取最新的记录
	 * @return
	 */
	public  List<TestRecordBean> selectDataGroupByName(){
		List<TestRecordBean> testRecordList = new ArrayList<TestRecordBean>();
//		Cursor cursor = database.rawQuery("select * from history as pkgname where exists(select 1 from table where pkgname=history.pkgname group by pkgname having max(test_id)=history.test_id)", null);
		Cursor cursor = database.rawQuery("select * from (select * from history where test_id in (select max(test_id) from history group by pkgname ) order by last_test_time DESC)", null);
				TestRecordBean tBean = null;
				if(cursor.moveToFirst()){
					do {
						tBean = new TestRecordBean();
						setBean(tBean, cursor);
						testRecordList.add(tBean);
						tBean = null;
					} while (cursor.moveToNext());
				}
				return testRecordList;
	}
	
	
	public int getNotReadRecord(String pkgname){
//		Cursor cursor = database.rawQuery("select * from history where test_id in (select max(test_id) from history group by pkgname)", null);
		//select count(id) from history where pkgname=? and has_read=0 
		Cursor cursor = database.rawQuery("select * from history where pkgname=? and has_read=0", new String[]{pkgname});
		return cursor.getCount();
	}

	/**
	 * 根据id删除记录
	 * @param test_id
	 */
	public boolean deleteDataById(int test_id){
		database.delete("history", "test_id=?", new String[]{String.valueOf(test_id)});
		return true;
	}

	/**
	 * 根据包名删除记录
	 * @param pkgname
	 */
	public boolean deleteDataByPkgname(String pkgname){
		database.delete("history", "pkgname=?", new String[]{pkgname});
		return true;
	}

	
	/**
	 * 设置是否已读
	 * @param test_id
	 */
	public void setHasRead(int test_id) {
		// TODO Auto-generated method stub
		database.execSQL("update history set has_read=1 where test_id=?", new String[]{String.valueOf(test_id)});
	}
	
	public void updateTestResult(int test_id, String testResult){
		database.execSQL("update history set test_result=? where test_id=?", new String[]{testResult, String.valueOf(test_id)});
	}

	/**
	 * 初始化bean
	 * @param tBean
	 * @param cursor
	 */
	public void setBean(TestRecordBean tBean, Cursor cursor){
		tBean.setTest_id(cursor.getInt(cursor.getColumnIndex("test_id")));
		tBean.setPkgname(cursor.getString(cursor.getColumnIndex("pkgname")));
		tBean.setApplabel(cursor.getString(cursor.getColumnIndex("applabel")));
		tBean.setTest_duration(cursor.getLong(cursor.getColumnIndex("test_duration")));
		tBean.setTest_refresh_rate(cursor.getLong(cursor.getColumnIndex("test_refresh_rate")));
		tBean.setLast_test_time(cursor.getString(cursor.getColumnIndex("last_test_time")));
		tBean.setTest_type(cursor.getInt(cursor.getColumnIndex("test_type")));
		tBean.setTest_result(cursor.getString(cursor.getColumnIndex("test_result")));
		tBean.setCpu_max_use(cursor.getString(cursor.getColumnIndex("cpu_max_use")));
		tBean.setCpu_avg_use(cursor.getString(cursor.getColumnIndex("cpu_avg_use")));
		tBean.setMemory_max_use(cursor.getString(cursor.getColumnIndex("memory_max_use")));
		tBean.setMemory_avg_use(cursor.getString(cursor.getColumnIndex("memory_avg_use")));
		tBean.setNet_total_use(cursor.getString(cursor.getColumnIndex("net_total_use")));
		tBean.setHas_read(cursor.getInt(cursor.getColumnIndex("has_read")));
		tBean.setTest_bouts(cursor.getString(cursor.getColumnIndex("test_bouts")));
		tBean.setThrottle(cursor.getString(cursor.getColumnIndex("throttle")));
		tBean.setIgnoreCrashes(cursor.getString(cursor.getColumnIndex("ignoreCrashes")));
		tBean.setIgnoreTimouts(cursor.getString(cursor.getColumnIndex("ignoreTimouts")));
	}
}
