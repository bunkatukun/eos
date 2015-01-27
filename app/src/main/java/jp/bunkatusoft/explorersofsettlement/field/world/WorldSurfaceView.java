package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by m_kagaya on 2015/01/27.
 */
public class WorldSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

	private Thread mThread;

	public WorldSurfaceView(Context context) {
		super(context);
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
		while(mThread != null) {	//メインループ
			onDraws(getHolder());
		}
	}

	private void onDraws(SurfaceHolder holder){
		Canvas c = holder.lockCanvas();
		if(c == null){
			return;
		}
		//ここに描画処理を書く
		holder.unlockCanvasAndPost(c);
	}

	private void onUpdates(){
		//TODO "館"を参照
	}
}
