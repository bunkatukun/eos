package jp.bunkatusoft.explorersofsettlement.field.map.tile;

import android.content.Context;
import android.graphics.Bitmap;

import jp.bunkatusoft.explorersofsettlement.util.Util;

public class TileUtil {
	/**
	 * マップのBitmapを取得
	 */
	public static Bitmap findImage(Context context, Tile tile) {
		return Util.loadResourceBitmapImage(context, tile.getResourceId());
	}
}
