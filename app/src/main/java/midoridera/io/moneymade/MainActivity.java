package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public Realm realm;
    public ListView listView;

    TextView wantTextView;

    EditText jikyuEditText2;
    EditText timeEditText2;

//    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(R.id.listView);

        wantTextView = (TextView) findViewById(R.id.wantTextView);

        jikyuEditText2 = (EditText) findViewById(R.id.jikyuEditText2);
        timeEditText2 = (EditText) findViewById(R.id.timeEditText2);
//
//        pref = getSharedPreferences("pref_memo", MODE_PRIVATE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Memo memo = (Memo) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate", memo.updateDate);
                startActivity(intent);
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

//    public void save(View v) {
//        String jikyuText = jikyuEditText.getText().toString();
//        String timeText = timeEditText.getText().toString();
//
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("key_jikyu", jikyuText);
//        editor.putString("key_time", timeText);
//        editor.commit();
//
//        finish();
//
//        jikyuEditText.setText(pref.getString("key_jikyu", ""));
//        timeEditText.setText(pref.getString("key_time", ""));
//
//    }


    public void memo(View v) {

        Intent intent = new Intent(this, Memo2Activity.class);
        startActivity(intent);

    }

}
