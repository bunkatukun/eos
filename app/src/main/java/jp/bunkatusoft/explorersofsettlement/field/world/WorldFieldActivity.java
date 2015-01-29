package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

/**
 * Created by m_kagaya on 2014/12/22.
 */
public class WorldFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener,View.OnClickListener{

	/** SurfaceViewのID */
	int VIEW_ID_WORLD_SURFACEVIEW = 1;

	Context mContext;

	/** フィールドデータ用 */
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;

	WorldSurfaceView mSurfaceView;


	View mUIView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();

		setContentView(R.layout.activity_field_world);

		mFieldPieces = WorldFieldUtil.loadFieldPieceData(this,"data/field_piece.json");
		mFieldRoads = WorldFieldUtil.loadFieldRoadData(this,"data/field_road.json");

		//SurfaceViewをまず追加
		mSurfaceView = (WorldSurfaceView) findViewById(R.id.world_part_worldSurfaceViewLayout);
		mSurfaceView.setOnClickListener(this);

		//UIViewを追加
		mUIView = (RelativeLayout) findViewById(R.id.world_part_worldUILayout);
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

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id){
			case R.id.world_part_worldSurfaceViewLayout:
				LogUtil.i("触られた");
				break;
		}
	}
}
