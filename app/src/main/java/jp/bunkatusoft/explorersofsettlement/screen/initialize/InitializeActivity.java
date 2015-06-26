package jp.bunkatusoft.explorersofsettlement.screen.initialize;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.screen.ScreenActivity;
import jp.bunkatusoft.explorersofsettlement.screen.logo.LogoActivity;

public class InitializeActivity extends ScreenActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initBackgroundLayout();

		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 2000);
	}

	@Override
	protected void initBackgroundLayout() {
		mRootLayout.setBackgroundResource(R.drawable.background_init_test169);
	}

	@Override
	protected void initSurfaceViewLayout() {
	}

	@Override
	protected void initUILayout() {
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
