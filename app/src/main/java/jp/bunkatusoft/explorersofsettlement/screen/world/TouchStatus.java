package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.view.MotionEvent;

import jp.bunkatusoft.explorersofsettlement.system.Touch;

/**
 * 画面タッチの状態
 */
public class TouchStatus {
	public boolean isScrolling;
	private int mScrollXBuff;
	private int mScrollYBuff;
	private Touch mTouch;

	public Touch getmTouch() {
		return mTouch;
	}

	public TouchStatus() {
		mTouch = new Touch();
	}

	public void actionDown(MotionEvent event) {
		if (isScrolling) {
			return;
		}

		startScroll();
		mTouch.isTouch = true;
		mTouch.touchX = (int) event.getX();
		mTouch.touchY = (int) event.getY();
		mScrollXBuff = mTouch.touchX;
		mScrollYBuff = mTouch.touchY;
	}

	public void actionMove(MotionEvent event) {
		if (!isScrolling) {
			return;
		}

		int nowScrollXBuff = (int) event.getX();
		int nowScrollYBuff = (int) event.getY();
		int scrollX = mScrollXBuff - nowScrollXBuff;
		int scrollY = mScrollYBuff - nowScrollYBuff;
		if (scrollX != 0 || scrollY != 0) {
			mTouch.scrollX = scrollX;
			mTouch.scrollY = scrollY;
			mScrollXBuff = nowScrollXBuff;
			mScrollYBuff = nowScrollYBuff;
		}
	}

	public void actionUp() {
		if (!isScrolling) {
			return;
		}

		stopScroll();
		mTouch.isTouch = false;
		mTouch.touchX = 0;
		mTouch.touchY = 0;
	}

	private void startScroll() {
		isScrolling = true;
	}

	private void stopScroll() {
		isScrolling = false;
	}
}
