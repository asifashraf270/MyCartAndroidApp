package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import glowingsoft.com.mycart.Fragment.catalog;
import glowingsoft.com.mycart.Fragment.popular;

/**
 * Created by Asif on 11/30/2018.
 */

public class subItemPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    Fragment fragment;
    Context context;
    String Listname;

    public subItemPagerAdapter(FragmentManager fm, Context context, String listName) {
        super(fm);
        this.Listname = listName;

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("listName", Listname);

                fragment = new popular();
                fragment.setArguments(bundle);
                break;
            case 1:
                Bundle bundleCatalo = new Bundle();
                bundleCatalo.putString("listName", Listname);
                fragment = new catalog();
                fragment.setArguments(bundleCatalo);

                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "POPULAR";
            case 1:
                return "ITEM CATALOG";
        }
        return super.getPageTitle(position);
    }
}
