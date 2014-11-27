package jp.bunkatusoft.explorersofsettlement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;


public class Main extends FragmentActivity implements SystemDialog.OnSystemDialogListener {

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_saveandload:
				showSystemDialog(SystemMenuEnum.save_and_load);
				break;
			case R.id.menu_setting:
				showSystemDialog(SystemMenuEnum.settings);
				break;
			case R.id.menu_finish:
				showSystemDialog(SystemMenuEnum.quit);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSystemDialog(SystemMenuEnum menuEnum) {
		SystemDialog systemDialog;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (menuEnum) {
			case save_and_load:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			case settings:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			case quit:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_confirm_gameend), getString(R.string.yes), getString(R.string.no), menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			default:
				//未使用の項目は割愛
				break;
		}
	}

	@Override
	public void OnPositiveClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case save_and_load:
				break;
			case settings:
				break;
			case quit:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case save_and_load:
				break;
			case settings:
				break;
			case quit:
				break;
			default:
				break;
		}
	}
}
