package jp.bunkatusoft.explorersofsettlement.system;

/**
 * 動的定義ViewのID発行に用いるメソッド
 */
public class Incrementer {
	private int mValue;

	public Incrementer(int seed) {
		mValue = seed;
	}

	public int getValue() {
		mValue++;
		return mValue - 1;
	}
}
