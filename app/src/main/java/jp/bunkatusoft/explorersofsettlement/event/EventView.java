package jp.bunkatusoft.explorersofsettlement.event;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.util.LogUtil;
import jp.bunkatusoft.explorersofsettlement.util.Util;

public class EventView implements View.OnClickListener {


	public interface OnEventPhase {
		void onEventFinish();
	}

	private static final int TEXTVIEW_MAXROW = 30;
	private static final int TEXTVIEW_FIRSTLINE = 0;
	private static final int TEXTVIEW_MAXLINE = 3;
	private static final int TEXTVIEW_TYPE_SPEED = 50;

	Context mContext;
	OnEventPhase mListener;

	//基本パーツ
	protected LinearLayout mBaseLayout;
	protected FrameLayout mImageFrame;
	protected ImageView mFrontImage;
	protected ImageView mRearImage;
	protected LinearLayout mTextView;
	protected List<LinearLayout> mTextLines = new ArrayList<LinearLayout>();
	protected boolean canTextGoNext = false;

	//[WIP]処理行の設定
	protected int drawLine = TEXTVIEW_FIRSTLINE;

	//拡張パーツ
	LinearLayout mChoiceLayout;

	//TODO 他の方法を考える
	List<Event> mRunEventSet;
	int mNowEventIndex = 0;

	public EventView(Context context, FrameLayout rootLayout, OnEventPhase listener) {
		mContext = context;
		mListener = listener;

		initBaseLayout(rootLayout);
		initImageView();
		initText();
		initChoices(rootLayout);
	}

	private void initBaseLayout(FrameLayout rootLayout) {
		mBaseLayout = new LinearLayout(mContext);
		mBaseLayout.setOrientation(LinearLayout.VERTICAL);
		mBaseLayout.setWeightSum(10);
		mBaseLayout.setBackgroundColor(Color.argb(128, 0, 0, 0));
		LinearLayout.LayoutParams baseLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		rootLayout.addView(mBaseLayout, baseLayoutParams);
	}

	private void initImageView() {
		mImageFrame = new FrameLayout(mContext);
		LinearLayout.LayoutParams imageFrameLayoutParams =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		imageFrameLayoutParams.weight = 3;
		mBaseLayout.addView(mImageFrame, imageFrameLayoutParams);
		FrameLayout.LayoutParams imageLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mRearImage = new ImageView(mContext);
		mRearImage.setVisibility(View.INVISIBLE);
		mImageFrame.addView(mRearImage, imageLayoutParams);
		mFrontImage = new ImageView(mContext);
		mFrontImage.setVisibility(View.VISIBLE);
		mImageFrame.addView(mFrontImage, imageLayoutParams);
	}

	private void initText() {
		mTextView = new LinearLayout(mContext);
		mTextView.setOrientation(LinearLayout.VERTICAL);
		mTextView.setBackgroundResource(R.drawable.background_dialog1);
		LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		textLayoutParams.weight = 7;
		mBaseLayout.addView(mTextView, textLayoutParams);
		mTextView.setOnClickListener(this);
	}

	private void initChoices(FrameLayout rootLayout) {
		mChoiceLayout = new LinearLayout(mContext);
		mChoiceLayout.setOrientation(LinearLayout.VERTICAL);
		FrameLayout.LayoutParams choiceLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		choiceLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		choiceLayoutParams.topMargin = Util.getDensityPoint(mContext, 24);
		rootLayout.addView(mChoiceLayout, choiceLayoutParams);
		mChoiceLayout.setVisibility(View.GONE);
	}

	/**
	 * EventViewの全パーツに対して可視性を設定する
	 *
	 * @param visibility 可視性
	 */
	public void setVisibility(int visibility) {
		mBaseLayout.setVisibility(visibility);
	}

	/**
	 * EventViewの全パーツに対してアニメーションを開始する
	 *
	 * @param animationSet アニメーション
	 */
	public void startAnimation(AnimationSet animationSet) {
		mBaseLayout.startAnimation(animationSet);
	}

	/**
	 * イベントセットを実行する
	 *
	 * @param events イベントセット
	 */
	public void startEvent(List<Event> events) {
		mRunEventSet = new ArrayList<Event>();
		mRunEventSet = events;
		//イベント設定の初期化
		resetText();
		mFrontImage.setImageDrawable(null);
		mChoiceLayout.removeAllViews();
		mNowEventIndex = 0;
		runEvent();
	}

	/**
	 * イベント行を"index=1～"で実行する
	 */
	private void runEvent() {
		this.runEvent(1);
	}

	/**
	 * イベント行を実行する
	 *
	 * @param start 処理を開始するイベント行
	 */
	private void runEvent(int start) {
		if (mRunEventSet.size() <= mNowEventIndex) {
			onEventFinalize();
			return;
		}
		Event nowEvent = mRunEventSet.get(mNowEventIndex);

		if (nowEvent.id < start) {
			//startインデックスまでスキップ
			mNowEventIndex++;
			runEvent(start);
			return;
		}

		switchEvent(nowEvent);
	}

	private void switchEvent(Event nowEvent) {
		switch (nowEvent.action) {
			case TEXT:
				drawText(nowEvent.value);
				break;
			case CLEAN:
				//TODO text以外も削除対象とするかどうか
				resetText();
				mNowEventIndex++;
				runEvent();
				break;
			case IMAGE:
				drawImage(nowEvent.value);
				break;
			case SELECTOR:
				createChoices(nowEvent.choices);
				break;
			case END:
				onEventFinalize();
				return;
			default:
				break;
		}
	}

