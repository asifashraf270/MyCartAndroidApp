package glowingsoft.com.mycart.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

import glowingsoft.com.mycart.Adapters.FragmentPagerAdapter;
import glowingsoft.com.mycart.R;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter adapter;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    CallbackManager callbackManager;
    private static final String tag = "LoginActivity";
    GoogleSignInClient googleSignIn;
    int RC_SIGN_IN = 123;
    ProgressDialog progressDialog;
    String sharedPreferencesFile;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView = findViewById(R.id.previousActivity);
        imageView.setOnClickListener(this);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.layOut);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#000000"));

        sharedPreferences = this.getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignIn = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            startActivity(new Intent(loginActivity.this, MainActivity.class));
            finish();
        } else {
            Log.d(tag, "required");


            SignInButton signInButton = findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            findViewById(R.id.sign_in_button).setOnClickListener(this);
        }
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        tabLayout.setTabTextColors(Color.parseColor("#FFD2D0D0"), Color.parseColor("#ffffff"));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Log.d(tag, "isLoggedIn");
            startActivity(new Intent(loginActivity.this, MainActivity.class));
            finish();
        } else {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d(tag, "onsucces");

                    startActivity(new Intent(loginActivity.this, MainActivity.class));
                    editor=sharedPreferences.edit();
                    editor.putInt("check",2);//loginwith facebook
                    finish();
                }

                @Override
                public void onCancel() {
                    Log.d(tag, "onCancel");
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.d(tag, "onError");
                    Toast.makeText(loginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            handleSignInResult(task);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previousActivity:
                finish();
                break;
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        progressDialog = new ProgressDialog(loginActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        Intent signInIntent = googleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        editor=sharedPreferences.edit();
        editor.putInt("check",3);//login with Google
        finish();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d(tag, "succesully find");
            progressDialog.dismiss();

            startActivity(new Intent(loginActivity.this, MainActivity.class));
            finish();
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d(tag, String.valueOf(e.getStatusCode()));
            progressDialog.dismiss();
        }

        // Signed in successfully, show authenticated UI.
        //
        // startActivity(new Intent(loginActivity.this, MainActivity.class));


    }
}
