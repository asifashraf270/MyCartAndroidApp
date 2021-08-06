package glowingsoft.com.mycart.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glowingsoft.com.mycart.Activities.MainActivity;
import glowingsoft.com.mycart.R;


public class signUp extends Fragment implements View.OnClickListener {
    TextInputLayout emailedittext, passwordEditText;
    AppCompatButton createAccountBtn;
    String email, password;
    private static String tag = "signUpFragment";
    AppCompatCheckBox checkBoxRemember;
    SharedPreferences sharedPreferences;
    String sharedPreferencesFile = "File";
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        emailedittext = view.findViewById(R.id.emailWrap);
        passwordEditText = view.findViewById(R.id.passwordWrap);
        createAccountBtn = view.findViewById(R.id.button);
        checkBoxRemember = view.findViewById(R.id.checkBox);
        sharedPreferences = getContext().getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);
        if (checkSharedPrefrences()) {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();

        } else {
            createAccountBtn.setOnClickListener(this);
        }


        return view;
    }

    private String getData(TextInputLayout textInputLayout) {
        String data = textInputLayout.getEditText().getText().toString();
        return data;
    }

    private boolean checkBoxSelected(AppCompatCheckBox checkBox) {
        if (checkBox.isChecked()) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getDataFromField();
                break;
        }
    }

    private void getDataFromField() {
        email = getData(emailedittext);
        password = getData(passwordEditText);
        if (email.length() == 0 && password.length() == 0) {
            emailedittext.setError("Email is required");
            passwordEditText.setErrorEnabled(true);
            passwordEditText.setError("Password is required");
        } else {
            Log.d(tag, email + "/" + password);
            if (checkBoxSelected(checkBoxRemember)) {
                sharedPrefrenceStoreRecord(email, password);
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

            } else {
                Log.d(tag, "shared preferences not required");
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

            }
        }
    }

    private void sharedPrefrenceStoreRecord(String email, String password) {
        editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("pass", password);
        editor.putInt("check", 1);
        editor.commit();
    }

    private boolean checkSharedPrefrences() {
        email = sharedPreferences.getString("email", "");
        password = sharedPreferences.getString("pass", "");
        if (email.length() == 0 && password.length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
