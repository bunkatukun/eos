package jp.bunkatusoft.explorersofsettlement.screen.record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.screen.title.TitleActivity;

public class RecordActivity extends FragmentActivity implements View.OnTouchListener{
	Context mContext;
	FrameLayout mRootLayout;
	LinearLayout mUILayout;

	boolean isBlockTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mRootLayout = new FrameLayout(this);
		setContentView(mRootLayout);

		initBlockTouch();
		initScreenBackground();
		initMainLayout();
	}

	private void initScreenBackground() {
		mRootLayout.setBackgroundResource(R.drawable.background_record_test169);
	}

	private void initMainLayout() {
		mUILayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.ui_activity_record, null);
		mRootLayout.addView(mUILayout);

		Button achievementListButton = (Button) findViewById(R.id.record_part_achievementsListButton);
		achievementListButton.setOnTouchListener(this);
		achievementListButton.setOnClickListener(mAchievementsListButtonClickListener);

		Button dataListButton = (Button) findViewById(R.id.record_part_dataListButton);
		dataListButton.setOnTouchListener(this);
		dataListButton.setOnClickListener(mDataListButtonClickListener);

		Button backButton = (Button) findViewById(R.id.record_part_backButton);
		backButton.setOnTouchListener(this);
		backButton.setOnClickListener(mBackButtonClickListener);
	}

	private void initBlockTouch() {
		isBlockTouch = false;
	}

	private void setBlockTouch(boolean block) {
		isBlockTouch = block;
	}

	public View.OnClickListener mAchievementsListButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		}
	};

	public View.OnClickListener mDataListButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		}
	};

	public View.OnClickListener mBackButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(RecordActivity.this, TitleActivity.class);
			startActivity(intent);
			finish();
		}
	};

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return isBlockTouch;
	}
}
