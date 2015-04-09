package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import jp.bunkatusoft.explorersofsettlement.R;

/**
 * ButtonGroup
 */
public class ButtonGroup implements View.OnClickListener {




	public interface OnClickListener {
		void onClick(ButtonGroupEnum buttonID);
	}

	private Context mContext;
	private OnClickListener mListener;
	private ButtonGroupEnum mButtonGroupID;

	private FrameLayout mBaseLayout;
	private ImageButton mImageButton;
	private TextView mButtonTitleText;
	private ImageView mBadge;

	public ButtonGroup(Context context, ButtonGroupEnum ID, OnClickListener listener, int drawableID) {
		this(context, ID, listener,context.getResources().getDrawable(drawableID));
	}

	public ButtonGroup(Context context, final ButtonGroupEnum ID, final OnClickListener listener, Drawable buttonSource) throws ResourceInitFailedException {
		if (buttonSource == null) {
			throw new ResourceInitFailedException("ButtonGroupのButtonリソースが取得できません");
		}
		mContext = context;
		mListener = listener;
		mButtonGroupID = ID;

		mBaseLayout = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.part_buttongroup, null);

		// ボタンの設定
		mImageButton = (ImageButton) mBaseLayout.findViewById(R.id.part_buttongroup_icon);
		mImageButton.setImageDrawable(buttonSource);
		mImageButton.setOnClickListener(this);

		// テキストの設定
		mButtonTitleText = (TextView) mBaseLayout.findViewById(R.id.part_buttongroup_text);
		mButtonTitleText.setText(ID.getValue());

		// [WIP] バッチの設定
		mBadge = (ImageView) mBaseLayout.findViewById(R.id.part_buttongroup_badge);
	}

	public FrameLayout getBaseLayout() {
		return mBaseLayout;
	}

	public ImageButton getImageButton() {
		return mImageButton;
	}

	public TextView getButtonTitleText() {
		return mButtonTitleText;
	}

	public ImageView getBadge() {
		return mBadge;
	}

	@Override
	 public void onClick(View view) {
		mListener.onClick(mButtonGroupID);
	}
}
