package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.field.world.TouchStatus;
import jp.bunkatusoft.explorersofsettlement.field.world.WorldMapTask;

/**
 * Created by m_kagaya on 2015/01/28.
 */
public class GameManager {

	private Context mContext;
	private List<Task> mTaskList = new ArrayList<Task>();

	public GameManager(Context context) {
		mContext = context;
		//TODO ここに実行するタスクを追加
		mTaskList.add(new WorldMapTask(context));
	}

	public boolean onUpdate() {
		if(mTaskList.isEmpty()){
			return true;
		}

		for (int i = 0; i < mTaskList.size(); i++) {
			mTaskList.get(i).onUpdate();
		}
		return true;
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for (int i = 0; i < mTaskList.size(); i++) {
			mTaskList.get(i).onDraw(canvas);
		}
	}

    public void onControl(Touch touch) {
        for (int i = 0; i < mTaskList.size(); i++) {
            mTaskList.get(i).onControl(touch);
        }
    }

	public void onControl(TouchStatus touchStatus){
		onControl(touchStatus.getmTouch());
	}
}
