package jp.bunkatusoft.explorersofsettlement.field.settlement;

public class Settlement {
	private String name;

	Settlement(){
		this("Neo-Saitama");
	}

	Settlement(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
}
