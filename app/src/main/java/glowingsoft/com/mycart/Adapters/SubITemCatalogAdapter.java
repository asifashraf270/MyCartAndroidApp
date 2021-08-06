package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.catalogModel;

/**
 * Created by Asif on 11/30/2018.
 */

public class SubITemCatalogAdapter extends BaseAdapter {
    List<catalogModel> textValue;
    Context context;
    LayoutInflater layoutInflater;

    public SubITemCatalogAdapter(List<catalogModel> textValue, Context context) {
        this.textValue = textValue;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void update(List<catalogModel> value) {
        textValue = value;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return textValue.size();
    }

    @Override
    public Object getItem(int position) {
        return textValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.subitemlist, parent, false);
        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);
        textView.setText(textValue.get(position).getItemValue());
        imageView.setImageResource(textValue.get(position).getImageResource());

        return view;
    }


}
