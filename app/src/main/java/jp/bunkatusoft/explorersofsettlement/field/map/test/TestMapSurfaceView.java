package jp.bunkatusoft.explorersofsettlement.field.map.test;

import android.content.Context;

import jp.bunkatusoft.explorersofsettlement.field.map.MapBase;

public class TestMapSurfaceView extends MapBase {

	public TestMapSurfaceView(Context context) {
		super(context,
				TestMapConfig.GAME_WIDTH, TestMapConfig.GAME_HEIGHT,
				TestMapConfig.TILE_PLACEMENT_X, TestMapConfig.TILE_PLACEMENT_Y);
	}
}
