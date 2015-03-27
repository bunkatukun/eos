package jp.bunkatusoft.explorersofsettlement;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import jp.bunkatusoft.explorersofsettlement.util.Util;


public class ExploreOfSettlementApplication extends Application{

	Context mContext;
	//TODO MAPにするべきかな？
	private static Map<Integer,Bitmap> mItemIconMap;

	@Override
	public void onCreate() {
		super.onCreate();
		//TODO 各種画像等、プログラムの開始時に読み込む必要があるものはここで
		mContext = this;
		mItemIconMap = new HashMap<Integer, Bitmap>();

		loadItemIconBitmaps();
	}

	private void loadItemIconBitmaps(){
		Bitmap[] itemIconBitmaps = Util.loadSplitBitmapImage(mContext,R.drawable.iconset_test,16,16);

		if(itemIconBitmaps != null && itemIconBitmaps.length>0){
			int loopCount = 0;
			for(Bitmap itemIcon : itemIconBitmaps){
				mItemIconMap.put(loopCount,itemIcon);
				loopCount++;
			}
		}
	}

	public static Bitmap getItemIconBitmap(int key){
		return mItemIconMap.get(key);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		//TODO onCreateで読み込んだ画像等はここでリリースする

		releaseItemIconBitmaps();
	}

	private void releaseItemIconBitmaps(){
		if(mItemIconMap != null && mItemIconMap.size()>0){
			for(Map.Entry<Integer, Bitmap> bitmap : mItemIconMap.entrySet()){
				bitmap.getValue().recycle();
			}
		}
	}
}
