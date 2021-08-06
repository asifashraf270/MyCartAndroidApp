package glowingsoft.com.mycart.SqlliteDatabase;

import glowingsoft.com.mycart.SqlliteDatabase.Model.newItemTable;

/**
 * Created by Asif on 11/29/2018.
 */

public class DataBaseCreate {
    public static String createItemTable = "CREATE TABLE " + itemTableSchema.TableName + "(" +
            itemTableSchema.ColumnsId + " INTEGER PRIMARY KEY ," + itemTableSchema.itemName + " TEXT," + itemTableSchema.Status + " INTEGER ," + itemTableSchema.TotalItem + " INTEGER ," + itemTableSchema.boughtItem + " INTEGER );";
    public static String createSubItemTable = "CREATE TABLE " + subItemTableInfo.TableName + "(" +
            subItemTableInfo.ID + " INTEGER PRIMARY KEY ," + subItemTableInfo.DataField + " TEXT," + subItemTableInfo.foeignKey + " INTEGER ," + subItemTableInfo.quantity + " INTEGER ," + subItemTableInfo.bought + " INTEGER );";
    public static String DeleteItemTable = " DROP TABLE IF EXISTS " + itemTableSchema.TableName;
    public static String DeleteSubItem = " DROP TABLE IF EXISTS " + subItemTableInfo.TableName;
    public static String createTableNewItem = "CREATE TABLE " + newItemTable.TableName + "(" +
            newItemTable.columnPrId + " INTEGER PRIMARY KEY ," + newItemTable.itemName + " TEXT);";
    public static String deteteNewItemTable = " DROP TABLE IF EXISTS " + newItemTable.TableName;

}
