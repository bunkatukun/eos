package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationEnum;
import jp.bunkatusoft.explorersofsettlement.util.CustomAnimationUtil;

//TODO 呼び出し側で"必要になったら"newで作成される
//TODO リスナでのコールバック実行後、自分を非表示にする
public class SystemDialogView implements View.OnClickListener {

	public interface OnDialogClickListener {
		void onPositiveClick(SystemMenuEnum menu);

		void onNegativeClick(SystemMenuEnum menu);
	}

	Context mContext;
	OnDialogClickListener mListener;
	SystemMenuEnum mSystemMenu;

	protected FrameLayout mRootLayout;
	protected RelativeLayout mBaseLayout;
	protected TextView mBodyText;
	protected Button mPositiveButton;
	protected Button mNegativeButton;

	public SystemDialogView(Context context, FrameLayout rootLayout, OnDialogClickListener listener, String bodyMsg, String positiveMsg, String negativeMsg, SystemMenuEnum systemMenu) {
		mContext = context;
		mListener = listener;
		mSystemMenu = systemMenu;
		mRootLayout = rootLayout;
		if (TextUtils.isEmpty(positiveMsg) && TextUtils.isEmpty(negativeMsg)) {
			//選択肢が無い
			return;
		}
		initBaseLayout();
		initBodyText(bodyMsg);
		initPositiveButton(positiveMsg);
		initNegativeButton(negativeMsg);
	}

	private void initBaseLayout() {
		mBaseLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.overlay_system_dialog, null);
		mRootLayout.addView(mBaseLayout);
	}

	private void initBodyText(String message) {
		mBodyText = (TextView) mBaseLayout.findViewById(R.id.systemDialog_part_bodyText);
		mBodyText.setText(message);
	}

	private void initPositiveButton(String message) {
		mPositiveButton = (Button) mBaseLayout.findViewById(R.id.systemDialog_part_positiveButton);
		if (TextUtils.isEmpty(message)) {
			mPositiveButton.setVisibility(View.GONE);
			return;
		}
		mPositiveButton.setText(message);
		mPositiveButton.setOnClickListener(this);
	}

	private void initNegativeButton(String message) {
		mNegativeButton = (Button) mBaseLayout.findViewById(R.id.systemDialog_part_negativeButton);
		if (TextUtils.isEmpty(message)) {
			mNegativeButton.setVisibility(View.GONE);
			return;
		}
		mNegativeButton.setText(message);
		mNegativeButton.setOnClickListener(this);
	}

	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	public void startAnimation(boolean isOpen) {
		if (isOpen) {
			mBaseLayout.startAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.FADE_IN));
		} else {
			mBaseLayout.startAnimation(CustomAnimationUtil.generateCustomAnimation(mContext, CustomAnimationEnum.FADE_OUT));
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.systemDialog_part_positiveButton:
				mListener.onPositiveClick(mSystemMenu);
				startAnimation(false);
				mRootLayout.removeView(mBaseLayout);
				break;
			case R.id.systemDialog_part_negativeButton:
				mListener.onNegativeClick(mSystemMenu);
				startAnimation(false);
				mRootLayout.removeView(mBaseLayout);
				break;
			default:
				break;
		}
	}
}
