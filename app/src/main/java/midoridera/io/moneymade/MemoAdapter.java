package midoridera.io.moneymade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MemoAdapter extends ArrayAdapter<Memo> {

    private LayoutInflater layoutinflater;

    MemoAdapter(Context context, int textViewResourceId, List<Memo> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Memo memo = getItem(position);

        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.layout_item_memo, null);
        }

        TextView thingText = (TextView) convertView.findViewById(R.id.thingText);
        TextView moneyText = (TextView) convertView.findViewById(R.id.moneyText);

        thingText.setText(memo.title);
        moneyText.setText(memo.content);
      
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView content1Text = (TextView) convertView.findViewById(R.id.content1Text);
        TextView content2Text = (TextView) convertView.findViewById(R.id.content2Text);
        TextView content3Text = (TextView) convertView.findViewById(R.id.content3Text);
        TextView content4Text = (TextView) convertView.findViewById(R.id.content4Text);

        titleText.setText(memo.title);
        content1Text.setText(memo.content1);
        content2Text.setText(memo.content2);
        content3Text.setText(memo.content3);
        content4Text.setText(memo.content4);

        return convertView;
    }
}
