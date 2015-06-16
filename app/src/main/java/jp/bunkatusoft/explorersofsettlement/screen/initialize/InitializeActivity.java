package jp.bunkatusoft.explorersofsettlement.screen.initialize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.FrameLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.screen.logo.LogoActivity;

public class InitializeActivity extends FragmentActivity{
	Context mContext;
	FrameLayout mRootLayout;

	boolean isBlockTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);

		initBlockTouch();
		initScreenBackground();

		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 2000);
	}

	private void initScreenBackground() {
		mRootLayout.setBackgroundResource(R.drawable.background_init_test169);
	}

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	class splashHandler implements Runnable {
		@Override
		public void run() {
			Intent intent = new Intent(InitializeActivity.this,LogoActivity.class);
			startActivity(intent);
			InitializeActivity.this.finish();
		}
	}
}
