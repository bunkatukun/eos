package jp.bunkatusoft.explorersofsettlement.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 管理・設定の補助メソッド群
 */
public class Util {
	public static String getPackageVersion(Context context) {

		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return "";
		}
	}

	public static String getAssetsJSONText(Context context, String assetsFilePath) {
		StringBuilder fileSource = new StringBuilder();

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
			LogUtil.e(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LogUtil.e(e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LogUtil.e(e);
				}
			}
		}

		LogUtil.i("filefile:\n" + fileSource.toString());
		return fileSource.toString();
	}

	public static Bitmap loadResourceBitmapImage(Context context, int id) {
		Resources resources = context.getResources();
		return BitmapFactory.decodeResource(resources, id);
	}

	public static int getDensityPoint(Context context, int value) {
		return (int) (value * context.getResources().getDisplayMetrics().density);
	}

	public static int intCompare(int no1, int no2) {
		if (no1 > no2) {
			return 1;
		} else if (no1 == no2) {
			return 0;
		} else {
			return -1;
		}
	}
}
