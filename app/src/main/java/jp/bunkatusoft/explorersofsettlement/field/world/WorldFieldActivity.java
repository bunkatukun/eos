package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.ResourceInitFailedException;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;
import jp.bunkatusoft.explorersofsettlement.title.TitleActivity;


public class WorldFieldActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener, View.OnClickListener {

	Context mContext;

	/**
	 * フィールドデータ用
	 */
	List<FieldPiece> mFieldPieces;
	List<FieldRoad> mFieldRoads;

	View mCommandsView;
	View mCommandEnterGroup;
	View mCommandMembersGroup;
	View mCommandItemsGroup;
	View mCommandInfoGroup;
	View mCommandActionsGroup;
	View mCommandLeaveGroup;

	View mOverlayWindow;

	AnimationSet mInAnimation;
	AnimationSet mOutAnimation;
	AnimationSet mProtrudeAnimation;
	AnimationSet mRecedeAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mFieldPieces = new ArrayList<FieldPiece>();
		mFieldRoads = new ArrayList<FieldRoad>();

		mFieldPieces = WorldFieldUtil.loadFieldPieceData(this, "data/field_piece.json");
		mFieldRoads = WorldFieldUtil.loadFieldRoadData(this, "data/field_road.json");

		//アニメーションの初期化処理はViewを扱う前に済ませるべし
		//「右からフェードイン＆スライドイン」するアニメーションセットの設定
		TranslateAnimation inTranslateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_in_from_right);
		AlphaAnimation inAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_in_alpha);
		mInAnimation = new AnimationSet(false);
		mInAnimation.addAnimation(inTranslateAnimation);
		mInAnimation.addAnimation(inAlphaAnimation);

		//「右へフェードアウト＆スライドアウト」するアニメーションセットの設定
		TranslateAnimation outTranslateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_out_to_right);
		AlphaAnimation outAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_out_alpha);
		mOutAnimation = new AnimationSet(false);
		mOutAnimation.addAnimation(outTranslateAnimation);
		mOutAnimation.addAnimation(outAlphaAnimation);

		// 「中央から飛び出し＆フェードイン」するアニメーションセットの設定
		ScaleAnimation protrudeScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.protrude_from_center);
		mProtrudeAnimation = new AnimationSet(false);
		mProtrudeAnimation.addAnimation(protrudeScaleAnimation);
		mProtrudeAnimation.addAnimation(inAlphaAnimation);

		// 「中央へ引っ込み＆フェードアウト」するアニメーションセットの設定
		ScaleAnimation recedeScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.recede_to_center);
		mRecedeAnimation = new AnimationSet(false);
		mRecedeAnimation.addAnimation(recedeScaleAnimation);
		mRecedeAnimation.addAnimation(outAlphaAnimation);


		FrameLayout rootLayout = new FrameLayout(this);
		setContentView(rootLayout);

		//SurfaceViewをまず追加
		WorldSurfaceView surfaceView = new WorldSurfaceView(this);
		surfaceView.setOnClickListener(this);
		rootLayout.addView(surfaceView);

		//UIレイアウトを追加
		RelativeLayout UILayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.part_activity_field_uis, null);
		rootLayout.addView(UILayout);

		//ポップアップウインドウは初期状態で非表示とする
		mOverlayWindow = getLayoutInflater().inflate(R.layout.overlay_window_base, null);
		rootLayout.addView(mOverlayWindow);
		mOverlayWindow.setVisibility(View.GONE);

		Button closeOverlayButton = (Button) mOverlayWindow.findViewById(R.id.general_part_closeOverlayButton);
		closeOverlayButton.setOnClickListener(this);

		mCommandsView = UILayout.findViewById(R.id.world_part_commandsLayout);
		//TODO ここ連続処理なのでまとめること
		mCommandEnterGroup = mCommandsView.findViewById(R.id.world_part_part_commandEnterGroup);
		mCommandMembersGroup = mCommandsView.findViewById(R.id.world_part_part_commandMembersGroup);
		mCommandItemsGroup = mCommandsView.findViewById(R.id.world_part_part_commandItemsGroup);
		mCommandInfoGroup = mCommandsView.findViewById(R.id.world_part_part_commandInfoGroup);
		mCommandActionsGroup = mCommandsView.findViewById(R.id.world_part_part_commandActionsGroup);
		mCommandLeaveGroup = mCommandsView.findViewById(R.id.world_part_part_commandLeaveGroup);
		try {
			initCommandButtonGroup(mCommandEnterGroup);
			initCommandButtonGroup(mCommandMembersGroup);
			initCommandButtonGroup(mCommandItemsGroup);
			initCommandButtonGroup(mCommandInfoGroup);
			initCommandButtonGroup(mCommandActionsGroup);
			initCommandButtonGroup(mCommandLeaveGroup);
		} catch (ResourceInitFailedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.world_field_activity, menu);
		return true;
	}

	/**
	 * コマンドボタン群の初期化を行う
	 *
	 * @param view 初期化するボタン
	 * @throws ResourceInitFailedException viewのIDが取得できない、あるいは存在しない
	 */
	private void initCommandButtonGroup(View view) throws ResourceInitFailedException {
		int baseID = view.getId();
		if (baseID == View.NO_ID) {
			throw new ResourceInitFailedException("ButtonGroupIDが取得できません");
		}
		ImageButton imageButton;
		//TODO ここは簡略化・省略できる場所だが、要素を確定させるのが先
		switch (baseID) {
			case R.id.world_part_part_commandEnterGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandEnterButton);
				imageButton.setOnClickListener(this);
				break;
			case R.id.world_part_part_commandMembersGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandMembersButton);
				imageButton.setOnClickListener(this);
				break;
			case R.id.world_part_part_commandItemsGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandItemsButton);
				imageButton.setOnClickListener(this);
				break;
			case R.id.world_part_part_commandInfoGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandInfoButton);
				imageButton.setOnClickListener(this);
				break;
			case R.id.world_part_part_commandActionsGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandActionsButton);
				imageButton.setOnClickListener(this);
				break;
			case R.id.world_part_part_commandLeaveGroup:
				imageButton = (ImageButton) view.findViewById(R.id.world_part_part_commandLeaveButton);
				imageButton.setOnClickListener(this);
				break;
			default:
				break;
		}
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
		switch (id) {
			//将来的にボタンごとの動作を実装するため、分岐は残しておく
			case R.id.world_part_worldSurfaceViewLayout:
				break;
			case R.id.world_part_part_commandEnterButton:
				//「入る」ボタンの固有動作を追加
			case R.id.world_part_part_commandMembersButton:
				//「パーティ」ボタンの固有動作を追加
			case R.id.world_part_part_commandItemsButton:
				//「荷物」ボタンの固有動作を追加
			case R.id.world_part_part_commandInfoButton:
				//「情報」ボタンの固有動作を追加
			case R.id.world_part_part_commandActionsButton:
				//「アクション」ボタンの固有動作を追加
			case R.id.world_part_part_commandLeaveButton:
				//「他の場所へ」ボタンの固有動作を追加
				mCommandsView.startAnimation(mOutAnimation);
				mCommandsView.setVisibility(View.INVISIBLE);
				mOverlayWindow.startAnimation(mProtrudeAnimation);
				mOverlayWindow.setVisibility(View.VISIBLE);
				break;
			case R.id.general_part_closeOverlayButton:
				//ポップアップウインドウはゲーム内の拡張動作画面として使う予定
				mOverlayWindow.startAnimation(mRecedeAnimation);
				mOverlayWindow.setVisibility(View.INVISIBLE);
				mCommandsView.startAnimation(mInAnimation);
				mCommandsView.setVisibility(View.VISIBLE);
				break;
		}
	}
}