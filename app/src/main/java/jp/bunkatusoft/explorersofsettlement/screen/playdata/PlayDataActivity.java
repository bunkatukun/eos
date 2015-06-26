package jp.bunkatusoft.explorersofsettlement.screen.playdata;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.screen.ScreenActivity;
import jp.bunkatusoft.explorersofsettlement.screen.localmap.LocalMapActivity;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;
import jp.bunkatusoft.explorersofsettlement.screen.world.WorldFieldActivity;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;

public class PlayDataActivity extends ScreenActivity implements View.OnTouchListener{
	String mCallMyselfActivityName;

	boolean isBlockTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mCallMyselfActivityName = intent.getStringExtra("callActivity");

		initBlockTouch();
		initBackgroundLayout();
		initUILayout();
	}

	@Override
	protected void initBackgroundLayout() {
		mRootLayout.setBackgroundResource(R.drawable.background_playdata_test169);
	}

	@Override
	protected void initSurfaceViewLayout() {

	}

	@Override
	protected void initUILayout() {
		mUILayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.ui_activity_playdata, null);
		mRootLayout.addView(mUILayout);

		Button dataSaveLoadButton = (Button) findViewById(R.id.playdata_part_dataSLButton);
		dataSaveLoadButton.setOnTouchListener(this);
		dataSaveLoadButton.setOnClickListener(mDataSaveLoadButtonClickListener);

		Button dataDeleteButton = (Button) findViewById(R.id.playdata_part_dataDeleteButton);
		dataDeleteButton.setOnTouchListener(this);
		dataDeleteButton.setOnClickListener(mDataDeleteButtonClickListener);

		Button dataListSortButton = (Button) findViewById(R.id.playdata_part_dataListSortButton);
		dataListSortButton.setOnTouchListener(this);
		dataListSortButton.setOnClickListener(mDataListSortButtonClickListener);

		Button backButton = (Button) findViewById(R.id.playdata_part_backButton);
		backButton.setOnTouchListener(this);
		backButton.setOnClickListener(mBackButtonClickListener);
	}

	View.OnClickListener mDataSaveLoadButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		}
	};

	View.OnClickListener mDataDeleteButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		}
	};

	View.OnClickListener mDataListSortButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		}
	};

	View.OnClickListener mBackButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			//TODO 将来的にはResult返却方式にする
			if (!TextUtils.isEmpty(mCallMyselfActivityName)) {
				LogUtil.i("getData : " + mCallMyselfActivityName);
				if("LocalMapActivity".equals(mCallMyselfActivityName)){
					Intent intent = new Intent(PlayDataActivity.this, LocalMapActivity.class);
					startActivity(intent);
				} else if("WorldFieldActivity".equals(mCallMyselfActivityName)){
					Intent intent = new Intent(PlayDataActivity.this, WorldFieldActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(PlayDataActivity.this, TitleActivity.class);
					startActivity(intent);
				}
			} else {
				Intent intent = new Intent(PlayDataActivity.this, TitleActivity.class);
				startActivity(intent);
			}
			finish();
		}
	};

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isBlockTouch;
	}
}
