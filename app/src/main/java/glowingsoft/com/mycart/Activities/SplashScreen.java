package glowingsoft.com.mycart.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.facebook.stetho.Stetho;

import java.util.Timer;
import java.util.TimerTask;

import glowingsoft.com.mycart.R;

public class SplashScreen extends AppCompatActivity {
    Timer timer;
    ProgressBar progressBar;
//splash screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        progressBar = findViewById(R.id.progresssBar);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.   class));
                finish();
            }
        }, 3000);
    }
}
