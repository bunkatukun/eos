package jp.bunkatusoft.explorersofsettlement.field.explore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.ExtendBitmap;
import jp.bunkatusoft.explorersofsettlement.system.Task;
import jp.bunkatusoft.explorersofsettlement.system.TaskPhaseEnum;
import jp.bunkatusoft.explorersofsettlement.system.Touch;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class ExploreTask extends Task {

	private Context mContext;
	private Paint mPaint;

	protected ExtendBitmap mBackgroundImage;

	public ExploreTask(Context context){
		mContext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		TaskPhaseEnum phase = getTaskPhase();
		if(phase == TaskPhaseEnum.INITIALIZE || phase == TaskPhaseEnum.FINALIZE){
			return;
		}
		canvas.drawBitmap(mBackgroundImage.src,mBackgroundImage.scrollX,mBackgroundImage.scrollY,mPaint);
	}

	@Override
	protected void onControl(Touch touch) {
		mBackgroundImage.scrollX -= touch.scrollX;
		mBackgroundImage.scrollY -= touch.scrollY;
	}

	@Override
	protected void onInitialize() {
		mPaint = new Paint();
		mBackgroundImage = new ExtendBitmap();
		mBackgroundImage.src = Util.loadResourceBitmapImage(mContext, R.drawable.explore_bg_test01);
	}

	@Override
	protected void onRunning() {

	}

	@Override
	protected void onFinalize() {
		mBackgroundImage.recycle();
	}

	@Override
	protected void onEnd() {

	}
}
