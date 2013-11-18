package cn.robotium;

import java.util.ArrayList;
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import cn.robotium.bean.TestType;
import cn.robotium.utils.Sleeper;

public class AppListInfo {
		String userNumber,sysNumber;
		Context context;
	
	public String getUserNumber() {
			return userNumber;
		}
	public void setUserNumber(String userNumber) {
			this.userNumber = userNumber;
		}

	public String getSysNumber() {
			return sysNumber;
		}
	public void setSysNumber(String sysNumber) {
			this.sysNumber = sysNumber;
		}
	public AppListInfo(Context context){
		this.context = context;
	}
	
	/**
	 * 通过pkgname，得到当前运行的程序的AppInfo实体
	 * 该方法在程序运行后使用
	 * @param pkgName 应用程序包名
	 * @return AppInfo
	 */
	public AppInfo getAppInfoBypkgName(String pkgName){
		AppInfo appInfo = new AppInfo();
		boolean isProcessStarted = false;
		long endTime = System.currentTimeMillis() +TestType.TIMEOUT ;
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
		while (System.currentTimeMillis() <= endTime) {
			List<RunningAppProcessInfo> runApps = am.getRunningAppProcesses();
			for (RunningAppProcessInfo runningAppInfo : runApps) {
				if ((pkgName != null) && (!pkgName.equals(TestType.PKGNAME)) && pkgName.equals(runningAppInfo.processName)) {
					appInfo.setPid(runningAppInfo.pid);
					appInfo.setUid(runningAppInfo.uid);
					appInfo.setPkgName(pkgName);
//					appInfo.setAppLabel(runningAppInfo.getClass().)
					if (appInfo.getPid() != 0) {
						isProcessStarted = true;
						break;
					}
				}
			}
			if (isProcessStarted)
				break;
		}
		return appInfo;
	}
	
	
	
	
	
	/**
	 * 得到当前所有用户应用
	 * @param appType
	 * @return
	 */
	public List<AppInfo> getUserApp() {
		PackageManager pm = context.getPackageManager();
		List<AppInfo> userAppList = new ArrayList<AppInfo>();
		for (ApplicationInfo appinfo : getPackagesInfo(context)) {
			AppInfo appinfos = new AppInfo();
				if ((appinfo.flags & appinfo.FLAG_SYSTEM) == 0 && !appinfo.packageName.equals(TestType.PKGNAME)) {
					appinfos.setPkgName(appinfo.processName);
					appinfos.setAppIcon(appinfo.loadIcon(pm));
					appinfos.setAppLabel((String) appinfo.loadLabel(pm));
					userAppList.add(appinfos);
				} 
		}
		return userAppList;
	}
	
	/**
	 * 得到当前所有系统应用
	 * @param appType 
	 * @return
	 */
	public List<AppInfo> getSysApp() {
		PackageManager pm = context.getPackageManager();
		List<AppInfo> sysAppList = new ArrayList<AppInfo>();
		for (ApplicationInfo appinfo : getPackagesInfo(context)) {
			AppInfo appinfos = new AppInfo();
				if ((appinfo.flags & appinfo.FLAG_SYSTEM) != 0) {
					appinfos.setPkgName(appinfo.processName);
					appinfos.setAppIcon(appinfo.loadIcon(pm));
					appinfos.setAppLabel((String) appinfo.loadLabel(pm));
					sysAppList.add(appinfos);
				} 
		}
		return sysAppList;
	}
	
	
	
	/**
	 * Return a List of all application packages that are installed on the device.
	 * @param context
	 * @return
	 */
	private List<ApplicationInfo> getPackagesInfo(Context context) {
		PackageManager pm = context.getApplicationContext().getPackageManager();
		List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		return appList;
	}
}
