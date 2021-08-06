package glowingsoft.com.mycart.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import glowingsoft.com.mycart.Fragment.SignIn;
import glowingsoft.com.mycart.Fragment.signUp;

/**
 * Created by Asif on 11/28/2018.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    Fragment fragment;
    Context context;


    public FragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new signUp();
                break;
            case 1:
                fragment = new SignIn();
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
        super.getPageTitle(position);

        switch (position) {
            case 0:
                return "Sign Up";
            case 1:
                return "Sign In";


        }
        return null;
    }
}
