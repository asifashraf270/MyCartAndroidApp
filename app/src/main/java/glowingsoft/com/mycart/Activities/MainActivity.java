package glowingsoft.com.mycart.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.pepperonas.materialdialog.MaterialDialog;

import java.util.List;

import glowingsoft.com.mycart.Adapters.ListViewItemAdapter;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.DataBaseCreate;
import glowingsoft.com.mycart.SqlliteDatabase.Model.getItemModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    FloatingActionButton floatingActionButton;
    AlertDialog alertDialogBuilder;
    LayoutInflater layoutInflater;
    ListView listView;
    sqlLiteOpenHelper database;
    ListViewItemAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    String sharedPreferencesFile = "File";
    LinearLayout parentLayoutId;
    AdView adView;
    AdRequest adRequest;
//    private InterstitialAd mInterstitialAd;


    private static final String tag = "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, getResources().getString(R.string.appId));
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
//        MobileAds.initialize(this,
//                "ca-app-pub-3940256099942544~3347511713");
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_Interstitial));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        parentLayoutId = findViewById(R.id.parentLayout);
        sharedPreferences = this.getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<getItemModel> listModel;

                listView = findViewById(R.id.listView);

                listModel = database.getItemData();
                try {
                    if (listModel.size() != 0) {
                        parentLayoutId.setBackground(null);
                        adapter = new ListViewItemAdapter(MainActivity.this, listModel);
                        listView.setAdapter(adapter);

                    } else {
                        Log.d(tag, "Nothing");
                        parentLayoutId.setBackgroundResource(R.drawable.norecord);
                    }
                } catch (Exception e) {
                    Log.d(tag, "exception");
                    parentLayoutId.setBackgroundResource(R.drawable.norecord);
                    swipeRefreshLayout.setRefreshing(false);

                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        database = new sqlLiteOpenHelper(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setHomeAsUpIndicator(R.drawable.menu);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            MaterialDialog.Builder dialog = new MaterialDialog.Builder(MainActivity.this);
            dialog.message("Are you Sure to Close Application");
            dialog.positiveText("Ok");
            dialog.negativeText("Cancel");
            dialog.buttonCallback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);

                    finish();
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.trash:
                startActivity(new Intent(MainActivity.this, Trash.class));

                break;

            case R.id.feedBack:
                String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.share:
                Intent abc = new Intent(Intent.ACTION_SEND);
                abc.setType("text/plain");
                abc.putExtra(Intent.EXTRA_SUBJECT, "My Cart");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName();
                abc.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(abc, "choose one"));
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
//                mInterstitialAd.show();
                customDialog();
                break;
        }
    }

    private String getListName(TextInputLayout data) {
        String listName = data.getEditText().getText().toString();
        return listName;
    }

    private void customDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this).create();
        layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.fabdialog, null);
        alertDialogBuilder.setView(view);
        final TextInputLayout listName = view.findViewById(R.id.createEditText);
        AppCompatTextView createBtn = view.findViewById(R.id.createTextView);
        alertDialogBuilder.show();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = getListName(listName);
                if (value.length() == 0) {
                    listName.setError("Field is required");
                } else {
                    Log.d(tag, value);
                    Log.d(tag, DataBaseCreate.createItemTable + "/" + DataBaseCreate.createSubItemTable);
                    if (database.insertItem(value) != -1) {
                        Intent intent = new Intent(MainActivity.this, ListDesciption.class);
                        intent.putExtra("value", value);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    alertDialogBuilder.dismiss();
                }


            }
        });


//    }
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<getItemModel> listModel;

        listView = findViewById(R.id.listView);

        listModel = database.getItemData();
        try {
            if (listModel.size() != 0) {
                parentLayoutId.setBackground(null);
                adapter = new ListViewItemAdapter(this, listModel);
                listView.setAdapter(adapter);

            } else {
                Log.d(tag, "Nothing");
                parentLayoutId.setBackgroundResource(R.drawable.norecord);
            }
        } catch (Exception e) {
            Log.d(tag, "exception");
            parentLayoutId.setBackgroundResource(R.drawable.norecord);

        }
    }
}