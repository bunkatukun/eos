package jp.bunkatusoft.explorersofsettlement.system;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.bunkatusoft.explorersofsettlement.R;

/**
 * Created by bunkatukun on 2014/07/04.
 */
public class SystemDialog extends DialogFragment implements View.OnClickListener {

    private static final String EXTRA_BODY_MESSAGE = "bodymsg";
    private static final String EXTRA_POSBTN_MESSAGE = "posmsg";
    private static final String EXTRA_NEGBTN_MESSAGE = "negmsg";
    private static final String EXTRA_ACTION_CODE = "actioncode";



	public interface OnSystemDialogListener {
        void OnPositiveClickListener(SystemMenuEnum menuEnum);
        void OnNegativeClickListener(SystemMenuEnum menuEnum);
    }

    private OnSystemDialogListener mListener;


    /**
     * システム通知用、簡易ダイアログのインスタンスを取得する
     * @param fragment  フラグメントで起動する場合のフラグメント
     * @param bodyMsg   本文文字列
     * @param posMsg    肯定ボタン (null指定で非表示)
     * @param negMsg    否定ボタン (null指定で非表示)
     * @return ダイアログのインスタンス
     * @throws IllegalStateException    いずれのボタンも非表示指定になっている
     */
    public static SystemDialog newInstance(Fragment fragment,String bodyMsg,String posMsg,String negMsg,SystemMenuEnum menuEnum) throws IllegalStateException{
        SystemDialog systemDialog = new SystemDialog();
        systemDialog.setTargetFragment(fragment,0);

        if(TextUtils.isEmpty(posMsg) && TextUtils.isEmpty(negMsg)){
            throw new IllegalStateException();
        }

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_BODY_MESSAGE,bodyMsg);
        bundle.putString(EXTRA_POSBTN_MESSAGE,posMsg);
        bundle.putString(EXTRA_NEGBTN_MESSAGE,negMsg);
        bundle.putInt(EXTRA_ACTION_CODE,menuEnum.getPhaseValue());
        systemDialog.setArguments(bundle);

        return systemDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Fragment tgtFragment = this.getTargetFragment();
        try {
            mListener = (tgtFragment != null) ? (OnSystemDialogListener) tgtFragment : (OnSystemDialogListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_system,null);

		TextView title = (TextView) view.findViewById(R.id.dialog_system_part_titleText);
		title.setText(R.string.sys_dialogtitle);

		TextView body = (TextView) view.findViewById(R.id.dialog_system_part_bodyText);
		body.setText(getArguments().getString(EXTRA_BODY_MESSAGE));

		Button positiveButton = (Button) view.findViewById(R.id.dialog_system_part_positiveButton);
		String posMsg = getArguments().getString(EXTRA_POSBTN_MESSAGE);
		if(!TextUtils.isEmpty(posMsg)){
			positiveButton.setText(posMsg);
			positiveButton.setOnClickListener(this);
		} else {
			positiveButton.setVisibility(View.GONE);
		}

		Button negativeButton = (Button) view.findViewById(R.id.dialog_system_part_negativeButton);
		String negMsg = getArguments().getString(EXTRA_NEGBTN_MESSAGE);
		if(!TextUtils.isEmpty(negMsg)){
			negativeButton.setText(negMsg);
			negativeButton.setOnClickListener(this);
		} else {
			negativeButton.setVisibility(View.GONE);
		}

		builder.setView(view);
        return builder.create();
    }

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.dialog_system_part_positiveButton:
				mListener.OnPositiveClickListener(SystemMenuEnum.getEnum(getArguments().getInt(EXTRA_ACTION_CODE)));
				break;
			case R.id.dialog_system_part_negativeButton:
				mListener.OnNegativeClickListener(SystemMenuEnum.getEnum(getArguments().getInt(EXTRA_ACTION_CODE)));
				break;
			default:
				break;
		}
		dismiss();
	}
//    @Override
//     public void onClick(DialogInterface dialogInterface, int which) {
//        switch (which){
//            case DialogInterface.BUTTON_POSITIVE:
//                mListener.OnPositiveClickListener(getArguments().getInt(EXTRA_ACTION_CODE));
//                break;
//            case DialogInterface.BUTTON_NEGATIVE:
//                mListener.OnNegativeClickListener(getArguments().getInt(EXTRA_ACTION_CODE));
//                break;
//            default:
//                break;
//        }
//        dismiss();
//    }
}
