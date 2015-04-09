package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;

public class SystemMenuView implements View.OnTouchListener {

	public interface OnMenuChoiceListener {
		void onSelectMenu(SystemMenuEnum select);
	}

	Context mContext;
	OnMenuChoiceListener mListener;
	boolean isBlockTouch;

	protected RelativeLayout mRootLayout;
	protected LinearLayout mBaseLayout;
	protected List<TextView> mMenuTextList = new ArrayList<TextView>();

	/**
	 * コンストラクタ
	 *
	 * @param context      コンテキスト
	 * @param rootLayout   追加元のレイアウト
	 * @param listener     リスナ
	 * @param baseButtonID メニューボタンのＩＤ
	 * @param menuArray    表示するメニューの一覧リスト
	 */
	public SystemMenuView(Context context, RelativeLayout rootLayout, OnMenuChoiceListener listener, int baseButtonID, ArrayList<SystemMenuEnum> menuArray) {
		mContext = context;
		mListener = listener;
		mRootLayout = rootLayout;

		initBlockTouch();
		initBaseLayout(baseButtonID);
		initMenuTexts(menuArray);
		initTextListVisibility();
	}

	/**
	 * システムメニュー欄の土台となるLayoutを初期化する
	 *
	 * @param baseButtonID メニューボタンのＩＤ
	 */
	private void initBaseLayout(int baseButtonID) {
		mBaseLayout = new LinearLayout(mContext);
		mBaseLayout.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayoutParams.setMargins(4, 4, 4, 4);
		relativeLayoutParams.addRule(RelativeLayout.BELOW, baseButtonID);
		relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mRootLayout.addView(mBaseLayout, relativeLayoutParams);
	}

	/**
	 * システムメニューの項目を初期化する
	 *
	 * @param menuArray 実装するメニュー項目の一覧
	 */
	private void initMenuTexts(ArrayList<SystemMenuEnum> menuArray) {
		if (menuArray != null) {
			for (SystemMenuEnum menu : menuArray) {
				addMenuText(menu);
			}
		}
	}

	/**
	 * システムメニューの項目欄を作成・追加する
	 *
	 * @param menu 実装するメニューの項目
	 */
	private void addMenuText(final SystemMenuEnum menu) {
		TextView textView = new TextView(mContext);
		textView.setText(menu.getPhase());
		textView.setBackgroundResource(R.drawable.frame_textview_01);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.onSelectMenu(menu);
			}
		});
		textView.setOnTouchListener(this);
		LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textLayoutParams.setMargins(4, 4, 4, 4);
		mBaseLayout.addView(textView, textLayoutParams);
		mMenuTextList.add(textView);
	}

	/**
	 * SystemMenuViewの全Viewに対して可視性を設定する
	 *
	 * @param visibility 可視性
	 */
	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	/**
	 * システムメニュー項目群の可視性を初期化する<br>
	 * 初期状態は非表示・非存在とする
	 */
	private void initTextListVisibility() {
		if (mMenuTextList != null && mMenuTextList.size() > 0) {
			for (int i = 0; i < mMenuTextList.size(); i++) {
				mMenuTextList.get(i).setVisibility(View.GONE);
			}
		}
	}

	/**
	 * システムメニュー項目群のアニメーションを設定・開始する
	 *
	 * @param isOpen 開いた状態であるか
	 */
	public void startAnimation(boolean isOpen) {
		if (mMenuTextList != null && mMenuTextList.size() > 0) {
			for (int i = 0; i < mMenuTextList.size(); i++) {
				mMenuTextList.get(i).startAnimation(CustomAnimationUtil.generateMenuAnimation(mContext, (isOpen ? CustomAnimationEnum.RISE_PERCENT : CustomAnimationEnum.DESCENT_PERCENT), i + 1));
				mMenuTextList.get(i).setVisibility((isOpen ? View.INVISIBLE : View.VISIBLE));
			}
		}
	}

	/**
	 * SystemMenuViewのタップ操作許可フラグを設定する
	 *
	 * @param block View内のタップ操作を許可するか
	 */
	public void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	/**
	 * SystemMenuViewのタップ操作許可フラグを初期化する
	 */
	private void initBlockTouch() {
		isBlockTouch = false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isBlockTouch;
	}
}
