package glowingsoft.com.mycart.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glowingsoft.com.mycart.Fragment.GeneralFragment;
import glowingsoft.com.mycart.R;
import glowingsoft.com.mycart.SqlliteDatabase.Model.popularRecordGetModel;
import glowingsoft.com.mycart.SqlliteDatabase.sqlLiteOpenHelper;
import ru.nikartm.support.ImageBadgeView;

public class SubItemActivity extends AppCompatActivity {
    Toolbar toolbar;
    String listName;
    sqlLiteOpenHelper helper;
    ImageBadgeView imageBadgeView;
    int TotalItem = 0;
    TextView toolbartitle;
    Handler handler;
    Runnable runnable;
    int delay = 1000; //millise
    // conds

    public static List<popularRecordGetModel> fragmentRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_item);

        toolbar = findViewById(R.id.ToolBarSub);
        setSupportActionBar(toolbar);
        toolbartitle = findViewById(R.id.toolbarTitle);
        imageBadgeView = findViewById(R.id.imageBadgeView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        listName = getIntent().getExtras().getString("value");
        toolbartitle.setText("" + org.apache.commons.lang3.StringUtils.capitalize(listName));
        imageBadgeView.setBadgeValue(ListDesciption.sharedPreferences.getInt("Key", 0));

        helper = new sqlLiteOpenHelper(this);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        GeneralFragment generalFragment = new GeneralFragment();
        Bundle bundle = new Bundle();
        bundle.putString("listName", listName);
        generalFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.frameLayout, generalFragment).commit();
        SubItemActivity.fragmentRecord = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                //do something
                ;
                imageBadgeView.setBadgeValue(ListDesciption.sharedPreferences.getInt("Key", 0));
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
