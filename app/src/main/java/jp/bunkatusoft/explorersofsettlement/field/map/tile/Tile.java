package jp.bunkatusoft.explorersofsettlement.field.map.tile;

import jp.bunkatusoft.explorersofsettlement.R;

public enum Tile {
	TEST(R.drawable.tile_test001);

	private final int resourceId;

	Tile(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getResourceId() {
		return resourceId;
	}
}
