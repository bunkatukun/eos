package jp.bunkatusoft.explorersofsettlement.field.map.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;

public class TestMapActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new TestMapSurfaceView(this));
	}

	@Override
	public void OnPositiveClickListener(SystemMenuEnum menuEnum) {
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {

	}
}
