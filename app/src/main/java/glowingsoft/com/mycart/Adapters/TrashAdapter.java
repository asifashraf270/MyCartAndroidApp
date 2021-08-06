package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pepperonas.materialdialog.MaterialDialog;

import java.util.List;

import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.getItemModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

/**
 * Created by Asif on 12/10/2018.
 */

public class TrashAdapter extends BaseAdapter {
    List<getItemModel> model;
    Context context;
    sqlLiteOpenHelper helper;
    LayoutInflater layoutInflater;

    public TrashAdapter(List<getItemModel> model, Context context) {
        this.model = model;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper = new sqlLiteOpenHelper(context);
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
        View view = layoutInflater.inflate(R.layout.trash_item_design, parent, false);
        TextView listName = view.findViewById(R.id.ListNameTrash);
        Button btnDelete = view.findViewById(R.id.delete);
        Button btnRestore = view.findViewById(R.id.restore);
        listName.setText("" + model.get(position).getItemName());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
                builder.message("Are you Sure to Delete this...");
                builder.positiveText("Delete").negativeText("Cancel");
                builder.buttonCallback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        helper.deleteItemFromTrash(model.get(position).getId());
                        helper.DeleteSubItemRecord(model.get(position).getId());
                        model.remove(position);
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
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
                builder.message("Are you sure to Restore this List ");
                builder.positiveText("Restore").negativeText("Cancel");
                builder.buttonCallback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        helper.RestoreTRashItem(model.get(position).getId());
                        model.remove(position);
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
