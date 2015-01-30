package jp.bunkatusoft.explorersofsettlement.field.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jp.bunkatusoft.explorersofsettlement.field.map.tile.Tile;
import jp.bunkatusoft.explorersofsettlement.field.map.tile.TileUtil;

public abstract class MapBase extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private float mScale;
    private Thread mThread;

    private float mGameWidth;
    private float mGameHeight;

    private final int mTilePlacementX;
    private final int mTilePlacementY;

    private Bitmap mMap;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        float scaleX = getWidth() / mGameWidth;
        float scaleY = getHeight() / mGameHeight;
        mScale = scaleX > scaleY ? scaleY : scaleX;

        mThread = new Thread(this);
        mThread.start();
    }

    /**
     * @param gameWidth      ゲームサイズ 横
     * @param gameHeight     ゲームサイズ 縦
     * @param tilePlacementX タイルの配置数 横
     * @param tilePlacementY タイルの配置数 縦
     */
    public MapBase(Context context, float gameWidth, float gameHeight, int tilePlacementX, int tilePlacementY) {
        super(context);
		getHolder().addCallback(this);
        mGameWidth = gameWidth;
        mGameHeight = gameHeight;
        this.mTilePlacementX = tilePlacementX;
        this.mTilePlacementY = tilePlacementY;

        //FIXME 直接指定せず、配置セットを受け取るように
        mMap = TileUtil.findImage(context, Tile.TEST);
    }

    @Override
    public void run() {
        while (mThread != null) {
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

        //TODO 外部から設定できるように
        canvas.drawColor(Color.BLACK);
        createTile(canvas, new Paint());
        holder.unlockCanvasAndPost(canvas);
    }

    protected void createTile(Canvas canvas, Paint paint) {
        for (int i = 0; i < mTilePlacementX; i++) {
            for (int j = 0; j < mTilePlacementY; j++) {
                canvas.drawBitmap(mMap, MapConfig.IMAGE_WIDTH / 2 * i - MapConfig.IMAGE_WIDTH / 2 * j
                        , -MapConfig.IMAGE_HEIGHT * 5 + MapConfig.IMAGE_HEIGHT / 2 * i + MapConfig.IMAGE_HEIGHT / 2 * j, paint);
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread = null;
    }
}
