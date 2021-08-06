package glowingsoft.com.mycart.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.common.StringUtil;
import com.pepperonas.materialdialog.MaterialDialog;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import glowingsoft.com.mycart.Activities.ListDesciption;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.getItemModel;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Asif on 11/29/2018.
 */

public class ListViewItemAdapter extends BaseAdapter {
    Context context;
    PopupMenu popupMenu;
    List<getItemModel> model;
    LayoutInflater layoutInflater;
    LinearLayout dottedMenu;
    private int selectedPosition = 0;
    String updateValue = "";
    sqlLiteOpenHelper helper;
    List<popularRecordGetModel> subModel;


    public ListViewItemAdapter(Context context, List<getItemModel> model) {
        this.context = context;
        this.model = model;
        layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.listviewitem, parent, false);
        TextView textView = view.findViewById(R.id.listName);
        ProgressBar progressBar = view.findViewById(R.id.progressBarHor);
        dottedMenu = view.findViewById(R.id.dottedMenu);
        dottedMenu.setTag(dottedMenu);

        dottedMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;

                popupMenu = new PopupMenu(context, v);

                popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.rename:
                                final Dialog dialog = new Dialog(context);
                                View view1 = layoutInflater.inflate(R.layout.pop_up_dialog_with_input_field, parent, false);
                                dialog.setContentView(view1);
                                final TextInputLayout updateEdittext = view1.findViewById(R.id.updateListName);
                                updateEdittext.getEditText().setText("" + model.get(position).getItemName());
                                updateEdittext.getEditText().setSelection(model.get(position).getItemName().length());
                                Button btnUpdate = view1.findViewById(R.id.update);
                                btnUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateValue = updateEdittext.getEditText().getText().toString();
                                        helper.updateListName(updateValue, model.get(position).getId());
                                        getItemModel itemModel = new getItemModel(model.get(position).getId(), updateValue, model.get(position).getStatus(), model.get(position).getTotalItem(), model.get(position).getBoughtItem());
                                        model.set(position, itemModel);
                                        notifyDataSetChanged();

                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                return true;


                            case R.id.delte:
                                MaterialDialog.Builder dialog1 = new MaterialDialog.Builder(context).message("Are you Sure to Delete this item...?");
                                dialog1.positiveText("Delete").negativeText("Cancel");
                                dialog1.buttonCallback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        helper.DeleteListItem(model.get(position).getId());
                                        model.remove(position);
                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        super.onNegative(dialog);

                                    }
                                });
                                dialog1.show();


                                break;
                            case R.id.share:


                                createPdf(model.get(position).getItemName(), getSubRecord(model.get(position).getItemName()));
                                break;

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListDesciption.class);
                Log.d("Adapter", model.get(position).getItemName());

                intent.putExtra("value", model.get(position).getItemName());

                context.startActivity(intent);
            }
        });
        TextView status = view.findViewById(R.id.numberOfItem);
        String value=model.get(position).getItemName();
        textView.setText(""+StringUtils.capitalize(value));
        float progress;
        float bought;
        float total;
        status.setText(model.get(position).getBoughtItem() + "/" + model.get(position).getTotalItem());
        try {
            bought = model.get(position).getBoughtItem();
            total = model.get(position).getTotalItem();
            progress = (float) bought / total * 100;
            Log.d("record", String.valueOf(progress));
            progressBar.setProgress((int) progress);

        } catch (Exception i) {
            Log.d("MainActivity", "Nothing");
            progressBar.setProgress(0);
        }
        return view;
    }

    private void createPdf(String value, List<popularRecordGetModel> getModel) {
        List<popularRecordGetModel> model = getModel;
        float x, y;
        x = 10;
        y = 90;
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(true);
        canvas.drawText(StringUtils.capitalize(value), 125, 30, paint);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
         Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        canvas.drawText(dateToStr,30,50,paint);
        canvas.drawText("Item", 20, 70, paint);
        canvas.drawText("Quantity", 130, 70, paint);
        canvas.drawText("Status",210,70,paint);
        paint.setFakeBoldText(false);
        for (int i = 0; i < model.size(); i++) {
            canvas.drawText(String.valueOf(i + 1 + " : " + model.get(i).getItem()), x, y, paint);
            canvas.drawText(String.valueOf("" + model.get(i).getCount()), x + 120, y, paint);
            int status= model.get(i).getBought();
            if(status==0) {
                canvas.drawText("Pending",x+210,y,paint);
            }
            else
            {
                canvas.drawText("Purchase",x+210,y,paint);
            }
            y = y + 30;

        }
        document.finishPage(page);
        String directoryPath = Environment.getExternalStorageDirectory().getPath() + "/myPdf/";
        File file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdirs();

        }
        String targetPdf = directoryPath + value + ".pdf";
        Log.d("targetPath", targetPdf);
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (Exception e) {
        }
        document.close();
        Intent share = new Intent();
        Uri uri = Uri.fromFile(filePath);
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);

        context.startActivity(Intent.createChooser(share, "choose one"));


    }

    List<popularRecordGetModel> getSubRecord(String ListName) {
        helper = new sqlLiteOpenHelper(context);
        subModel = new ArrayList<>();

        int id = helper.primaryKey(ListName);
        subModel = helper.getSubitem(id);


        return subModel;
    }
}
