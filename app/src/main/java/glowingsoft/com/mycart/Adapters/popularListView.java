package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Activities.ListDesciption;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

/**
 * Created by Asif on 12/3/2018.
 */

public class popularListView extends BaseAdapter {
    List<String> itemValue;
    Context context;
    LayoutInflater layoutInflater;
    int check = 0;
    List<popularRecordGetModel> alreadySelectedItem;
    String ListName;
    sqlLiteOpenHelper helper;
    int itemCount;
    MediaPlayer mediaPlayer;


    public popularListView(List<String> itemValue, Context context, List<popularRecordGetModel> selecltedItem, String ListName) {
        this.itemValue = itemValue;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        helper = new sqlLiteOpenHelper(context);
        this.alreadySelectedItem = selecltedItem;
        this.ListName = ListName;
        mediaPlayer = MediaPlayer.create(context, R.raw.tik);


    }

    public void update(List<String> values) {
        itemValue = values;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return itemValue.size();
    }

    @Override
    public Object getItem(int position) {
        return itemValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.populer_list_view, parent, false);
        final CounterFab counterFab = view.findViewById(R.id.fab);
        final TextView itemName = view.findViewById(R.id.itemName);
        final TextView counterValue = view.findViewById(R.id.counterValue);
        counterValue.setText("" + 0);
        final ImageView decrement = view.findViewById(R.id.decrease);

        ImageView increment = view.findViewById(R.id.increase);
        for (int i = 0; i < alreadySelectedItem.size(); i++) {
            if (alreadySelectedItem.get(i).getItem().equals(itemValue.get(position))) {
                counterFab.setCount(alreadySelectedItem.get(i).getCount());
                counterValue.setText("" + alreadySelectedItem.get(i).getCount());
            }
        }
        counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counterFab.increase();

                mediaPlayer.start();
                check = 0;
                int id = helper.primaryKey(ListName);

                counterValue.setText("" + counterFab.getCount());

                if (counterFab.getCount() >= 1) {


                    List<popularRecordGetModel> dataBaseChecker = new ArrayList<>();

                    dataBaseChecker = helper.getSubitem(helper.primaryKey(ListName));

                    for (int i = 0; i < dataBaseChecker.size(); i++) {
                        if (dataBaseChecker.get(i).getItem().equals(itemValue.get(position))) {
                            helper.updateSubItem(itemValue.get(position), id, counterFab.getCount());
                            check = 1;

                        }
                    }
                    if (check == 0) {
                        long result = helper.insertSubitemData(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()), id);

                        if (result != -1) {
                            alreadySelectedItem.add(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()));
                            itemCount = ListDesciption.sharedPreferences.getInt("Key", 0) + 1;
                            Log.d("itemCount", String.valueOf(itemCount));
                            ListDesciption.editor.putInt("Key", itemCount);
                            ListDesciption.editor.commit();
                        } else {
                        }
                    }


                }

                helper.updateTotalItem(id);


            }


        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                counterFab.increase();
                counterValue.setText("" + counterFab.getCount());

                check = 0;
                if (counterFab.getCount() >= 1) {
                    decrement.setVisibility(View.VISIBLE);

                    int id = helper.primaryKey(ListName);
                    List<popularRecordGetModel> dataBaseChecker = new ArrayList<>();

                    dataBaseChecker = helper.getSubitem(helper.primaryKey(ListName));

                    for (int i = 0; i < dataBaseChecker.size(); i++) {
                        if (dataBaseChecker.get(i).getItem().equals(itemValue.get(position))) {
                            helper.updateSubItem(itemValue.get(position), id, counterFab.getCount());
                            check = 1;

                        }
                    }
                    if (check == 0) {
                        long result = helper.insertSubitemData(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()), id);
                        ;
                        if (result != -1) {
                            alreadySelectedItem.add(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()));

                            itemCount = ListDesciption.sharedPreferences.getInt("Key", 0) + 1;
                            Log.d("itemCount", String.valueOf(itemCount));
                            ListDesciption.editor.putInt("Key", itemCount);
                            ListDesciption.editor.commit();
                        } else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    helper.updateTotalItem(id);


                }
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterFab.decrease();
                if (counterFab.getCount() == 0) {
                    decrement.setVisibility(View.INVISIBLE);
                } else {
                    mediaPlayer.start();
                }
                counterValue.setText("" + counterFab.getCount());

                check = 0;
                int id = helper.primaryKey(ListName);

                if (counterFab.getCount() >= 1) {

                    List<popularRecordGetModel> dataBaseChecker = new ArrayList<>();

                    dataBaseChecker = helper.getSubitem(helper.primaryKey(ListName));

                    for (int i = 0; i < dataBaseChecker.size(); i++) {
                        if (dataBaseChecker.get(i).getItem().equals(itemValue.get(position))) {
                            helper.updateSubItem(itemValue.get(position), id, counterFab.getCount());
                            check = 1;

                        }
                    }
                    if (check == 0) {
                        long result = helper.insertSubitemData(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()), id);
                        ;
                        if (result != -1) {
                            alreadySelectedItem.add(new popularRecordGetModel(itemValue.get(position), counterFab.getCount()));

                            itemCount = ListDesciption.sharedPreferences.getInt("key", 0) + 1;

                            Log.d("itemCount", String.valueOf(itemCount));
                            ListDesciption.editor.putInt("Key", itemCount);

                            ListDesciption.editor.commit();
                        } else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    helper.deleteITem(itemValue.get(position));
                    alreadySelectedItem.remove(itemValue.get(position));

                    ListDesciption.editor.putInt("Key", ListDesciption.sharedPreferences.getInt("Key", 0) - 1);

                    ListDesciption.editor.commit();


                }
                helper.updateTotalItem(id);

            }
        });
        itemName.setText("" + itemValue.get(position));


        return view;
    }


}
