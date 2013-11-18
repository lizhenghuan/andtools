package cn.robotium.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;
import cn.robotium.R;

public class VerifyUtil {
	
	public static boolean isNullEditText(EditText editText1, EditText editText2,Context context, String str) {
    	if (editText1.getText().toString().equals("") ||
    			editText2.getText().toString().equals("")) {
    		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    		return true;
		}
		return false;
    	
    }

}
