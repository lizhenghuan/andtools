package cn.robotium;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import cn.robotium.bean.TestRecordBean;
import cn.robotium.utils.Sleeper;

import android.os.SystemClock;

public class NetInfo {
	private int uid;
	private long time;
	private String systemNetPath= "/proc/net/dev";
	private long tcp_rcv,tcp_snd,trafficBegin,trafficEnd,tracffic;
	private double trafficResult;
	private Sleeper sleeper;

		
	public NetInfo(int uid, long time){
		this.uid = uid;
		this.time = time;
		this.sleeper = new Sleeper();
	}
	
	public void calcAppNetInfo(TestRecordBean bean){
		DecimalFormat fomart = new DecimalFormat();
		fomart.setMaximumFractionDigits(2);
		fomart.setMinimumFractionDigits(2);
		getNetInfo();
		trafficBegin = tracffic;
		int wait = (int) time;
		sleeper.sleep(wait);
		getNetInfo();
		trafficEnd = tracffic;
		trafficResult = trafficEnd - trafficBegin;
		String sumTraffic = fomart.format((trafficResult / 1024)); 
		bean.setNet_total_use(sumTraffic);
	}
	
	public void getNetInfo(){
		String appNetRcvPath = "/proc/uid_stat/" + uid +"/tcp_rcv";
		String appNetSndPath = "/proc/uid_stat/" + uid +"/tcp_snd";
		try {
			RandomAccessFile fileRcv = new RandomAccessFile(appNetRcvPath, "r");
			RandomAccessFile fileSnd = new RandomAccessFile(appNetSndPath, "r");
			tcp_rcv = Long.parseLong(fileRcv.readLine().toString());
			tcp_snd = Long.parseLong(fileSnd.readLine().toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tracffic = tcp_rcv + tcp_snd;
	}
	
	
}
