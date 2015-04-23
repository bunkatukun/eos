package jp.bunkatusoft.explorersofsettlement.system;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.field.world.TouchStatus;

public abstract class GameManager {

	protected List<Task> mTaskList = new ArrayList<Task>();

	public boolean onUpdate() {
		if(mTaskList.isEmpty()){
			return true;
		}

		for (Task task : mTaskList) {
			task.onUpdate();
		}
		return true;
	}

	public void onDraw(Canvas canvas) {
		for (Task task : mTaskList) {
			task.onDraw(canvas);
		}
	}

    public void onControl(Touch touch) {
		for (Task task : mTaskList) {
			task.onControl(touch);
		}
    }

	public void onControl(TouchStatus touchStatus){
		onControl(touchStatus.getmTouch());
	}
}
