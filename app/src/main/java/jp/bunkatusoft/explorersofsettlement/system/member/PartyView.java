package jp.bunkatusoft.explorersofsettlement.system.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.fellowship.Fellowship;
import jp.bunkatusoft.explorersofsettlement.system.fellowship.PartyCategoryEnum;

public class PartyView implements View.OnClickListener, View.OnTouchListener {



	public interface OnPartyActionListener {
		void onPartyClose();
	}

	private Context mContext;
	private OnPartyActionListener mListener;
	Fellowship mFellowship;

	RelativeLayout mBaseLayout;

	// コマンドボタン : depth-1
	PartyCategoryEnum mSelectCategory;
	Button mCategoryStatusButton;
	Button mCategoryEquipmentButton;
	Button mCategoryCommandsButton;

	Button mCloseButton;

	public PartyView(Context context, FrameLayout rootLayout, OnPartyActionListener listener, Fellowship fellowship) {
		mContext = context;
		mListener = listener;
		mFellowship = fellowship;

		initBaseLayout(rootLayout);
		initSystemButtons();
		initCategoryButtons();
	}

	private void initBaseLayout(FrameLayout rootLayout){
		mBaseLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.overlay_party,null);
		rootLayout.addView(mBaseLayout);
	}

	private void initSystemButtons(){
		mCloseButton = (Button) mBaseLayout.findViewById(R.id.part_party_closePartyButton);
		mCloseButton.setOnClickListener(this);
		mCloseButton.setOnTouchListener(this);
	}

	private void initCategoryButtons(){
		mCategoryStatusButton = (Button) mBaseLayout.findViewById(R.id.part_party_categoryStatusButton);
		mCategoryStatusButton.setOnClickListener(this);
		mCategoryStatusButton.setOnTouchListener(this);
		mCategoryEquipmentButton = (Button) mBaseLayout.findViewById(R.id.part_party_categoryEquipmentButton);
		mCategoryEquipmentButton.setOnClickListener(this);
		mCategoryEquipmentButton.setOnTouchListener(this);
		mCategoryCommandsButton = (Button) mBaseLayout.findViewById(R.id.part_party_categoryCommandsButton);
		mCategoryCommandsButton.setOnClickListener(this);
		mCategoryCommandsButton.setOnTouchListener(this);

		//TODO ちぇんじせれくしょん
	}

	@Override
	public void onClick(View view) {
		int viewId = view.getId();
		switch (viewId){
			case R.id.part_party_categoryStatusButton:
				break;
			case R.id.part_party_categoryEquipmentButton:
				break;
			case R.id.part_party_categoryCommandsButton:
				break;
			case R.id.part_party_closePartyButton:
				mListener.onPartyClose();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return false;
	}

	/**
	 * PartyViewの全パーツに対して可視性を設定する
	 *
	 * @param visibility 可視性
	 */
	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	/**
	 * PartyViewの全パーツに対してアニメーションを開始する
	 *
	 * @param animationSet アニメーション
	 */
	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}
}