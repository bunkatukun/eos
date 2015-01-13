package jp.bunkatusoft.explorersofsettlement.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by m_kagaya on 2014/11/25.
 * 管理・設定の補助メソッド群<br>
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

	public static String getAssetsJSONText(Context context, String assetsFilePath) {
		StringBuffer fileSource = new StringBuffer();

		InputStream is = null;
		BufferedReader br = null;

		try {
			is = context.getAssets().open(assetsFilePath);
			br = new BufferedReader(new InputStreamReader(is));
			String buffer;
			while ((buffer = br.readLine()) != null) {
				fileSource.append(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileSource.toString();
	}
}
