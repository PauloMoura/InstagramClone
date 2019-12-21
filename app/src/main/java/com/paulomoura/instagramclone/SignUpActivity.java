package com.paulomoura.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    public enum ActiveScreen {
        SIGN_UP("Sign Up"), LOGIN("Login");

        private String title;

        ActiveScreen(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnActive, btnInactive;
    private ActiveScreen activeScreen = ActiveScreen.SIGN_UP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setActionBarText();
        bindViews();
        setClickListeners();
    }

    private void setActionBarText() {
        getSupportActionBar().setTitle(activeScreen.getTitle());
    }

    private void bindViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnActive = findViewById(R.id.btnActive);
        btnInactive = findViewById(R.id.btnInactive);
    }

    private void setClickListeners() {
        btnActive.setOnClickListener(view -> {

            ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);

            if (activeScreen == ActiveScreen.SIGN_UP) {

                progressDialog.setMessage("Signning up");
                progressDialog.show();

                ParseUser parseUser = new ParseUser();
                parseUser.setEmail(edtEmail.getText().toString());
                parseUser.setUsername(edtUsername.getText().toString());
                parseUser.setPassword(edtPassword.getText().toString());

                parseUser.signUpInBackground(exception -> {
                    if (exception == null) {
                        transitionToSocialMedia();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Failed to Sign Up", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
            } else {
                progressDialog.setMessage("Logging");
                progressDialog.show();

                ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), (user, exception) -> {
                    if (user != null && exception == null) {
                        transitionToSocialMedia();
                    } else {
                        Toast.makeText(SignUpActivity.this, "failed to login", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
            }
        });

        btnInactive.setOnClickListener(view -> {
            if (activeScreen == ActiveScreen.SIGN_UP) {
                activeScreen = ActiveScreen.LOGIN;
                edtEmail.setVisibility(View.INVISIBLE);
                btnActive.setText("Login");
                btnInactive.setText("Sign Up");
            } else {
                activeScreen = ActiveScreen.SIGN_UP;
                edtEmail.setVisibility(View.VISIBLE);
                btnActive.setText("Sign Up");
                btnInactive.setText("Login");
            }
            setActionBarText();
        });
    }

    public void rootClicked(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void transitionToSocialMedia() {
        Intent intent = new Intent(SignUpActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
