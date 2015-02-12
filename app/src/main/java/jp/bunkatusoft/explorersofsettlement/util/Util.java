package jp.bunkatusoft.explorersofsettlement.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileSource.toString();
	}

	public static Bitmap loadResourceBitmapImage(Context context, int id) {
		Resources resources = context.getResources();
		return BitmapFactory.decodeResource(resources, id);
	}
}
