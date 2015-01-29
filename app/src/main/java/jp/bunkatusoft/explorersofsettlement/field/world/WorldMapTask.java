package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.Task;
import jp.bunkatusoft.explorersofsettlement.system.TaskPhaseEnum;
import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * Created by m_kagaya on 2015/01/28.
 */
public class WorldMapTask extends Task {

	private Context mContext;
	private Paint mPaint;

	/**
	 * 画像群
	 */
	private Bitmap mMapImage;

	public WorldMapTask(Context context) {
		mContext = context;
	}

	@Override
	public boolean onUpdate() {
		return super.onUpdate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		TaskPhaseEnum phase = getTaskPhase();
		if (phase == TaskPhaseEnum.INITIALIZE || phase == TaskPhaseEnum.FINALIZE) {
			return;
		}
		canvas.drawBitmap(mMapImage, 0, 0, mPaint);
	}

	@Override
	public void onInitialize() {
		mPaint = new Paint();
		mMapImage = Util.loadResourceBitmapImage(mContext, R.drawable.test_world_map);
	}

	@Override
	public boolean onStarting() {
		return super.onStarting();
	}

	@Override
	public void onRunning() {
		//ここに処理を追加してゆく
		//終了時はfinish()を呼び出し
	}

	@Override
	public boolean onStopping() {
		return super.onStopping();
	}

	@Override
	public void onFinalize() {
		mMapImage.recycle();
		mMapImage = null;
	}

	@Override
	public void onEnd() {
	}
}
