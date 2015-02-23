package jp.bunkatusoft.explorersofsettlement.util;

import java.util.NoSuchElementException;

/**
 * 自作アニメーションセットの識別定数
 */
public enum CustomAnimationEnum {
	PROTRUDE_IN_FROM_CENTER(0),
	RECEDE_OUT_TO_CENTER(1),
	SLIDE_IN_FROM_RIGHT_10P(2),
	SLIDE_OUT_TO_RIGHT_10P(3);

	int animationNum;

	CustomAnimationEnum(int animationNum){this.animationNum = animationNum;}

	public int getAnimationNum(){return animationNum;}

	public static CustomAnimationEnum valueOf(int phase) {
		CustomAnimationEnum[] enumArray = CustomAnimationEnum.values();
		for (CustomAnimationEnum singleEnum : enumArray) {
			if (phase == singleEnum.animationNum) {
				return singleEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
