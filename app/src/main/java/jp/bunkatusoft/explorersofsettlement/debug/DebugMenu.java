package jp.bunkatusoft.explorersofsettlement.debug;

/**
 * デバッグメニューの一覧
 */
public enum DebugMenu {
    START_SETTLEMENT("start_settlement", "Start Settlement Test"),
    START_FIELD("start_field", "Start Field Test");

    private String text;
    private String tag;

    DebugMenu(String text, String tag){
        this.text = text;
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }
}
