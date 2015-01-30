package jp.bunkatusoft.explorersofsettlement.system;

import android.graphics.Bitmap;

/**
 * Created by m_kagaya on 2015/01/29.
 */
public class ExtendBitmap {
	public Bitmap src;
	public int scrollX;
	public int scrollY;

	public void recycle(){
		if(src != null) {
			src.recycle();
			src = null;
		}
	}
}
