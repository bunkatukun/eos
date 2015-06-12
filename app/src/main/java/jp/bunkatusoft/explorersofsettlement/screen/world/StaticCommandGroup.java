package jp.bunkatusoft.explorersofsettlement.screen.world;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.LinearButtonCommandGroup;

public class StaticCommandGroup extends LinearButtonCommandGroup{

	interface OnStaticCommandClickListener{
		void OnStaticCommandClick(StaticCommandEnum command);
	}

	OnStaticCommandClickListener mListener;

	public StaticCommandGroup(Context context, RelativeLayout rootLayout, OnStaticCommandClickListener listener) {
		super(context, rootLayout);

		mListener = listener;

		addCommandButton(StaticCommandEnum.PARTY);
		addCommandButton(StaticCommandEnum.INVENTORY);
		addCommandButton(StaticCommandEnum.MOVE);
	}

	@Override
	protected void initBaseLayout(ViewGroup rootLayout) {
		mBaseLayout = new LinearLayout(mContext);
		mBaseLayout.setOrientation(LinearLayout.HORIZONTAL);

		//実装方法の関係上、rootLayoutはここでは使用しない
	}

	@Override
	public LinearLayout getBaseLayout() {
		return mBaseLayout;
	}

	protected void addCommandButton(StaticCommandEnum command) {
		ImageButton imageButton = new ImageButton(mContext);
		imageButton.setImageResource(command.getCommandResourceId());
		imageButton.setBackgroundResource(R.drawable.icon_background);
		imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		final StaticCommandEnum sendCommand = command;
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.OnStaticCommandClick(sendCommand);
			}
		});

		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.setMargins(4,4,4,4);

		mBaseLayout.addView(imageButton,layoutParams);
	}

	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}
}
