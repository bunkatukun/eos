package jp.bunkatusoft.explorersofsettlement.system;

/**
 * Created by m_kagaya on 2015/01/29.
 */
public class Touch {
	//TODO なんかクラス名が標準と被りそう もっといい名前に変更する
	public boolean isTouch;
	public int touchX;
	public int touchY;
	public int scrollX;
	public int scrollY;

	public Touch(){
		isTouch = false;
		touchX = 0;
		touchY = 0;
		scrollX = 0;
		scrollY = 0;
	}
}
