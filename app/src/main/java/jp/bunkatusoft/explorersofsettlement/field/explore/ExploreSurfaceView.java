package jp.bunkatusoft.explorersofsettlement.field.explore;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.field.world.TouchStatus;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

public class ExploreSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private Thread mThread;
	private ExploreGameManager mExploreGameManager;
	private TouchStatus mTouchStatus;

	public ExploreSurfaceView(Context context) {
		this(context, null);
	}

	public ExploreSurfaceView(Context context, AttributeSet attributeSet) {
		this(context, attributeSet, 0);
	}

	public ExploreSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mExploreGameManager = new ExploreGameManager(context);
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
		while (mThread != null){
			mExploreGameManager.onUpdate();
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder){
		Canvas c = holder.lockCanvas();
		if(c == null){
			return;
		}
		mExploreGameManager.onDraw(c);
		holder.unlockCanvasAndPost(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
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
		mExploreGameManager.onControl(mTouchStatus);

		return true;
	}
}
