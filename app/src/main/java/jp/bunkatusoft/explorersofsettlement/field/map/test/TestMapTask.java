package jp.bunkatusoft.explorersofsettlement.field.map.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import jp.bunkatusoft.explorersofsettlement.field.map.MapConfig;
import jp.bunkatusoft.explorersofsettlement.field.map.tile.Tile;
import jp.bunkatusoft.explorersofsettlement.system.ExtendBitmap;
import jp.bunkatusoft.explorersofsettlement.system.Task;
import jp.bunkatusoft.explorersofsettlement.system.Touch;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class TestMapTask extends Task {
	private Context mContext;
	private Paint mPaint;
	protected ExtendBitmap mTileImage;

	private final int mTilePlacementX;
	private final int mTilePlacementY;

	public TestMapTask(Context mContext, int tilePlacementX, int tilePlacementY) {
		this.mContext = mContext;
		this.mTilePlacementX = tilePlacementX;
		this.mTilePlacementY = tilePlacementY;
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		createTile(canvas);
	}

	@Override
	protected void onControl(Touch touch) {
	}

	protected void createTile(Canvas canvas) {
		for (int i = 0; i < mTilePlacementX; i++) {
			for (int j = 0; j < mTilePlacementY; j++) {
				canvas.drawBitmap(mTileImage.src,
						MapConfig.IMAGE_WIDTH / 2 * i - MapConfig.IMAGE_WIDTH / 2 * j
						, -MapConfig.IMAGE_HEIGHT * 5 + MapConfig.IMAGE_HEIGHT / 2 * i
								+ MapConfig.IMAGE_HEIGHT / 2 * j, mPaint);
			}
		}
	}

	@Override
	public void onInitialize() {
		mPaint = new Paint();
		mTileImage = new ExtendBitmap();
		mTileImage.src = Util.loadResourceBitmapImage(mContext, Tile.TEST.getResourceId());
	}

	@Override
	protected void onRunning() {
	}

	@Override
	public void onFinalize() {
		mTileImage.recycle();
	}

	@Override
	protected void onEnd() {
	}
}
