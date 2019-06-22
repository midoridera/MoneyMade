package midoridera.io.moneymade;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import io.realm.Realm;

import static android.support.v4.os.LocaleListCompat.create;

public class Deletedialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("！")
                .setMessage("削除しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed

//                        Memo memo = (Memo) adapterView.getItemAtPosition(position);
//
//                        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
//
//                        adapter.remove(memo);
//
//                        final Memo targetMemo = realm.where(Memo.class).equalTo("updateDate",
//                                memo.updateDate).findFirst();
//
//                        realm.executeTransaction(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                targetMemo.deleteFromRealm();
//                            }
//                        });

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
//                .create();
    }

    @Override
    public void onPause() {
        super.onPause();

        // onPause でダイアログを閉じる場合
        dismiss();
    }
}
