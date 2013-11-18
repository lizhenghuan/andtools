package cn.robotium.utils;

import java.io.ByteArrayOutputStream;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class AndImageUtils {
	
	public static byte[] getAppIconByte(Context c, String packageName){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			BitmapDrawable drawable = (BitmapDrawable) c.getPackageManager().getApplicationIcon(packageName);
			drawable.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toByteArray();
	}
}
