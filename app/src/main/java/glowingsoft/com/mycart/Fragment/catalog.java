package glowingsoft.com.mycart.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Adapters.SubITemCatalogAdapter;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.catalogModel;


public class catalog extends Fragment {
    ListView listView;
    String[] itemName;
    List<catalogModel> itemNameList = new ArrayList<>();
    int[] imageResources;
    SubITemCatalogAdapter adapter;
    static String key = "value";
    static String value;
    SwipeRefreshLayout layout;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        listView = view.findViewById(R.id.listViewItem);
        searchView = view.findViewById(R.id.SearchViewcatalog);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        itemName = getResources().getStringArray(R.array.item_Catalog);
        imageResources = new int[]{R.drawable.glass, R.drawable.baby, R.drawable.cupcake, R.drawable.drop,
                R.drawable.canned, R.drawable.car, R.drawable.hanger, R.drawable.coffee, R.drawable.cosmetics,
                R.drawable.dairy, R.drawable.leaf, R.drawable.television, R.drawable.fish, R.drawable.icecream,
                R.drawable.cereal, R.drawable.house, R.drawable.cake, R.drawable.house, R.drawable.meat, R.drawable.newspaper};

        for (int i = 0; i < itemName.length; i++) {
            itemNameList.add(new catalogModel(itemName[i], imageResources[i]));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<catalogModel> filterList = new ArrayList<>();
                for (catalogModel laterValue : itemNameList) {
                    if (laterValue.getItemValue().toLowerCase().contains(query.toLowerCase())) {
                        filterList.add(laterValue);
                    }
                }
                ((SubITemCatalogAdapter) listView.getAdapter()).update(filterList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<catalogModel> filterList = new ArrayList<>();
                for (catalogModel laterValue : itemNameList) {
                    if (laterValue.getItemValue().toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(laterValue);
                    }
                }
                ((SubITemCatalogAdapter) listView.getAdapter()).update(filterList);

                return false;
            }
        });
        layout = view.findViewById(R.id.refreshLayout);
        searchView = view.findViewById(R.id.searchView);
        adapter = new SubITemCatalogAdapter(itemNameList, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle d = new Bundle();
                value = itemName[position];
                Fragment fragment = new GeneralLoaderFragment();
                d.putString(catalog.key, value);
                d.putString("listName",getArguments().getString("listName"));
                fragment.setArguments(d);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
            }
        });
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new SubITemCatalogAdapter(itemNameList, getContext());
                listView.setAdapter(adapter);
                layout.setRefreshing(false);
            }
        });


        return view;
    }

}
