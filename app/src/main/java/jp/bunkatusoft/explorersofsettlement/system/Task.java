package jp.bunkatusoft.explorersofsettlement.system;

import android.graphics.Canvas;

/**
 * Created by m_kagaya on 2015/01/28.
 */
public abstract class Task {

	private TaskPhaseEnum mTaskPhase = TaskPhaseEnum.INITIALIZE;

	public boolean onUpdate() {
		runPhaseWork();
		return true;
	}

	public void onDraw(Canvas canvas) {
	}

	/**
	 * Update処理中のフェイズ更新メソッド
	 */
	private void runPhaseWork() {
		switch (mTaskPhase) {
			case INITIALIZE:
				onInitialize();
				mTaskPhase = TaskPhaseEnum.STARTING;
				break;
			case STARTING:
				if(onStarting()) {
					mTaskPhase = TaskPhaseEnum.RUNNING;
				}
				break;
			case RUNNING:
				onRunning();
				break;
			case STOPPING:
				if(onStopping()) {
					mTaskPhase = TaskPhaseEnum.FINALIZE;
				}
				break;
			case FINALIZE:
				onFinalize();
				mTaskPhase = TaskPhaseEnum.END;
				break;
			case END:
				onEnd();
				break;
			default:
				break;
		}
	}

	/**
	 * 現在のフェイズを取得する
	 * @return	現在のフェイズ
	 */
	public TaskPhaseEnum getTaskPhase() {
		return mTaskPhase;
	}

	/**
	 * 現在のフェイズがEndでない場合、Stoppingフェイズを開始する
	 */
	public void finish() {
		if(!mTaskPhase.equals(TaskPhaseEnum.END)) {
			mTaskPhase = TaskPhaseEnum.STOPPING;
		}
	}

	/**
	 * InitializeフェイズのUpdate処理
	 */
	public void onInitialize() {
	}

	/**
	 * StartingフェイズのUpdate処理
	 * @return	trueでStartingフェイズを終了する。falseは継続する。
	 */
	public boolean onStarting() {
		return true;
	}

	/**
	 * RunningフェイズのUpdate処理
	 */
	public void onRunning() {
	}

	/**
	 * StoppingフェイズのUpdate処理
	 * @return	trueでStoppingフェイズを終了する。falseなら継続する。
	 */
	public boolean onStopping() {
		return true;
	}

	/**
	 * FinalizeフェイズのUpdate処理
	 */
	public void onFinalize() {
	}

	/**
	 * Endフェイズの処理
	 */
	public void onEnd() {
	}
}
