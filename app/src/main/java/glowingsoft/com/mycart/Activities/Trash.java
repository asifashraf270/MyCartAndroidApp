package glowingsoft.com.mycart.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Adapters.TrashAdapter;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.getItemModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

public class Trash extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    List<getItemModel> model;
    sqlLiteOpenHelper Helper;
    LinearLayout parentLayout;
    AdView adView;
    AdRequest adRequest;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        toolbar = findViewById(R.id.ToolBar);
        listView = findViewById(R.id.ListView);
        setSupportActionBar(toolbar);
        adView = findViewById(R.id.adView);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model = new ArrayList<>();
                Helper = new sqlLiteOpenHelper(Trash.this);
                model = Helper.getTrashitem();
                try {
                    if (model.size() != 0) {
                        TrashAdapter adapter = new TrashAdapter(model, Trash.this);
                        parentLayout.setBackground(null);
                        listView.setAdapter(adapter);
                    } else {
                        parentLayout.setBackgroundResource(R.drawable.norecord);

                    }
                } catch (Exception e) {
                    parentLayout.setBackgroundResource(R.drawable.norecord);

                }
                refreshLayout.setRefreshing(false);
            }
        });
        MobileAds.initialize(this, getResources().getString(R.string.appId));
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        toolbar.setTitleTextColor(Color.WHITE);
        parentLayout = findViewById(R.id.parentLayoutId);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        model = new ArrayList<>();
        Helper = new sqlLiteOpenHelper(this);
        model = Helper.getTrashitem();
        try {
            if (model.size() != 0) {
                TrashAdapter adapter = new TrashAdapter(model, this);
                parentLayout.setBackground(null);
                listView.setAdapter(adapter);
            }
        } catch (Exception e) {
            parentLayout.setBackgroundResource(R.drawable.norecord);
        }
    }
}
