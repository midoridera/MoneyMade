package midoridera.io.moneymade;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public Realm realm;
    public ListView listView;

    TextView wantTextView;

    EditText jikyuEditText2;

    SharedPreferences pref;

    TextView timePickStartTextView;
    TextView timePickEndTextView;
    TextView jikyuTextView;
    boolean isPushedStart1;
    boolean isPushedStart2;

    int jikyu;
    int hour;
    int minute;

    int goukei;
    int kaisu;
    int hasu;

    int hour1;
    int minute1;
    int hour2;
    int minute2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPushedStart1 =false;
        isPushedStart2 =false;

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(R.id.listView);

        wantTextView = (TextView) findViewById(R.id.wantTextView);

        jikyuTextView = (TextView) findViewById(R.id.jikyuTextView);

        timePickStartTextView = (TextView)findViewById(R.id.timePickStartTextView);
        timePickEndTextView = (TextView)findViewById(R.id.timePickEndTextView);

        pref = getSharedPreferences("pref_memo", MODE_PRIVATE);

        hour1 = pref.getInt("key_hour1", 0);
        minute1 = pref.getInt("key_minute1", 0);
        timePickStartTextView.setText(String.format(Locale.US, "%d:%d", hour1, minute1));

        hour2 = pref.getInt("key_hour2", 0);
        minute2 = pref.getInt("key_minute2", 0);
        timePickEndTextView.setText(String.format(Locale.US, "%d:%d", hour2, minute2));

        jikyu = getIntent().getIntExtra("jikyu", 0);
        jikyuTextView.setText(pref.getString("key_jikyu", ""));
        jikyu = Integer.parseInt(pref.getString("key_jikyu", "0"));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Memo memo = (Memo) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate", memo.updateDate);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder .setMessage("削除しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Memo memo = (Memo) adapterView.getItemAtPosition(position);
                                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                                adapter.remove(memo);

                                final Memo targetMemo = realm.where(Memo.class).equalTo("updateDate",
                                        memo.updateDate).findFirst();

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        targetMemo.deleteFromRealm();
                                    }
                                });

                                Toast.makeText(MainActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("キャンセル", null)
                        .setCancelable(true);

                // show dialog
                builder.show();

                return true;
            }
        });



    }

    public void setMemoList() {
        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = realm.copyFromRealm(results);

        MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setMemoList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();

    }

    public void create(View view){
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }


    public void save(View v) {
        Intent intent = new Intent(this, Entry.class);
        startActivity(intent);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (isPushedStart1) {
            String str = String.format(Locale.US, "%d:%d", hourOfDay, minute);
            timePickStartTextView.setText(str);
            hour1 = hourOfDay;
            minute1 = minute;
            isPushedStart1 = false;

            String timeStart = timePickStartTextView.getText().toString();
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("key_hour1", hourOfDay);
            editor.putInt("key_minute1", minute);
            editor.commit();

        } else if (isPushedStart2) {
            String str = String.format(Locale.US, "%d:%d", hourOfDay, minute);
            timePickEndTextView.setText(str);
            hour2 = hourOfDay;
            minute2 = minute;
            isPushedStart2 = false;

            String timeEnd = timePickEndTextView.getText().toString();
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("key_hour2", hourOfDay);
            editor.putInt("key_minute2", minute);
            editor.commit();
        }


    }

    public void showTimePickerDialogStart(View v) {
        isPushedStart1 =true;
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void showTimePickerDialogEnd(View v) {
        isPushedStart2 =true;
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public  void go (View v) {

        hour = hour2 - hour1;
        minute = minute2 - minute1;

        if (minute < 0) {
            hour = hour - 1;
            minute = minute + 60;
        }

        RealmResults<Memo> results = realm.where(Memo.class).findAll();



        List<Memo> items = new ArrayList<Memo>(results);


        for (Memo memos : items ){

            goukei += Integer.parseInt(memos.content) ;
        }

        kaisu = goukei / (jikyu * hour + jikyu * minute / 60);
        hasu = goukei % (jikyu * hour + jikyu * minute / 60);

        if (hasu > 0) {
            kaisu = kaisu + 1;
        }


        Intent intent = new Intent(this, Memo2Activity.class);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("jikyu", jikyu);
        intent.putExtra("goukei", goukei);
        intent.putExtra("kaisu",kaisu);

        startActivity(intent);

    }

}
