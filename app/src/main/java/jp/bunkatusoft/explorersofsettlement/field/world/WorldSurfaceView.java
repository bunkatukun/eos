package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

/**
 * Created by m_kagaya on 2015/01/27.
 */
public class WorldSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private Thread mThread;
	private WorldMapGameManager mWorldMapGameManager;
	private TouchStatus mTouchStatus;

	public WorldSurfaceView(Context context) {
		this(context, null);
	}

	public WorldSurfaceView(Context context, AttributeSet attributeSet) {
		//TODO スタイル値が０でいいのか？
		this(context, attributeSet, 0);
	}

	public WorldSurfaceView(Context context, AttributeSet attributeSet, int style) {
		super(context, attributeSet, style);
		mWorldMapGameManager = new WorldMapGameManager(context);
		mTouchStatus = new TouchStatus();
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		mThread = new Thread(this);
		mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		mThread = null;
	}

	@Override
	public void run() {
		while (mThread != null) {
			mWorldMapGameManager.onUpdate();
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder) {
		Canvas c = holder.lockCanvas();
		if (c == null) {
			return;
		}
		mWorldMapGameManager.onDraw(c);
		holder.unlockCanvasAndPost(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mTouchStatus.actionDown(event);
				break;
			case MotionEvent.ACTION_MOVE:
				mTouchStatus.actionMove(event);
				break;
			case MotionEvent.ACTION_UP:
				mTouchStatus.actionUp();
				break;
			default:
				LogUtil.i("その他のイベント : " + event.getAction());
				break;
		}
		mWorldMapGameManager.onControl(mTouchStatus);

		return true;
	}
}
