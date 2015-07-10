package jp.bunkatusoft.explorersofsettlement.event;

public enum EventActionEnum {
	TEXT("text"),
	IMAGE("image"),
	CLEAN("clean"),
	SELECTOR("selector"),
	OPEN("open"),
	WAIT("wait"),
	END("end");

	String action;

	EventActionEnum(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}
