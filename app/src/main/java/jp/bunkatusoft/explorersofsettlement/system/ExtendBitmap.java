package jp.bunkatusoft.explorersofsettlement.system;

import android.graphics.Bitmap;

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
