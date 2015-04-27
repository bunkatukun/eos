package jp.bunkatusoft.explorersofsettlement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	Thread mThread;

	boolean flag;

	public BaseSurfaceView(Context context) {
		super(context);
		getHolder().addCallback(this);
		flag = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mThread = new Thread(this);             //別スレッドでメインループを作る
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread = null;
	}

	@Override
	public void run() {
		while (mThread!=null) { //メインループ
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder) {
		Canvas c = holder.lockCanvas();
		if(c == null){
			return;
		}
		LogUtil.i("NowFlag : " + flag);
		if(flag){
			c.drawColor(Color.WHITE);
		} else {
			c.drawColor(Color.GRAY);
		}
		//ここに描画処理を書く
		holder.unlockCanvasAndPost(c);
	}

	public void setFlag(boolean flag){
		this.flag = flag;
	}

	public boolean getFlag(){
		return flag;
	}
}
