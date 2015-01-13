package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by m_kagaya on 2014/12/22.
 */
public class WorldFieldActivity extends FragmentActivity {

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		setContentView(null);
	}
}
