package jp.bunkatusoft.explorersofsettlement.field;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import jp.bunkatusoft.explorersofsettlement.BaseSurfaceView;
import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;

/**
 * Created by m_kagaya on 2014/12/02.
 */
public class SettlementFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener, View.OnClickListener {
	FrameLayout mBaseLayout;
	BaseSurfaceView mSurfaceView;
	View mUIView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//無地のFrameLayoutを作成し、Activityに設定
		mBaseLayout = new FrameLayout(this);
		setContentView(mBaseLayout);

		//SurfaceViewをまず追加
		mSurfaceView = new BaseSurfaceView(this);
		mBaseLayout.addView(mSurfaceView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		//XMLで作成したUIViewを追加
		mUIView = getLayoutInflater().inflate(R.layout.activity_field_settlement,null);
		mBaseLayout.addView(mUIView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		//UIViewの中でIDを探すのに注意
		Button button = (Button)mUIView.findViewById(R.id.part_settle_testButton);
		button.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settlement_field_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_settle_field_load:
				showSystemDialog(SystemMenuEnum.LOAD);
				break;
			case R.id.menu_settle_field_save:
				showSystemDialog(SystemMenuEnum.SAVE);
				break;
			case R.id.menu_settle_field_setting:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_settle_field_return_title:
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
			case ACHIEVEMENTS:
				break;
			case SETTINGS:
				break;
			case VERSION_INFO:
				break;
			case RETURN_TITLE:
				startActivity(new Intent(SettlementFieldActivity.this, TitleActivity.class));
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
			case RETURN_TITLE:
				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View view) {
		if(mSurfaceView.getFlag()){
			mSurfaceView.setFlag(false);
		} else {
			mSurfaceView.setFlag(true);
		}
	}
}
