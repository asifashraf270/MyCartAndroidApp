package glowingsoft.com.mycart.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import glowingsoft.com.mycart.Adapters.ListDescriptionAdapter;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

public class ListDesciption extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView textView;
    FloatingActionButton floatingActionButton;
    Intent intent;
    public static String listName;
    ListView listView;
    List<popularRecordGetModel> model;
    sqlLiteOpenHelper helper;
    SwipeRefreshLayout refreshLayout;
    ListDescriptionAdapter adapter;
    RelativeLayout parentLayout;
    static public String sharedPreferencesFile = "MyCart";
    static public SharedPreferences.Editor editor;
    static public SharedPreferences sharedPreferences;
    AdView adView;
    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_desciption);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.listName);
        listView = findViewById(R.id.listView);
        toolbar.setTitle("");
        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, getResources().getString(R.string.appId));
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
//        MobileAds.initialize(this,
//                getResources().getString(R.string.appId));

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//        adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
        parentLayout = findViewById(R.id.parentLayoutId);
        refreshLayout = findViewById(R.id.swipeRefresh);
        ListDesciption.sharedPreferences = getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE);
        ListDesciption.editor = sharedPreferences.edit();


        ListDesciption.listName = getIntent().getExtras().getString("value");
        textView.setText("" + StringUtils.capitalize(listName));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        Log.d("sizResule", String.valueOf(getSubRecord().size()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model = getSubRecord();
                model = helper.OrderBy();
                try {
                    if (model.size() != 0) {

                        adapter = new ListDescriptionAdapter(model, ListDesciption.this);
                        parentLayout.setBackground(null);

                        listView.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                    } else {
                        refreshLayout.setRefreshing(false);
                        parentLayout.setBackgroundResource(R.drawable.norecord);

                    }
                } catch (Exception e) {
                    parentLayout.setBackgroundResource(R.drawable.norecord);

                }
            }
        });
        refreshLayout.setRefreshing(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:


                intent = new Intent(ListDesciption.this, SubItemActivity.class);
                intent.putExtra("value", listName);
                try {
                    ListDesciption.editor.putInt("Key", listView.getAdapter().getCount());
                    ListDesciption.editor.commit();
                } catch (Exception e) {
                    ListDesciption.editor.putInt("Key", 0);
                    ListDesciption.editor.commit();
                }
                startActivity(intent);
//                mInterstitialAd.show();
                break;
        }
    }

    List<popularRecordGetModel> getSubRecord() {
        helper = new sqlLiteOpenHelper(this);

        int id = helper.primaryKey(listName);
        model = helper.getSubitem(id);


        return model;
    }

    @Override
    protected void onResume() {
        super.onResume();
        model = getSubRecord();
//
        try {
            if (model.size() != 0) {
                adapter = new ListDescriptionAdapter(model, this);
                parentLayout.setBackground(null);
                listView.setAdapter(adapter);
            }
        } catch (Exception e) {
            parentLayout.setBackgroundResource(R.drawable.norecord);
        }

    }
}
