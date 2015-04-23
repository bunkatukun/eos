package jp.bunkatusoft.explorersofsettlement.debug;

/**
 * デバッグメニューの一覧
 */
public enum DebugMenu {
    START_SETTLEMENT("start_settlement", "Start Settlement Test"),
    START_WORLD_MAP("start_world_map", "Start World Map Test"),
	START_EXPLORE("start_Explore", "Start Explore"),
    START_TEST_MAP("start_test_map", "Start Test Map");


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
