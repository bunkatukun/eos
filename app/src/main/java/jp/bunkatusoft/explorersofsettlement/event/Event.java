package jp.bunkatusoft.explorersofsettlement.event;

import java.util.List;

public class Event {
	public int id;
	public EventActionEnum action;
	public String value;
	public List<Choices> choices;
}
