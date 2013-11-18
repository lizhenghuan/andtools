package cn.robotium.bean;

import java.io.Serializable;
import java.util.Date;

import android.graphics.drawable.Drawable;

/**
 * 构造测试记录Bean    2013-07-29
 * @author Administrator
 *
 */
public class TestRecordBean implements Serializable{
	// (test_id primary key autoincrement, pkgname , applabel, test_duration, last_test_time, test_type, cpu_result, memory_result, net_result)
	
	/*test_id integer primary key autoincrement, pkgname , "
			+ "applabel, test_duration,test_refresh_rate, last_test_time, test_type, test_result, cpu_max_use, "
			+ "cpu_avg_use, memory_max_use, memory_avg_use, net_total_use, has_read)");   */
	
	private int test_id;
	private String pkgname;
	private String applabel;
	private long test_duration;
	private long test_refresh_rate;
	private String last_test_time;
	private int test_type;
	private String test_result;
	private String cpu_max_use;
	private String cpu_avg_use;
	private String memory_max_use;
	private String memory_avg_use;
	private String net_total_use;
	private String test_bouts;
	private String throttle;
	private String ignoreCrashes;
	private String ignoreTimouts;
	
	
	

	
	/**
	 * 表示是否已读，0表示未读，1表示已读
	 */
	private int has_read;
	
	public TestRecordBean(){
		
	}

	public TestRecordBean(int test_id, String pkgname, String applabel,
			long test_duration, long test_refresh_rate, String last_test_time,
			int test_type, String test_result, String cpu_max_use,
			String cpu_avg_use, String memory_max_use, String memory_avg_use,
			String net_total_use, String test_bouts, String throttle, String ignoreCrashes,
			String ignoreTimouts, int has_read) {
		this.test_id = test_id;
		this.pkgname = pkgname;
		this.applabel = applabel;
		this.test_duration = test_duration;
		this.test_refresh_rate = test_refresh_rate;
		this.last_test_time = last_test_time;
		this.test_type = test_type;
		this.test_result = test_result;
		this.cpu_max_use = cpu_max_use;
		this.cpu_avg_use = cpu_avg_use;
		this.memory_max_use = memory_max_use;
		this.memory_avg_use = memory_avg_use;
		this.net_total_use = net_total_use;
		this.has_read = has_read;
		this.test_bouts = test_bouts;
		this.throttle = throttle;
		this.ignoreCrashes = ignoreCrashes;
		this.ignoreTimouts = ignoreTimouts;
	}


	

	@Override
	public String toString() {
		return "TestRecordBean [test_id=" + test_id + ", pkgname=" + pkgname
				+ ", applabel=" + applabel + ", test_duration=" + test_duration
				+ ", test_refresh_rate=" + test_refresh_rate
				+ ", last_test_time=" + last_test_time + ", test_type="
				+ test_type + ", test_result=" + test_result + ", cpu_max_use="
				+ cpu_max_use + ", cpu_avg_use=" + cpu_avg_use
				+ ", memory_max_use=" + memory_max_use + ", memory_avg_use="
				+ memory_avg_use + ", net_total_use=" + net_total_use
				+ ", has_read=" + has_read + ", test_bouts=" + test_bouts
				+ ", throttle=" + throttle + ", ignoreCrashes=" + ignoreCrashes
				+ ",ignoreTimouts=" + ignoreTimouts + "]";
	}



	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	public String getApplabel() {
		return applabel;
	}

	public void setApplabel(String applabel) {
		this.applabel = applabel;
	}

	public long getTest_duration() {
		return test_duration;
	}

	public void setTest_duration(long test_duration) {
		this.test_duration = test_duration;
	}

	public long getTest_refresh_rate() {
		return test_refresh_rate;
	}

	public void setTest_refresh_rate(long test_refresh_rate) {
		this.test_refresh_rate = test_refresh_rate;
	}

	public String getLast_test_time() {
		return last_test_time;
	}

	public void setLast_test_time(String last_test_time) {
		this.last_test_time = last_test_time;
	}

	public int getTest_type() {
		return test_type;
	}

	public void setTest_type(int test_type) {
		this.test_type = test_type;
	}

	public String getTest_result() {
		return test_result;
	}

	public void setTest_result(String test_result) {
		this.test_result = test_result;
	}

	public String getCpu_max_use() {
		return cpu_max_use;
	}

	public void setCpu_max_use(String cpu_max_use) {
		this.cpu_max_use = cpu_max_use;
	}

	public String getCpu_avg_use() {
		return cpu_avg_use;
	}

	public void setCpu_avg_use(String cpu_avg_use) {
		this.cpu_avg_use = cpu_avg_use;
	}

	public String getMemory_max_use() {
		return memory_max_use;
	}

	public void setMemory_max_use(String memory_max_use) {
		this.memory_max_use = memory_max_use;
	}

	public String getMemory_avg_use() {
		return memory_avg_use;
	}

	public void setMemory_avg_use(String memory_avg_use) {
		this.memory_avg_use = memory_avg_use;
	}

	public String getNet_total_use() {
		return net_total_use;
	}

	public void setNet_total_use(String net_total_use) {
		this.net_total_use = net_total_use;
	}

	public int getHas_read() {
		return has_read;
	}

	public void setHas_read(int has_read) {
		this.has_read = has_read;
	}
	public String getTest_bouts() {
		return test_bouts;
	}
	public void setTest_bouts(String test_bouts) {
		this.test_bouts = test_bouts;
	}
	public String getThrottle() {
		return throttle;
	}
	public void setThrottle(String throttle) {
		this.throttle = throttle;
	}
	public String getIgnoreCrashes() {
		return ignoreCrashes;
	}
	public void setIgnoreCrashes(String ignoreCrashes) {
		this.ignoreCrashes = ignoreCrashes;
	}
	public String getIgnoreTimouts() {
		return ignoreTimouts;
	}
	public void setIgnoreTimouts(String ignoreTimouts) {
		this.ignoreTimouts = ignoreTimouts;
	}

	
	
}
