package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pepperonas.materialdialog.MaterialDialog;

import java.util.List;

import glowingsoft.com.mycart.Activities.ListDesciption;
import glowingsoft.com.mycart.Activities.SubItemActivity;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

/**
 * Created by Asif on 12/5/2018.
 */

public class ListDescriptionAdapter extends BaseAdapter {
    List<popularRecordGetModel> model;
    Context context;
    LayoutInflater layoutInflater;
    sqlLiteOpenHelper helper;
    int id;


    public ListDescriptionAdapter(List<popularRecordGetModel> model, Context context) {
        this.model = model;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper = new sqlLiteOpenHelper(context);
        id = helper.primaryKey(ListDesciption.listName);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listdesciptionlayout, parent, false);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);
        Log.d("checkbox", String.valueOf(model.get(position).getBought()));
        if (model.get(position).getBought() == 1) {
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkBox.isChecked()) {
                    helper.updatedCheckBox(model.get(position).getItem(),
                            helper.primaryKey(ListDesciption.listName));
                    Log.d("ischeckedRunning", "running");
                    helper.updateItembought(id);

                } else {
                    helper.updateItembought(model.get(position).getItem());
                    Log.d("elseRunning", "modeTesting");

                    helper.updateItembought(id);
                    helper.updateTotalItem(id);
                    helper.close();
                }

            }
        });
        TextView textViewTitle = view.findViewById(R.id.textViewItemName);
        TextView quantity = view.findViewById(R.id.quantity);
        textViewTitle.setText("" + model.get(position).getItem());
        quantity.setText("" + model.get(position).getCount());
        ImageView deleteBtn = view.findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog.Builder builder = new MaterialDialog.Builder(context).message("Are you sure to Delete...?").
                        positiveText("Delete").negativeText("Cancel").buttonCallback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        helper.deleteITem(model.get(position).getItem());
                        model.remove(position);
                        helper.updateTotalItem(id);
                        Log.d("boughtItem", String.valueOf(helper.countBoughtItem(id)));
                        helper.countBoughtItem(id);
                        helper.updateItembought(id);
                        notifyDataSetChanged();


                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
