package jp.bunkatusoft.explorersofsettlement.system;

public class ResourceInitFailedException extends ResourceException {
	public ResourceInitFailedException(String cause) {
		super("リソースの初期化に失敗しました : " + cause);
	}
}
