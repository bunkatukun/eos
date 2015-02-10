package jp.bunkatusoft.explorersofsettlement.field.map;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.system.GameManager;

public abstract class MapBase extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private float mScale;
	private Thread mThread;

	private float mGameWidth;
	private float mGameHeight;

	private GameManager mGameManager;

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		float scaleX = getWidth() / mGameWidth;
		float scaleY = getHeight() / mGameHeight;
		mScale = scaleX > scaleY ? scaleY : scaleX;

		mThread = new Thread(this);
		mThread.start();
	}

	public MapBase(Context context, float gameWidth, float gameHeight, GameManager gameManager) {
		super(context);
		getHolder().addCallback(this);
		mGameWidth = gameWidth;
		mGameHeight = gameHeight;
		this.mGameManager = gameManager;
	}

	@Override
	public void run() {
		while (mThread != null) {
			mGameManager.onUpdate();
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			return;
		}
		// サイズを調整
		canvas.translate((getWidth() - mGameWidth) / 2 * mScale,
				(getHeight() - mGameHeight) / 2 * mScale);

		//XXX ここ怪しい
		canvas.scale(mScale, mScale);

		mGameManager.onDraw(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread = null;
	}
}
