package jp.bunkatusoft.explorersofsettlement.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by m_kagaya on 2014/11/25.
 */
public class Util {
	public static String getPackageVersion(Context context) {

		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = null;
			info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return "";
		}
	}
}
