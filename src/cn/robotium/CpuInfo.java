package cn.robotium;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.robotium.bean.TestRecordBean;
import cn.robotium.utils.Sleeper;

import android.os.SystemClock;

public class CpuInfo {
	private long idleCPU1, idleCPU2;
	private long totalCPU1, totalCPU2;
	private long idleCPU;
	private long totalCPU;
	private long processCPU;
	private long processCPU1, processCPU2;
	private double resProcessCPU;
	private double resTotalCPU;
	private int pid;
	private long time, interval;
	private Sleeper sleeper;
	
	public CpuInfo(int pid, long time, long interval){
		this.pid =  pid;
		this.time = time;
		this.interval = interval;
		this.sleeper = new Sleeper();
	}
	
	
	public void getCpuUsage(TestRecordBean bean){
		String avgCpu,maxCpu;
		double avgProcessCpu = 0, maxProcessCpu = 0, sumProcessCpu = 0;
		int i = 0;
		final long endTime = SystemClock.uptimeMillis() + time;
		DecimalFormat fomart = new DecimalFormat();
		fomart.setMaximumFractionDigits(2);
		fomart.setMinimumFractionDigits(2);
		while (SystemClock.uptimeMillis() <= endTime) {
			i++;
			calcPorcessTotalCpuUsage();
			if (maxProcessCpu < resProcessCPU) {
				maxProcessCpu = resProcessCPU;
			}
			sumProcessCpu += resProcessCPU;
		}
		avgProcessCpu = sumProcessCpu / i;
//		System.out.println("===平均Cpu===" + avgProcessCpu);
		avgCpu = fomart.format(avgProcessCpu);
		maxCpu = fomart.format (maxProcessCpu);
		bean.setCpu_avg_use(avgCpu);
		bean.setCpu_max_use(maxCpu);
	}
	
	/**
	 * 某进程的CPU使用率= 100*(processCPU2-processCPU1)/(totalCPU2-totalCPU1) 
	 * CPU总使用率= 100*((totalCPU2-totalCPU1)-(idleCPU2-idleCPU1))/(totalCPU2-totalCPU1)
	 */
	public void calcPorcessTotalCpuUsage(){
		int sleep = (int) interval;
		getProcessCpuTime();
		processCPU1 = processCPU;
		getTotalCpuTime();
		totalCPU1 = totalCPU;
		idleCPU1 = idleCPU;
		sleeper.sleep(sleep);
		getProcessCpuTime();
		processCPU2 = processCPU;
		getTotalCpuTime();
		totalCPU2 = totalCPU;
		idleCPU2 = idleCPU;
//		System.out.println("===processCPU1===" + processCPU1 + "===processCPU2===" + processCPU2);
		resProcessCPU = 100 * (double)(processCPU2 - processCPU1) /(totalCPU2 - totalCPU1);
//		System.out.println("===resProcessCPU===" + resProcessCPU);
//		resTotalCPU = 100 * (double)((totalCPU2 - idleCPU2) - (totalCPU1 - idleCPU1)) / (totalCPU2 - totalCPU1);
	}
	

	public void getTotalCpuTime(){
		String totalCpuPath = "/proc/stat";
		try {
			RandomAccessFile file = new RandomAccessFile(totalCpuPath, "r");
			String[] toks = file.readLine().toString().split(" "); 
			idleCPU = Long.parseLong(toks[5]);
			totalCPU = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[5]) + Long.parseLong(toks[7])
					+ Long.parseLong(toks[8]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getProcessCpuTime(){
		String processCpuPath = "/proc/" + pid + "/stat";
		try {
			RandomAccessFile file = new RandomAccessFile(processCpuPath, "r");
			String line = "";
			StringBuffer sb = new StringBuffer();
			sb.setLength(0);
			while ((line = file.readLine()) != null) {
				sb.append(line + "\n");
			}
			String[] toks = sb.toString().split(" "); 
			processCPU = Long.parseLong(toks[13])+Long.parseLong(toks[14])+ Long.parseLong(toks[15]) + Long.parseLong(toks[16]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
