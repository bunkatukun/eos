package jp.bunkatusoft.explorersofsettlement.screen.localmap;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.system.LinearButtonCommandGroup;

public class DynamicCommandGroup extends LinearButtonCommandGroup {

	interface OnDynamicCommandClickListener{
		void OnDynamicCommandClick(DynamicCommandEnum command);
	}

	OnDynamicCommandClickListener mListener;

	public DynamicCommandGroup(Context context, ViewGroup rootLayout, OnDynamicCommandClickListener listener) {
		super(context, rootLayout);

		mListener = listener;


	}

	public void setCommandList(List<DynamicCommandEnum> useCommandList) {
		mBaseLayout.removeAllViews();
		if(useCommandList != null && useCommandList.size()>0){
			for(DynamicCommandEnum command : useCommandList){
				addCommandButton(command);
			}
		}
	}

	@Override
	protected void initBaseLayout(ViewGroup rootLayout) {
		mBaseLayout = new LinearLayout(mContext);
		mBaseLayout.setOrientation(LinearLayout.HORIZONTAL);
	}

	@Override
	public LinearLayout getBaseLayout() {
		return mBaseLayout;
	}

	protected void addCommandButton(DynamicCommandEnum command) {
		ImageButton imageButton = new ImageButton(mContext);
		imageButton.setImageResource(command.getCommandResourceId());
		imageButton.setBackgroundResource(R.drawable.icon_background);
		imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		final DynamicCommandEnum sendCommand = command;
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mListener.OnDynamicCommandClick(sendCommand);
			}
		});

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
