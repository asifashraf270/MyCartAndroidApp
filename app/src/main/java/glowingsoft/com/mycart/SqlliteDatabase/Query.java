package glowingsoft.com.mycart.SqlliteDatabase;

import glowingsoft.com.mycart.SqlliteDatabase.Model.newItemTable;

/**
 * Created by Asif on 11/29/2018.
 */

public class Query {
    public static String getNewItem=" SELECT * From " + newItemTable.TableName+ " ORDER BY "+newItemTable.columnPrId+ " DESC ";
    public static String getItemData = " SELECT * From " + itemTableSchema.TableName + " WHERE " + itemTableSchema.Status + " = " + 0;
    public static String getPrimaryId;

    public static String getPrimaryKey(String name) {

        return getPrimaryId = " Select " + itemTableSchema.ColumnsId + " From " + itemTableSchema.TableName + " WHERE " + itemTableSchema.itemName + " = " + "'" + name + "'";

    }

    public static String getSubitem(int id) {
        return "Select * From " + subItemTableInfo.TableName + " WHERE " + subItemTableInfo.foeignKey + " = " + id;

    }

    public static String getAllSubItemRecord(int id) {
        return "Select * From " + subItemTableInfo.TableName + " Where " + subItemTableInfo.foeignKey + " = " + id;
    }

    public static String updateBought(String value) {
        return "Update " + subItemTableInfo.TableName + " SET " + subItemTableInfo.bought + " = " + 1 + " Where " + subItemTableInfo.DataField + " = " + "'" + value + "'";

    }

    public static String updateTotalItem(long total, int primaryId) {
        return "Update " + itemTableSchema.TableName + " SET " + itemTableSchema.TotalItem + " = " + total + " Where " + itemTableSchema.ColumnsId + " = " + primaryId;
    }

    public static String updateBoughtITem(long values, int primaryId) {
        return "Update " + itemTableSchema.TableName + " SET " + itemTableSchema.boughtItem + " = " + values + " Where " + itemTableSchema.ColumnsId + " = " + primaryId;
    }

    public static String updateSubItemName(String value, int primaryId, int count) {
        return "Update " + subItemTableInfo.TableName + " SET " + subItemTableInfo.quantity + " = " + count + " Where " + subItemTableInfo.foeignKey + " = " + primaryId + " And " + subItemTableInfo.DataField + " = " + "'" + value + "'";
    }

    public static String deletesubitemRow(String value) {
        return " DELETE FROM " + subItemTableInfo.TableName + " WHERE " + subItemTableInfo.DataField + " = " + "'" + value + "'";
    }

    public static String UpdateListName(String value, int Id) {
        return "Update " + itemTableSchema.TableName + " SET " + itemTableSchema.itemName + " = " + "'" + value + "'" + " Where " + itemTableSchema.ColumnsId + " = " + Id;

    }

    public static String DeleteListItem(int Id) {
        return "Update " + itemTableSchema.TableName + " SET " + itemTableSchema.Status + " = " + 1 + " Where " + itemTableSchema.ColumnsId + " = " + Id;
    }

    public static String TrashItemRestore() {
        return "Select * From " + itemTableSchema.TableName + " Where " + itemTableSchema.Status + " = " + 1;
    }

    public static String DeleteItemFromTrash(int Id) {
        return "Delete From " + itemTableSchema.TableName + " Where " + itemTableSchema.ColumnsId + " = " + Id;
    }

    public static String RestoreTrashItem(int Id) {
        return "Update " + itemTableSchema.TableName + " SET " + itemTableSchema.Status + " = " + 0 + " Where " + itemTableSchema.ColumnsId + " = " + Id;
    }

    public static String DeleteSubITemFromItem(int Id) {
        return "Delete From " + subItemTableInfo.TableName + " Where " + subItemTableInfo.foeignKey + " = " + Id;
    }

    public static String OrderBy() {
        return "Select * From " + subItemTableInfo.TableName + " " + " ORDER BY " + subItemTableInfo.bought + " DESC ";
    }

    public static String UpdateUnSelectedCheckBox(String value, int foreignKey) {
        return "Update " + subItemTableInfo.TableName + " SET " + subItemTableInfo.bought + " = " + 0 + " Where " + subItemTableInfo.DataField + " = " + "'" + value + "'" + " And " + subItemTableInfo.foeignKey + " = " + foreignKey;

    }

}
