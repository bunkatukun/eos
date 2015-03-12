package jp.bunkatusoft.explorersofsettlement.battle;

import android.view.View;
import android.widget.ImageView;

public class ExtendImageView {
	private String id;
	private ImageView imageView;

	public ExtendImageView(String id, ImageView imageView) {
		this.id = id;
		this.imageView = imageView;
	}

	public String getId() {
		return id;
	}

	public void dead() {
		imageView.setVisibility(View.INVISIBLE);
	}
}
