package jp.bunkatusoft.explorersofsettlement.debug;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import jp.bunkatusoft.explorersofsettlement.field.SettlementFieldActivity;

/**
 * このActivity および関連項目のみ、可能な限りresourceに分担させないようにする<br>
 * レイアウトや文言はソース中で定義
 */
public class DebugActivity extends FragmentActivity implements View.OnClickListener {

    Context mContext;
    LinearLayout mBaseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mBaseLayout = new LinearLayout(mContext);
        mBaseLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(mBaseLayout);

        mBaseLayout.addView(createButtonStartSettlement());
        mBaseLayout.addView(createButtonStartField());
    }

    private Button createButtonStartSettlement() {
        Button button = new Button(mContext);
        button.setTag(DebugMenu.START_SETTLEMENT.getTag());
        button.setText(DebugMenu.START_SETTLEMENT.getText());
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(this);
        return button;
    }

    private Button createButtonStartField() {
        Button button = new Button(mContext);
        button.setTag(DebugMenu.START_WORLD_MAP.getTag());
        button.setText(DebugMenu.START_WORLD_MAP.getText());
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(this);
        return button;
    }

    @Override
    public void onClick(View view) {
        String tag = view.getTag().toString();
        if (DebugMenu.START_SETTLEMENT.getTag().equals(tag)) {
            startActivity(new Intent(DebugActivity.this, SettlementFieldActivity.class));
            finish();
        } else if (DebugMenu.START_WORLD_MAP.getTag().equals(tag)) {
            //TODO ワールドマップへ遷移
        }
    }
}
