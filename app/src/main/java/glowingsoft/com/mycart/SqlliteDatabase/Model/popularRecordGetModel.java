package glowingsoft.com.mycart.SqlliteDatabase.Model;

/**
 * Created by Asif on 12/3/2018.
 */

public class popularRecordGetModel {
    String item;
    int count;
    int bought;

    public popularRecordGetModel(String item, int count, int bought) {
        this.item = item;
        this.count = count;
        this.bought = bought;
    }

    public popularRecordGetModel(String item, int count) {
        this.item = item;
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public int getBought() {
        return bought;
    }
}
