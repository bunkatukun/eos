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

	/**
	 * 画像セットを分割して読み込む
	 * @param context	コンテキスト
	 * @param resourceID	使用画像のリソースＩＤ
	 * @param xNum	横方向分割数
	 * @param yNum	縦方向分割数
	 * @return	ビットマップ配列<br>xNum * yNumの要素を持つ
	 */
	public static Bitmap[] loadSplitBitmapImage(Context context, int resourceID, int xNum, int yNum) {
		int rawWidth, rawHeight;
		int splitWidth, splitHeight;
		int cursorX = 0;
		int cursorY = 0;
		int allNum = xNum * yNum;
		Bitmap splitImage[] = new Bitmap[allNum];
		Bitmap rawImage = loadResourceBitmapImage(context, resourceID);

		rawWidth = rawImage.getWidth();
		rawHeight = rawImage.getHeight();
		splitWidth = rawWidth / xNum;
		splitHeight = rawHeight / yNum;

		for (int i = 0; i < allNum; i++) {
			if (i != 0 && i % xNum == 0) {
				cursorX = 0;
				cursorY += splitHeight;
			} else if (i != 0) {
				cursorX += splitWidth;
			}
			splitImage[i] = Bitmap.createBitmap(rawImage, cursorX, cursorY,
					splitWidth, splitHeight);
		}
		return splitImage;
	}
}
