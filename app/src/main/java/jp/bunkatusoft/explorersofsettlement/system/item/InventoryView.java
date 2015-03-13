package jp.bunkatusoft.explorersofsettlement.system.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import jp.bunkatusoft.explorersofsettlement.R;

public class InventoryView {

	Context mContext;

	View mBaseView;

	InventoryView(Context context, FrameLayout rootLayout){
		mContext = context;

		initBaseLayout(rootLayout);
	}

	private void initBaseLayout(FrameLayout rootLayout){
		mBaseView = LayoutInflater.from(mContext).inflate(R.layout.part_activity_field_uis, null);
		rootLayout.addView(mBaseView);
	}
}
