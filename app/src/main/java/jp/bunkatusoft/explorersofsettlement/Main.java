package jp.bunkatusoft.explorersofsettlement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;


public class Main extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		startActivity(new Intent(this, TitleActivity.class));
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
