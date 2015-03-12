package jp.bunkatusoft.explorersofsettlement.battle;

import java.util.List;

/**
 *
 */
public class BattleViewController {
	public List<ExtendImageView> extendImageViewList;

	public BattleViewController(List<ExtendImageView> extendImageViewList) {
		this.extendImageViewList = extendImageViewList;
	}

	public ExtendImageView findById(String id) {
		for (ExtendImageView i : extendImageViewList) {
			if (i.getId().equals(id)) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
}
