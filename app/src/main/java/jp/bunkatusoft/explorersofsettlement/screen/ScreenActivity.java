package jp.bunkatusoft.explorersofsettlement.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

abstract public class ScreenActivity extends FragmentActivity {

	protected Context mContext;

	protected FrameLayout mRootLayout;
	protected ViewGroup mUILayout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);
	}

	abstract protected void initBackgroundLayout();
	abstract protected void initSurfaceViewLayout();
	abstract protected void initUILayout();
}
