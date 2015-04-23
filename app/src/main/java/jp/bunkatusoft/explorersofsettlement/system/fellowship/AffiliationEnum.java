package jp.bunkatusoft.explorersofsettlement.system.fellowship;

public enum AffiliationEnum {
	NONE(0),
	PLAYER(1);

	int mAffiliation;

	AffiliationEnum(int affiliation){
		mAffiliation = affiliation;
	}
}
