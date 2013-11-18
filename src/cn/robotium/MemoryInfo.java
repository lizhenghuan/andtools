package cn.robotium;

import java.text.DecimalFormat;

import cn.robotium.bean.TestRecordBean;
import cn.robotium.utils.Sleeper;

import android.R.integer;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.SumPathEffect;
import android.os.Debug;
import android.os.Handler;
import android.os.SystemClock;

public class MemoryInfo {
	private Sleeper sleeper;

	public void calcMemory(int pid, long time, long interval, Context context, TestRecordBean bean) {
		DecimalFormat fomart = new DecimalFormat();
		fomart.setMaximumFractionDigits(2);
		fomart.setMinimumFractionDigits(2);
		this.sleeper = new Sleeper();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		final long endTime = SystemClock.uptimeMillis() + time;
		int[] myMempid = new int[] { pid };
		int i = 0, sumUss= 0, sumPss= 0,avgPss = 0,maxPss = 0;
		double avgUss = 0, maxUss = 0;
		while (SystemClock.uptimeMillis() <= endTime) {
			Debug.MemoryInfo[] memoryInfoArray = am.getProcessMemoryInfo(myMempid);
			Debug.MemoryInfo pidMemoryInfo = memoryInfoArray[0];
			i++;
//			long uss = pidMemoryInfo.getTotalSharedDirty();
			int pss = pidMemoryInfo.getTotalPss();
//			sumUss += uss;
			sumPss += pss;
			if (sumPss < pss) {
				sumPss = pss;
			}
			int sleep = (int) interval;
			sleeper.sleep(sleep);
		}
		avgPss = sumUss / i;
//		avgUss = sumUss / i;
		String avgMemory = fomart.format((avgPss / 1024));
		String maxMemory = fomart.format((sumPss / 1024));
		bean.setMemory_avg_use(avgMemory);
		bean.setMemory_max_use(maxMemory);
	}
	
//	public void totalMemory(Context context){
//		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//		final ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
//		am.getMemoryInfo(info);
//	}

	public void javaObjectHeapMeory(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int AppMaxMemory = am.getMemoryClass();
		System.out.println("==============" +AppMaxMemory);
	}

}
