package jp.bunkatusoft.explorersofsettlement.field.explore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import jp.bunkatusoft.explorersofsettlement.system.GameManager;

public class ExploreGameManager extends GameManager {
	public ExploreGameManager(Context context){
		mTaskList.add(new ExploreTask(context));
	}

	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		super.onDraw(canvas);
	}
}
