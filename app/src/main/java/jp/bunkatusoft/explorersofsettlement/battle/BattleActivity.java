package jp.bunkatusoft.explorersofsettlement.battle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.R;
import jp.bunkatusoft.explorersofsettlement.battle.core.Battle;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Enemy;
import jp.bunkatusoft.explorersofsettlement.battle.unit.Player;
import jp.bunkatusoft.explorersofsettlement.battle.unit.enemy.Wolf;
import jp.bunkatusoft.explorersofsettlement.battle.unit.player.Wizard;
import jp.bunkatusoft.explorersofsettlement.system.SystemDialog;
import jp.bunkatusoft.explorersofsettlement.system.SystemMenuEnum;

public class BattleActivity extends FragmentActivity implements SystemDialog.OnSystemDialogListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.battle_frame);

		List<Player> playerList = new ArrayList<Player>() {{
			add(new Wizard("1"));
		}};
		List<Enemy> enemyList = new ArrayList<Enemy>() {{
			add(new Wolf("2"));
		}};

		List<ExtendImageView> extendImageViews = new ArrayList<ExtendImageView>();
		extendImageViews.add(new ExtendImageView("1", (ImageView) findViewById(R.id.battle_frame_wizard)));
		extendImageViews.add(new ExtendImageView("2", (ImageView) findViewById(R.id.battle_frame_wolf)));
		BattleViewController battleViewController = new BattleViewController(extendImageViews);
		Battle battle = new Battle(playerList, enemyList, battleViewController);
		battle.exec();
	}

	@Override
	public void OnPositiveClickListener(SystemMenuEnum menuEnum) {

	}

	@Override
	public void OnNegativeClickListener(SystemMenuEnum menuEnum) {

	}
}
