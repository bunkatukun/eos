package jp.bunkatusoft.explorersofsettlement.screen.logo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.screen.ScreenActivity;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;

public class LogoActivity extends ScreenActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initBackgroundLayout();

		Handler handler = new Handler();
		handler.postDelayed(new splashHandler(), 2000);
	}

	@Override
	protected void initBackgroundLayout() {
		mRootLayout.setBackgroundResource(R.drawable.background_logo_test169);
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
			Intent intent = new Intent(LogoActivity.this, TitleActivity.class);
			startActivity(intent);
			LogoActivity.this.finish();
		}
	}
}
