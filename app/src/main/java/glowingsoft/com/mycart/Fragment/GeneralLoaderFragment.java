package glowingsoft.com.mycart.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Adapters.popularListView;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;


public class GeneralLoaderFragment extends Fragment {
    TextView textView;
    ListView listView;
    String[] item;
    String value;
    String[] itemRecord;
    List<String> itemValueList = new ArrayList<>();
    List<String> newItemList = new ArrayList<>();

    popularListView listViewAdapter;
    SwipeRefreshLayout layout;
    List<popularRecordGetModel> alreadySelectedOption;
    sqlLiteOpenHelper openHelper;
    SearchView searchView;
    Button btnAddNewItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle bundle = getArguments();
        // bundle.getString("value");

        View view = inflater.inflate(R.layout.fragment_general_loader, container, false);
        textView = view.findViewById(R.id.textViewTitle);
        listView = view.findViewById(R.id.listView);
        value = bundle.getString(catalog.key);
        textView.setText("" + value);
        btnAddNewItem = view.findViewById(R.id.addNewItem);

        layout = view.findViewById(R.id.refreshLayout);
        item = getResources().getStringArray(R.array.item_Catalog);

        if (value.equals(item[0])) {
            itemRecord = getResources().getStringArray(R.array.alcoholic_drink);
        } else if (value.equals(item[1])) {
            itemRecord = getResources().getStringArray(R.array.baby_products);
        } else if (value.equals(item[2])) {
            itemRecord = getResources().getStringArray(R.array.bakery);
        } else if (value.equals(item[3])) {
            itemRecord = getResources().getStringArray(R.array.beverages);
        } else if (value.equals(item[4])) {
            itemRecord = getResources().getStringArray(R.array.canned_food);
        } else if (value.equals(item[5])) {
            itemRecord = getResources().getStringArray(R.array.car_care_product);
        } else if (value.equals(item[6])) {
            itemRecord = getResources().getStringArray(R.array.Clothes);
        } else if (value.equals(item[7])) {
            itemRecord = getResources().getStringArray(R.array.coffea_tea_hotChocolate);
        } else if (value.equals(item[8])) {
            itemRecord = getResources().getStringArray(R.array.cosmetics);
        } else if (value.equals(item[9])) {
            itemRecord = getResources().getStringArray(R.array.dairy_products);
        } else if (value.equals(item[10])) {
            itemRecord = getResources().getStringArray(R.array.diet_foods);
        } else if (value.equals(item[11])) {
            itemRecord = getResources().getStringArray(R.array.electrical_products);
        } else if (value.equals(item[12])) {
            itemRecord = getResources().getStringArray(R.array.fish_Seafood);
        } else if (value.equals(item[13])) {
            itemRecord = getResources().getStringArray(R.array.frozen);
        } else if (value.equals(item[14])) {
            itemRecord = getResources().getStringArray(R.array.grainspasta);
        } else if (value.equals(item[15])) {
            itemRecord = getResources().getStringArray(R.array.homekitchen);
        } else if (value.equals(item[16])) {
            itemRecord = getResources().getStringArray(R.array.homebaking);
        } else if (value.equals(item[17])) {
            itemRecord = getResources().getStringArray(R.array.houseCleaningProducts);
        } else if (value.equals(item[18])) {
            itemRecord = getResources().getStringArray(R.array.meat_poutry);
        } else if (value.equals(item[19])) {
            itemRecord = getResources().getStringArray(R.array.newspaper);
        }

        openHelper = new sqlLiteOpenHelper(getContext());
        newItemList = new ArrayList<>();
        newItemList = openHelper.getNewItem();
        Log.d("databasesize", String.valueOf(newItemList.size()));
        if (newItemList.size() > 0) {
            for (int i = 0; i < newItemList.size(); i++) {
                itemValueList.add(newItemList.get(i));
            }
        }

        for (int i = 0; i < itemRecord.length; i++) {
            itemValueList.add(itemRecord[i]);
        }
        Log.d("itemValueList", String.valueOf(itemValueList.size()));
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                List<String> filterList = new ArrayList<>();
                for (String ListValue : itemValueList) {
                    if (ListValue.toLowerCase().contains(query.toLowerCase())) {
                        filterList.add(ListValue);
                    }
                }
                Log.d("filterSize", String.valueOf(filterList.size()));
                if (filterList.size() == 0) {
                    btnAddNewItem.setVisibility(View.VISIBLE);
                    btnAddNewItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String value = query;
                            long resultStatus = openHelper.insertNewItem(value);
                            searchView.setQuery("", true);
                            newItemList = new ArrayList<>();
                            itemValueList = new ArrayList<>();

                            newItemList = openHelper.getNewItem();
                            if (newItemList.size() > 0) {
                                for (int i = 0; i < newItemList.size(); i++) {
                                    itemValueList.add(newItemList.get(i));


                                }
                            }
                            for (int i = 0; i < itemRecord.length; i++) {
                                itemValueList.add(itemRecord[i]);
                            }
                            listViewAdapter = new popularListView(itemValueList, getContext(), alreadySelectedOption, bundle.getString("listName"));
                            listView.setAdapter(listViewAdapter);
                        }
                    });

                } else {
                    btnAddNewItem.setVisibility(View.GONE);
                    ((popularListView) listView.getAdapter()).update(filterList);

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                List<String> filterList = new ArrayList<>();
                for (String ListValue : itemValueList) {
                    if (ListValue.toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(ListValue);
                    }
                }
                Log.d("filterSize", String.valueOf(filterList.size()));


                if (filterList.size() == 0) {

                    btnAddNewItem.setVisibility(View.VISIBLE);
                    btnAddNewItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String value = newText;
                            long resultStatus = openHelper.insertNewItem(value);
                            searchView.setQuery("", true);
                            newItemList = new ArrayList<>();
                            itemValueList = new ArrayList<>();

                            newItemList = openHelper.getNewItem();
                            if (newItemList.size() > 0) {
                                for (int i = 0; i < newItemList.size(); i++) {
                                    itemValueList.add(newItemList.get(i));


                                }
                            }
                            for (int i = 0; i < itemRecord.length; i++) {
                                itemValueList.add(itemRecord[i]);
                            }
                            listViewAdapter = new popularListView(itemValueList, getContext(), alreadySelectedOption, bundle.getString("listName"));
                            listView.setAdapter(listViewAdapter);
                        }
                    });
                    ((popularListView) listView.getAdapter()).update(filterList);

                } else {
                    btnAddNewItem.setVisibility(View.GONE);
                    ((popularListView) listView.getAdapter()).update(filterList);

                }

//                ((popularListView) listView.getAdapter()).update(filterList);

                return false;
            }
        });
        alreadySelectedOption = new ArrayList<>();
        openHelper = new sqlLiteOpenHelper(getContext());
        alreadySelectedOption = openHelper.getSubitem(openHelper.primaryKey(bundle.getString("listName")));

        listViewAdapter = new popularListView(itemValueList, getContext(), alreadySelectedOption, bundle.getString("listName"));
        listView.setAdapter(listViewAdapter);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listViewAdapter = new popularListView(itemValueList, getContext(), alreadySelectedOption, bundle.getString("listName"));
                listView.setAdapter(listViewAdapter);
                layout.setRefreshing(false);
            }
        });

        return view;
    }


}
