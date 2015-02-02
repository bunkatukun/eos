package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import jp.bunkatusoft.explorersofsettlement.system.GameManager;

public class WorldMapGameManager extends GameManager {
	public WorldMapGameManager(Context context) {
		mTaskList.add(new WorldMapTask(context));
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		super.onDraw(canvas);
	}
}
