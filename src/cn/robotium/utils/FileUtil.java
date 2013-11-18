package cn.robotium.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import cn.robotium.R;
import cn.robotium.bean.TestRecordBean;

public class FileUtil {
	
	  
	/**
	 * 判断目录是否存在，不存在则创建
	 * @param path
	 */
	public void FileExist(){
		String SDCardRoot;
			SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
			File file = new File(SDCardRoot + "AndTools" + File.separator);
			if (!file.exists()) {
				file.mkdir();
			} else {
				return;
			}
	}	
	
	/**
	 * 生成xls
	 * @param list
	 * @param fileName
	 */
	public void exportXLS(List<TestRecordBean> list, String fileName, Context context) {
		FileExist();
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(new File(fileName + ".xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != wwb) {
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			String[] topic = { "id", "应用程序", "测试类型", "测试时长", 
					"刷新频率", "平均内存使用率", "最大内存使用率", 
					"平均CPU使用率", "最大CPU使用率", "流量使用","测试结论", "日期"};
			for (int i = 0; i < topic.length; i++) {
				Label labelC = new Label(i, 0, topic[i]);
				try {
					ws.addCell(labelC);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TestRecordBean bean;
			ArrayList<String> li;
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				li = new ArrayList<String>();
				li.add(bean.getTest_id() + "");
				li.add(bean.getApplabel());
				String testType = "";
				if (bean.getTest_type() == 1) {
					li.add(context.getString(R.string.cpu_test_type));
				} else  if (bean.getTest_type() == 2) {
					li.add(context.getString(R.string.memory_test_type));
				} else {
					li.add(context.getString(R.string.net_test_type));
				}
				li.add(bean.getTest_duration() + "毫秒");
				li.add(bean.getTest_refresh_rate() + "毫秒");
				li.add(bean.getMemory_avg_use() + "MB");
				li.add(bean.getMemory_max_use() + "MB");
				li.add(bean.getCpu_avg_use() + "%");
				li.add(bean.getCpu_max_use() + "%");
				li.add(bean.getNet_total_use() + "KB");
				li.add(bean.getTest_result());
				li.add(bean.getLast_test_time());
				int k = 0;
				for (String str : li) {
					Label labelC = new Label(k, i + 1, str);
					k++;
					try {
						ws.addCell(labelC);
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				li = null;
			}
		}
		try {
			Log.i("debug", wwb.toString());
			wwb.write();
			wwb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
