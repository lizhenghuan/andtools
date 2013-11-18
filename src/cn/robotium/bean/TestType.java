package cn.robotium.bean;

public class TestType {
	/**
	 * CPU测试
	 */
	public static final int CPUTEST = 1;
	
	/**
	 * 内存测试
	 */
	public static final int MEMORYTEST = 2;
	
	/**
	 * 流量测试
	 */
	public static final int NETTEST = 3;
	
	/**
	 * MonkeyTest   4为系统默认错误类型
	 */
	public static final int MONKEYTEST = 5;
	/**
	 * 默认超时时间
	 */
	public static final int TIMEOUT = 20000;
	
	/**
	 * 当前包名
	 */
	public static final String PKGNAME = "cn.robotium";
	
	/**
	 * 点击选择应用
	 */
	public static final int CHOOSE_APP = 1;
	/**
	 * 点击选择测试类型
	 */
	public static final int CHOOSE_TEST_TYPE =2;			
}
