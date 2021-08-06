
package glowingsoft.com.mycart.SqlliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.SqlliteDatabase.Model.getItemModel;
import glowingsoft.com.mycart.SqlliteDatabase.Model.newItemTable;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;

/**
 * Created by Asif on 11/29/2018.
 */

public class sqlLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DatabaseName = "mycart.db";
    public static final int DatabaseVersion = 28;
    SQLiteDatabase sqLiteDatabase;
    Context context;
    popularRecordGetModel recordModel;

    public sqlLiteOpenHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseCreate.createItemTable);
        db.execSQL(DataBaseCreate.createSubItemTable);
        db.execSQL(DataBaseCreate.createTableNewItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseCreate.DeleteItemTable);
        db.execSQL(DataBaseCreate.DeleteSubItem);
        db.execSQL(DataBaseCreate.deteteNewItemTable);
        onCreate(db);
    }

    public long insertItem(String value) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(itemTableSchema.itemName, value);
        contentValues.put(itemTableSchema.Status, 0);
        contentValues.put(itemTableSchema.boughtItem, 0);
        contentValues.put(itemTableSchema.TotalItem, 0);
        long result = sqLiteDatabase.insert(itemTableSchema.TableName, null, contentValues);
        return result;
    }

    public List<String> getNewItem() {
        List<String> valuesModel = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.getNewItem, null);
        if (cursor.moveToFirst()) {
            do {
                valuesModel.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return valuesModel;
    }

    public List<getItemModel> getItemData() {
        getItemModel model;
        List<getItemModel> listItem = new ArrayList<>();

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.getItemData, null);
        if (cursor.moveToFirst()) {
            do {
                model = new getItemModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                listItem.add(model);
            } while (cursor.moveToNext());
            Log.d("MainActvity", listItem.toString());
            cursor.close();
            sqLiteDatabase.close();
            return listItem;
        } else {
            sqLiteDatabase.close();
            return null;
        }


    }

    public int primaryKey(String name) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.getPrimaryKey(name), null);
        Log.d("Query", Query.getPrimaryKey(name));
        if (cursor != null) {
            if (cursor.moveToFirst()) {


                return cursor.getInt(0);
            }
        }
        return 0;
    }

    public long insertSubitemData(popularRecordGetModel modelData, int id) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(subItemTableInfo.DataField, modelData.getItem());
        values.put(subItemTableInfo.quantity, modelData.getCount());
        values.put(subItemTableInfo.foeignKey, id);

        values.put(subItemTableInfo.bought, 0);
        long result = sqLiteDatabase.insert(subItemTableInfo.TableName, null, values);
        return result;
    }

    public long insertNewItem(String value) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(newItemTable.itemName, value);
        long result = sqLiteDatabase.insert(newItemTable.TableName, null, values);
        return result;
    }

    public List<popularRecordGetModel> getSubitem(int id) {
        List<popularRecordGetModel> models = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.getSubitem(id), null);
        if (cursor.moveToFirst()) {
            do {
                recordModel = new popularRecordGetModel(cursor.getString(1), cursor.getInt(3), cursor.getInt(4));

                models.add(recordModel);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return models;
    }

    public void updateItembought(String value) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.updateBought(value));
        sqLiteDatabase.close();

    }

    public void updateTotalItem(int primaryId) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.updateTotalItem(countTotalItem(primaryId), primaryId));
        sqLiteDatabase.close();
    }

    public void updateItembought(int primaryId) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.updateBoughtITem(countBoughtItem(primaryId), primaryId));
        sqLiteDatabase.close();
    }

    public long countTotalItem(int id) {
        String countQuery = "SELECT  * FROM " + subItemTableInfo.TableName + " WHERE " + subItemTableInfo.foeignKey + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;


    }

    public long countBoughtItem(int Id) {
        String countQuery = " SELECT * FROM " + subItemTableInfo.TableName + " WHERE " + subItemTableInfo.bought + " = " + 1 + " AND " + subItemTableInfo.foeignKey + " = " + Id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteITem(String value) {
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL(Query.deletesubitemRow(value));

    }

    public void updateListName(String value, int id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.UpdateListName(value, id));
        sqLiteDatabase.close();
    }

    public void DeleteListItem(int id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.DeleteListItem(id));
        sqLiteDatabase.close();
    }

    public List<getItemModel> getTrashitem() {
        List<getItemModel> schema = new ArrayList<>();

        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.TrashItemRestore(), null);
        if (cursor.moveToFirst()) {
            do {
                schema.add(new getItemModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)));

            } while (cursor.moveToNext());
        }
        return schema;

    }

    public void deleteItemFromTrash(int Id) {
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL(Query.DeleteItemFromTrash(Id));
    }

    public void RestoreTRashItem(int Id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.RestoreTrashItem(Id));
    }

    public void DeleteSubItemRecord(int Id) {
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL(Query.DeleteSubITemFromItem(Id));
    }

    public List<popularRecordGetModel> getAllSubItemRecord(int id) {
        List<popularRecordGetModel> models = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.getAllSubItemRecord(id), null);
        if (cursor.moveToFirst()) {
            do {
                recordModel = new popularRecordGetModel(cursor.getString(1), cursor.getInt(3), cursor.getInt(4));

                models.add(recordModel);

            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return models;
    }

    public void updateSubItem(String value, int primaryKey, int count) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.updateSubItemName(value, primaryKey, count));
    }

    public List<popularRecordGetModel> OrderBy() {
        sqLiteDatabase = this.getReadableDatabase();
        List<popularRecordGetModel> model = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(Query.OrderBy(), null);
        if (cursor.moveToFirst()) {
            do {
                model.add(new popularRecordGetModel(cursor.getString(1), cursor.getInt(3), cursor.getInt(4)));


            } while (cursor.moveToNext());

        }
        return model;

    }

    public void updatedCheckBox(String value, int key) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Query.UpdateUnSelectedCheckBox(value, key));
    }


}
