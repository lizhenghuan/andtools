package cn.robotium;

import android.graphics.drawable.Drawable;

public class AppInfo {
	 
		private String appLabel;    //应用标签、名字
		private Drawable appIcon ;  //应用图标
		private String pkgName ;    //包名
		private int ppid ;  //进程号
		private int uid;
		
		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public AppInfo(){
		}
		
		public String getAppLabel() {
			return appLabel;
		}
		public void setAppLabel(String appName) {
			this.appLabel = appName;
		}
		public Drawable getAppIcon() {
			return appIcon;
		}
		public void setAppIcon(Drawable appIcon) {
			this.appIcon = appIcon;
		}
		public String getPkgName(){
			return pkgName ;
		}
		public void setPkgName(String pkgName){
			this.pkgName=pkgName ;
		}

		public int getPid() {
			return ppid;
		}

		public void setPid(int pid) {
			this.ppid = pid;
		}

		
}
