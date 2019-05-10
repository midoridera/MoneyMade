package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Memo2Activity extends AppCompatActivity {

    EditText jikyuEditText;
    EditText timeEditText;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        jikyuEditText = (EditText) findViewById(R.id.jikyuEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);

        pref = getSharedPreferences("pref_memo", MODE_PRIVATE);

        jikyuEditText.setText(pref.getString("key_jikyu", ""));
        timeEditText.setText(pref.getString("key_time", ""));

    }

    public void save(View v) {
        String jikyuText = jikyuEditText.getText().toString();
        String timeText = timeEditText.getText().toString();

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_jikyu", jikyuText);
        editor.putString("key_time", timeText);
        editor.commit();

        finish();

    }

}
