package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.system.GameManager;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

/**
 * Created by m_kagaya on 2015/01/27.
 */
public class WorldSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private Thread mThread;
	private GameManager mGameManager;

	/**
	 * スクロール試験用
	 */
	private boolean isCanScroll = false;
	private int mScrollXBuff = 0;
	private int mScrollYBuff = 0;


	public WorldSurfaceView(Context context) {
		this(context, null);
	}

	public WorldSurfaceView(Context context, AttributeSet attributeSet) {
		//TODO スタイル値が０でいいのか？
		this(context, attributeSet, 0);
	}

	public WorldSurfaceView(Context context, AttributeSet attributeSet, int style) {
		super(context, attributeSet, style);
		mGameManager = new GameManager(context);
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

	}

	@Override
	public void run() {
		while (mThread != null) {    //メインループ
			mGameManager.onUpdate();
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder) {
		Canvas c = holder.lockCanvas();
		if (c == null) {
			return;
		}
		//ここに描画処理を書く
		mGameManager.onDraw(c);
		holder.unlockCanvasAndPost(c);
	}

	private void onUpdates() {
		//TODO "館"を参照
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//TODO タッチイベントをキャッチするゾ
		//LogUtil.i("WorldSurfaceView-TouchEvent\nX : " + event.getX() + "\nY : " + event.getY());

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//単純なタップならここだけ見れば良さそう
				//OnClickListenerなんていらなかった
				if (!isCanScroll) {
					LogUtil.i("スクロール開始");
					isCanScroll = true;
					mScrollXBuff = (int) event.getX();
					mScrollYBuff = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				//TODO 動かす間だけかな～とか思ってましたが、押している間はずっと呼ばれるようです
				if (isCanScroll) {
					int nowScrollXBuff = (int) event.getX();
					int nowScrollYBuff = (int) event.getY();
					int scrollX = mScrollXBuff - nowScrollXBuff;
					int scrollY = mScrollYBuff - nowScrollYBuff;

					if (scrollX != 0 && scrollY != 0) {
						LogUtil.i("スクロール検知 - X : " + scrollX + " Y : " + scrollY);
						mScrollXBuff = nowScrollXBuff;
						mScrollYBuff = nowScrollYBuff;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (isCanScroll) {
					LogUtil.i("スクロール終了");
					isCanScroll = false;
				}
				break;
			default:
				LogUtil.i("その他のイベント : " + event.getAction());
		}

		return true;
	}
}
