package jp.bunkatusoft.explorersofsettlement;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * EoS起動中に動き続ける Application クラス
 */
public class ExploreOfSettlementApplication extends Application {

	private static final int ITEMICON_SPLIT_W = 24;
	private static final int ITEMICON_SPLIT_H = 24;

	/**
	 * コンストラクタ
	 */
	Context mContext;

	/**
	 * アイテムアイコン用画像セット
	 */
	private static Map<Integer, Bitmap> mItemIconMap;

	@Override
	public void onCreate() {
		super.onCreate();
		//TODO 各種画像等、プログラムの開始時に読み込む必要があるものはここで
		mContext = this;

		// 画像群・データ群の読み込み
		loadItemIconBitmaps();
	}

	/**
	 * アイテムアイコン画像群を読み込む
	 */
	private void loadItemIconBitmaps() {
		mItemIconMap = new HashMap<Integer, Bitmap>();
		//SPLITは 分 割 数 なので間違えないように
		Bitmap[] itemIconBitmaps = Util.loadSplitBitmapImage(mContext, R.drawable.iconset_test, ITEMICON_SPLIT_W, ITEMICON_SPLIT_H);

		if (itemIconBitmaps != null && itemIconBitmaps.length > 0) {
			int loopCount = 0;
			for (Bitmap itemIcon : itemIconBitmaps) {
				mItemIconMap.put(loopCount, itemIcon);
				loopCount++;
			}
		}
		//TODO 読み込みの成否で処理を行う
	}

	/**
	 * アイテムアイコン画像を取得する
	 *
	 * @param key 取得するアイコン画像のキー値
	 * @return アイコン画像
	 */
	public static Bitmap getItemIconBitmap(int key) {
		return mItemIconMap.get(key);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		//TODO onCreateで読み込んだ画像等はここでリリースする

		releaseItemIconBitmaps();
	}

	/**
	 * アイテムアイコン画像群を破棄する
	 */
	private void releaseItemIconBitmaps() {
		if (mItemIconMap != null && mItemIconMap.size() > 0) {
			for (Map.Entry<Integer, Bitmap> bitmap : mItemIconMap.entrySet()) {
				bitmap.getValue().recycle();
			}
		}
		//TODO 破棄の成否で処理を行う
	}
}
