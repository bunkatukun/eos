package jp.bunkatusoft.explorersofsettlement.field.world;

import java.util.NoSuchElementException;

/**
 * フィールドチップの種類(役割)
 */
public enum PieceTypeEnum {
	/** 特になし */
	NONE(0),
	/** 開拓地(本拠地) */
	SETTLEMENT(1),
	/** ダンジョン */
	DUNGEON(2),
	/** 本拠地でないNPC開拓地 */
	OUTPOST(3),
	/** 資源産出地 */
	RESOURCE(4),
	/** 古代の遺跡 */
	RUIN(5),
	/** NPCの小規模拠点 */
	HIDE(6);

	int pieceType;

	PieceTypeEnum(int pieceType) { this.pieceType = pieceType;}

	public int getPieceType(){ return pieceType; }

	public static PieceTypeEnum valueOf(int pieceType) {
		PieceTypeEnum[] enumArray = PieceTypeEnum.values();
		for(PieceTypeEnum singleEnum : enumArray){
			if(pieceType == singleEnum.pieceType){
				return singleEnum;
			}
		}
		throw new NoSuchElementException();
	}
}
