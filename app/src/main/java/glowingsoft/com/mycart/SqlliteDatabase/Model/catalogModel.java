package glowingsoft.com.mycart.SqlliteDatabase.Model;

/**
 * Created by Asif on 12/12/2018.
 */

public class catalogModel {
    String itemValue;
    int imageResource;

    public catalogModel(String itemValue, int imageResource) {
        this.itemValue = itemValue;
        this.imageResource = imageResource;
    }

    public String getItemValue() {
        return itemValue;
    }

    public int getImageResource() {
        return imageResource;
    }
}
