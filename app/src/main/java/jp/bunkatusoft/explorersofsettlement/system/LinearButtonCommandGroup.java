package jp.bunkatusoft.explorersofsettlement.system;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class LinearButtonCommandGroup {

	protected Context mContext;
	protected LinearLayout mBaseLayout;

	public LinearButtonCommandGroup(Context context, ViewGroup rootLayout){
		mContext = context;

		initBaseLayout(rootLayout);
	}

	protected abstract void initBaseLayout(ViewGroup rootLayout);
	public abstract LinearLayout getBaseLayout();
}