	/**
	 * イベントセットの実行を終了するとき呼び出すメソッド
	 */
	private void onEventFinalize() {
		LogUtil.i("イベントセットを終了します。");
		mListener.onEventFinish();
	}

	/**
	 * テキスト欄へ文字列を表示する
	 *
	 * @param text 表示対象のテキスト
	 */
	public void drawText(String text) {
		canTextGoNext = false;
		setTextLine(text);

		if (mTextLines.size() <= 0) {
			// 必要ならばここでエラー処理
			return;
		}

		int offset = TEXTVIEW_TYPE_SPEED;
		for (int i = 0; i < mTextLines.size(); i++) {
			if (i < drawLine) {
				continue;
			}
			if (drawLine < TEXTVIEW_MAXLINE - 1) {
				drawLine++;
			}

			for (int j = 0; j < mTextLines.get(i).getChildCount(); j++) {
				AlphaAnimation inAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(mContext, R.anim.fade_in_alpha_01sec);
				AnimationSet animationSet = new AnimationSet(false);
				animationSet.addAnimation(inAlphaAnimation);
				//特別な動き(震える、跳ねる等)をさせる場合、ここにアニメーションを追加
				animationSet.setStartOffset(offset);
				if (j == mTextLines.get(i).getChildCount() - 1 && i == mTextLines.size() - 1) {
					animationSet.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							canTextGoNext = true;
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}
					});
				}

				mTextLines.get(i).getChildAt(j).setVisibility(View.VISIBLE);
				mTextLines.get(i).getChildAt(j).startAnimation(animationSet);
				offset += TEXTVIEW_TYPE_SPEED;
			}
		}
	}

	/**
	 * テキスト欄に表示する文字を設定する
	 *
	 * @param text 表示対象のテキスト
	 */
	private void setTextLine(String text) {
		LinearLayout drawLayout = generateLinearLayout();
		for (int i = 0; i < text.length(); i++) {
			TextView drawChar = new TextView(mContext);
			drawChar.setTextSize(Util.getDensityPoint(mContext, 4));
			drawChar.setText(String.valueOf(text.charAt(i)));
			drawChar.setVisibility(View.INVISIBLE);
			drawLayout.addView(drawChar);

			//TODO ここの値の決め方を決める
			if (i > TEXTVIEW_MAXROW) {
				//TODO 半角はサポートしていないので、それを良しとするかどうか
				setTextLine(text.substring(i + 1));
				break;
			}
		}
	}

	private LinearLayout generateLinearLayout() {
		LinearLayout linearLayout = new LinearLayout(mContext);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		//TODO 指定行数を超える場合、古いのを消す
		if (mTextLines.size() >= TEXTVIEW_MAXLINE) {
			mTextLines.remove(TEXTVIEW_FIRSTLINE);
			mTextView.removeViewAt(TEXTVIEW_FIRSTLINE);
		}
		mTextLines.add(linearLayout);
		mTextView.addView(linearLayout);
		return linearLayout;
	}

	/**
	 * テキスト欄の表示を全削除する
	 */
	public void resetText() {
		mTextLines.clear();
		mTextView.removeAllViews();
		drawLine = TEXTVIEW_FIRSTLINE;
	}

	public void drawImage(String newImage) {
		if (mFrontImage.getDrawable() != null) {
			AlphaAnimation outAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(mContext, R.anim.fade_out_alpha_1sec);
			mFrontImage.startAnimation(outAlphaAnimation);
			mFrontImage.setVisibility(View.INVISIBLE);
			//TODO こちらでリスナが必要か？
		}

		mRearImage.setImageResource(mContext.getResources().getIdentifier(newImage, "drawable", mContext.getPackageName()));
		AlphaAnimation inAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(mContext, R.anim.fade_in_alpha_1sec);
		mRearImage.startAnimation(inAlphaAnimation);
		mRearImage.setVisibility(View.VISIBLE);
		inAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mFrontImage.setImageDrawable(mRearImage.getDrawable());
				mFrontImage.setVisibility(View.VISIBLE);
				mRearImage.setVisibility(View.INVISIBLE);

				mNowEventIndex++;
				runEvent();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	private void createChoices(List<Choices> choicesList) {
		canTextGoNext = false;
		mChoiceLayout.setVisibility(View.VISIBLE);

		LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		textLayoutParams.setMargins(Util.getDensityPoint(mContext, 4), Util.getDensityPoint(mContext, 4), Util.getDensityPoint(mContext, 4), Util.getDensityPoint(mContext, 4));
		textLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		int i = 1;
		for (final Choices c : choicesList) {
			TextView textView = new TextView(mContext);
			textView.setBackgroundResource(R.drawable.command_button);
			textView.setId(i++);
			textView.setText(c.text);
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mChoiceLayout.setVisibility(View.GONE);
					mNowEventIndex++;
					runEvent(c.jump);
				}
			});
			mChoiceLayout.addView(textView, textLayoutParams);
		}
	}

	@Override
	public void onClick(View view) {
		if (canTextGoNext) {
			mNowEventIndex++;
			runEvent();
		}
	}
}
