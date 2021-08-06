package glowingsoft.com.mycart.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glowingsoft.com.mycart.Adapters.subItemPagerAdapter;
import glowingsoft.com.mycart.R;


public class GeneralFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    subItemPagerAdapter adapter;
    String listName="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        tabLayout = view.findViewById(R.id.TabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        listName=getArguments().getString("listName");
        adapter = new subItemPagerAdapter(getChildFragmentManager(), getContext(),listName);
        viewPager.setAdapter(adapter);
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#000000"));

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


}
