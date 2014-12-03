package jp.bunkatusoft.explorersofsettlement.title;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.field.SettlementFieldActivity;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * Created by m_kagaya on 2014/11/25.
 */
public class TitleActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener,View.OnClickListener{

	Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO TITLE
		setContentView(R.layout.activity_title);
		mContext = this;

		Button newStartButton = (Button) findViewById(R.id.part_title_newStartButton);
		newStartButton.setOnClickListener(this);

		Button continueButton = (Button) findViewById(R.id.part_title_continueButton);
		continueButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.title_activity,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_title_achievements:
				showSystemDialog(SystemMenuEnum.ACHIEVEMENTS);
				break;
			case R.id.menu_title_settings:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_title_version_info:
				showSystemDialog(SystemMenuEnum.VERSION_INFO);
				break;
			case R.id.menu_title_quit:
				showSystemDialog(SystemMenuEnum.QUIT);
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
			case ACHIEVEMENTS:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			case SETTINGS:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_wip), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			case VERSION_INFO:
				systemDialog = SystemDialog.newInstance(null, String.format(getString(R.string.title_text_version_info), Util.getPackageVersion(mContext)), getString(R.string.back), null, menuEnum);
				ft.add(systemDialog,null);
				ft.commitAllowingStateLoss();
				break;
			case QUIT:
				systemDialog = SystemDialog.newInstance(null, getString(R.string.sys_msg_confirm_gameend), getString(R.string.yes), getString(R.string.no), menuEnum);
				ft.add(systemDialog,null);
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
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case QUIT:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {
		switch (menuEnum) {
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case QUIT:
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id){
			case R.id.part_title_newStartButton:
				startActivity(new Intent(TitleActivity.this, SettlementFieldActivity.class));
				finish();
				break;
			case R.id.part_title_continueButton:
				finish();
				break;
			default:
				break;
		}
	}
}