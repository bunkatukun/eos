package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;

/**
 * Created by m_kagaya on 2014/12/22.
 */
public class WorldFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener{

	Context mContext;

	/** フィールドデータ用 */
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;

	FrameLayout mBaseLayout;
	WorldSurfaceView mSurfaceView;

	View mUIView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();

		mBaseLayout = new FrameLayout(this);
		setContentView(mBaseLayout);

		mFieldPieces = WorldFieldUtil.loadFieldPieceData(this,"data/field_piece.json");
		mFieldRoads = WorldFieldUtil.loadFieldRoadData(this,"data/field_road.json");

		//SurfaceViewをまず追加
		mSurfaceView = new WorldSurfaceView(this);
		mBaseLayout.addView(mSurfaceView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		//XMLで作成したUIViewを追加
		mUIView = getLayoutInflater().inflate(R.layout.activity_field_world, null);
		mBaseLayout.addView(mUIView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.world_field_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.menu_world_field_load:
				showSystemDialog(SystemMenuEnum.LOAD);
				break;
			case R.id.menu_world_field_save:
				showSystemDialog(SystemMenuEnum.SAVE);
				break;
			case R.id.menu_world_field_setting:
				showSystemDialog(SystemMenuEnum.SETTINGS);
				break;
			case R.id.menu_world_field_return_title:
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
				startActivity(new Intent(WorldFieldActivity.this, TitleActivity.class));
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
