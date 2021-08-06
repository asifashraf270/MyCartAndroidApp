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

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Adapters.popularListView;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;


public class popular extends Fragment {
    ListView listView;
    popularListView adapter;
    String[] itemValue;
    List<String> itemValueList = new ArrayList<>();
    SwipeRefreshLayout layout;
    sqlLiteOpenHelper openHelper;
    List<popularRecordGetModel> modelList;
    String ListName = "";
    SearchView searchView;
    Button btnAddNewItem;
    List<String> newItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_popular, container, false);
        itemValue = getResources().getStringArray(R.array.popularList);
        btnAddNewItem = view.findViewById(R.id.addNewItem);
        ListName = getArguments().getString("listName");
        Log.d("ListName", ListName);
        openHelper = new sqlLiteOpenHelper(getContext());

        modelList = openHelper.getSubitem(openHelper.primaryKey(ListName));
        adapter = new popularListView(itemValueList, getContext(), modelList, ListName);

        newItemList = openHelper.getNewItem();
        if (newItemList.size() > 0) {
            for (int i = 0; i < newItemList.size(); i++) {
                itemValueList.add(newItemList.get(i));
            }
        }
        for (int i = 0; i < itemValue.length; i++) {
            itemValueList.add(itemValue[i]);
        }
        listView = view.findViewById(R.id.ListView);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                List<String> filterList = new ArrayList<>();
                for (String checkValue : itemValueList) {
                    if (checkValue.toLowerCase().contains(query.toLowerCase())) {
                        filterList.add(checkValue);
                    }
                }
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
                            for (int i = 0; i < itemValue.length; i++) {
                                itemValueList.add(itemValue[i]);
                            }
                            adapter = new popularListView(itemValueList, getContext(), modelList, ListName);
                            listView.setAdapter(adapter);

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
                for (String checkValue : itemValueList) {
                    if (checkValue.toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(checkValue);
                    }
                }
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
                            for (int i = 0; i < itemValue.length; i++) {
                                itemValueList.add(itemValue[i]);
                            }
                            adapter = new popularListView(itemValueList, getContext(), modelList, ListName);
                            listView.setAdapter(adapter);
                        }
                    });
                    ((popularListView) listView.getAdapter()).update(filterList);
                } else {
                    btnAddNewItem.setVisibility(View.GONE);
                    ((popularListView) listView.getAdapter()).update(filterList);


                }

                return false;
            }
        });
        layout = view.findViewById(R.id.refreshLayout);
        ListName = getArguments().getString("listName");

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                listView.setAdapter(adapter);
                layout.setRefreshing(false);
            }
        });
        int foreignKey = openHelper.primaryKey(ListName);
        modelList = openHelper.getAllSubItemRecord(foreignKey);
        Log.d("modelList", String.valueOf(modelList.size()));
        adapter = new popularListView(itemValueList, getContext(), modelList, ListName);


        listView.setAdapter(adapter);

        return view;
    }


}
