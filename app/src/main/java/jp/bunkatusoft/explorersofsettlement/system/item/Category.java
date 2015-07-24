package jp.bunkatusoft.explorersofsettlement.system.item;

public enum Category implements UnknownCategoryName {
	//TODO Qualityに対応する語句を指定
	ORE {
		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public Filter toFilter() {
			return Filter.MATERIAL;
		}

		@Override
		public String toUnknownName() {
			return UNKNOWN_ORE;
		}
	},
	SWORD {
		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public Filter toFilter() {
			return Filter.EQUIPMENT;
		}

		@Override
		public String toUnknownName() {
			return UNKNOWN_SIMPLE_SWORD;
		}
	},
	LEATHER_ARMOR {
		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public Filter toFilter() {
			return Filter.EQUIPMENT;
		}

		@Override
		public String toUnknownName() {
			return UNKNOWN_SIMPLE_LEATHER_ARMOR;
		}
	},
	PROCESSED_MEAT {
		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public Filter toFilter() {
			return Filter.SUPPLIES;
		}

		@Override
		public String toUnknownName() {
			return UNKNOWN_COOKED_MEAT;
		}
	},
	STATUE {
		@Override
		public String toString() {
			return super.toString();
		}

		@Override
		public Filter toFilter() {
			return Filter.MISCELLANEOUS;
		}

		@Override
		public String toUnknownName() {
			return UNKNOWN_SMALL_STONE_STATUE;
		}
	},;
}
