package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.ExtendBitmap;
import jp.bunkatusoft.explorersofsettlement.system.Task;
import jp.bunkatusoft.explorersofsettlement.system.TaskPhaseEnum;
import jp.bunkatusoft.explorersofsettlement.system.Touch;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class WorldMapTask extends Task {

	private Context mContext;
	private Paint mPaint;

	/**
	 * 画像群
	 */
	protected ExtendBitmap mMapImage;

	public WorldMapTask(Context context) {
		mContext = context;
	}

	@Override
	public void onDraw(Canvas canvas) {
		TaskPhaseEnum phase = getTaskPhase();
		if (phase == TaskPhaseEnum.INITIALIZE || phase == TaskPhaseEnum.FINALIZE) {
			return;
		}
		canvas.drawBitmap(mMapImage.src, mMapImage.scrollX, mMapImage.scrollY, mPaint);
	}

	@Override
	public void onControl(Touch touch) {
		mMapImage.scrollX -= touch.scrollX;
		mMapImage.scrollY -= touch.scrollY;
	}

	@Override
	public void onInitialize() {
		mPaint = new Paint();
		mMapImage = new ExtendBitmap();
		mMapImage.src = Util.loadResourceBitmapImage(mContext, R.drawable.test_world_map);
	}

	@Override
	public void onRunning() {
		//ここに処理を追加してゆく
		//終了時はfinish()を呼び出し
	}

	@Override
	public void onFinalize() {
		mMapImage.recycle();
	}

	@Override
	public void onEnd() {
	}
}
