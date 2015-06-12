package jp.bunkatusoft.explorersofsettlement.screen.world;

public class FieldRoad {
	public int id;
	public int connectA;
	public int connectB;
	public FieldRoadDirectivity directivity;

	public enum FieldRoadDirectivity {
		mutual(0),
		AtoB(1),
		BtoA(2);

		private int id;

		FieldRoadDirectivity(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}
}
