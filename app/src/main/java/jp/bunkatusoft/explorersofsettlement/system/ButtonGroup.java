package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
public class ButtonGroup {

	public interface OnClickListener {
		void onClick(ButtonGroupEnum buttonID);
	}

	private OnClickListener mListener;

	private Context mContext;
	private FrameLayout mBaseLayout;
	private ImageButton mImageButton;
	private TextView mButtonTitleText;
	private ImageView mBadge;

	public ButtonGroup(Context context, ButtonGroupEnum ID, OnClickListener listener, int drawableID, String textSource) {
		this(context, ID, listener, context.getResources().getDrawable(drawableID), textSource);
	}

	public ButtonGroup(Context context, final ButtonGroupEnum ID, final OnClickListener listener, Drawable buttonSource, String textSource) throws ResourceInitFailedException {
		if (buttonSource == null) {
			throw new ResourceInitFailedException("ButtonGroupのButtonリソースが取得できません");
		}
		mContext = context;
		mListener = listener;

		mBaseLayout = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.part_buttongroup, null);

		// ボタンの設定
		mImageButton = (ImageButton) mBaseLayout.findViewById(R.id.part_buttongroup_icon);
		mImageButton.setImageDrawable(buttonSource);
		mImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.onClick(ID);
			}
		});

		// テキストの設定
		mButtonTitleText = (TextView) mBaseLayout.findViewById(R.id.part_buttongroup_text);
		if (!TextUtils.isEmpty(textSource)) {
			mButtonTitleText.setText(textSource);
		}

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
}
