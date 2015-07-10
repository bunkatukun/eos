package jp.bunkatusoft.explorersofsettlement.field.map.test;

import android.content.Context;

import jp.bunkatusoft.explorersofsettlement.system.GameManager;

public class TestMapGameManager extends GameManager {
	public TestMapGameManager(Context context) {
		mTaskList.add(new TestMapTask(context,
				TestMapConfig.TILE_PLACEMENT_X, TestMapConfig.TILE_PLACEMENT_Y));
	}
}
