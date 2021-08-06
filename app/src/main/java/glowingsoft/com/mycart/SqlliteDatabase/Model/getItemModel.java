package glowingsoft.com.mycart.SqlliteDatabase.Model;

/**
 * Created by Asif on 11/29/2018.
 */

public class getItemModel {
    int id;
    String itemName;
    int status;
    int totalItem;
    int boughtItem;

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getStatus() {
        return status;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public int getBoughtItem() {
        return boughtItem;
    }

    public getItemModel(int id, String itemName, int status, int totalItem, int boughtItem) {
        this.id = id;
        this.itemName = itemName;
        this.status = status;
        this.totalItem = totalItem;
        this.boughtItem = boughtItem;
    }



}
