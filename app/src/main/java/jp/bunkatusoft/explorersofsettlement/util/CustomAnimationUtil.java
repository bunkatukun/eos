package jp.bunkatusoft.explorersofsettlement.util;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import jp.bunkatusoft.explorersofsettlement.R;

/**
 * 自作アニメーションセットに関するクラス
 */
public class CustomAnimationUtil {
	public static AnimationSet generateCustomAnimation(Context context, CustomAnimationEnum animationType){
		AnimationSet resultAnimationSet = new AnimationSet(false);
		switch (animationType){
			case SLIDE_IN_FROM_RIGHT_10P:
				TranslateAnimation inTranslateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_in_from_right);
				AlphaAnimation inAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_in_alpha_03sec);
				resultAnimationSet.addAnimation(inTranslateAnimation);
				resultAnimationSet.addAnimation(inAlphaAnimation);
				break;
			case SLIDE_OUT_TO_RIGHT_10P:
				TranslateAnimation outTranslateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_out_to_right);
				AlphaAnimation outAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_out_alpha_03sec);
				resultAnimationSet.addAnimation(outTranslateAnimation);
				resultAnimationSet.addAnimation(outAlphaAnimation);
				break;
			case PROTRUDE_IN_FROM_CENTER:
				ScaleAnimation protrudeScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.protrude_from_center);
				AlphaAnimation protrudeAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_in_alpha_03sec);
				resultAnimationSet.addAnimation(protrudeScaleAnimation);
				resultAnimationSet.addAnimation(protrudeAlphaAnimation);
				break;
			case RECEDE_OUT_TO_CENTER:
				ScaleAnimation recedeScaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.recede_to_center);
				AlphaAnimation recedeAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_out_alpha_03sec);
				resultAnimationSet.addAnimation(recedeScaleAnimation);
				resultAnimationSet.addAnimation(recedeAlphaAnimation);
				break;
			case FADE_IN:
				inAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_in_alpha_03sec);
				resultAnimationSet.addAnimation(inAlphaAnimation);
				break;
			case FADE_OUT:
				outAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.fade_out_alpha_03sec);
				resultAnimationSet.addAnimation(outAlphaAnimation);
				break;
			default:
				break;
		}
		return resultAnimationSet;
	}

	public static AnimationSet generateMenuAnimation(Context context, CustomAnimationEnum animationType, int percent){
		AnimationSet resultAnimationSet = new AnimationSet(false);
		switch (animationType){
			case RISE_PERCENT:
				AlphaAnimation riseAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context,R.anim.fade_out_alpha_03sec);
				TranslateAnimation riseTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-percent);
				riseTranslateAnimation.setDuration(300);
				riseTranslateAnimation.setFillEnabled(true);
				riseTranslateAnimation.setFillAfter(true);
				resultAnimationSet.addAnimation(riseAlphaAnimation);
				resultAnimationSet.addAnimation(riseTranslateAnimation);
				break;
			case DESCENT_PERCENT:
				AlphaAnimation descentAlphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context,R.anim.fade_in_alpha_03sec);
				TranslateAnimation descentTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,-percent,Animation.RELATIVE_TO_SELF,0);
				descentTranslateAnimation.setDuration(300);
				descentTranslateAnimation.setFillEnabled(true);
				descentTranslateAnimation.setFillAfter(false);
				resultAnimationSet.addAnimation(descentAlphaAnimation);
				resultAnimationSet.addAnimation(descentTranslateAnimation);
				break;
			default:
				break;
		}
		return resultAnimationSet;
	}
}
