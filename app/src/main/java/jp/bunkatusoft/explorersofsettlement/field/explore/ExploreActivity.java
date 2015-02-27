package jp.bunkatusoft.explorersofsettlement.field.explore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;

public class ExploreActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.explore_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_explore_load:
				showSystemDialog(SystemMenuEnum.LOAD);
				break;
			case R.id.menu_explore_save:
				showSystemDialog(SystemMenuEnum.SAVE);
				break;
			case R.id.menu_explore_setting:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_explore_return_title:
				showSystemDialog(SystemMenuEnum.RETURN_TITLE);
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
			case LOAD:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case SAVE:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case SETTINGS:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			case RETURN_TITLE:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_confirm_back_title), getString(R.string.yes), getString(R.string.no), menuEnum);
				ft.add(systemDialog, null);
				ft.commitAllowingStateLoss();
				break;
			default:
				//未実装の定義は省略
				break;
		}
	}

	@Override
	public void OnPositiveClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case LOAD:
				break;
			case SAVE:
				break;
			case SETTINGS:
				break;
			case RETURN_TITLE:
				startActivity(new Intent(ExploreActivity.this, TitleActivity.class));
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case LOAD:
				break;
			case SAVE:
				break;
			case SETTINGS:
				break;
			case RETURN_TITLE:
				break;
			default:
				break;
		}
	}
}
