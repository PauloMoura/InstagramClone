package com.paulomoura.instagramclone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    EditText edtUsernameSignUp, edtPasswordSignUp, edtUsernameLogin, edtPasswordLogin;
    Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        edtUsernameSignUp = findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(view -> {
            ParseUser appUser = new ParseUser();
            appUser.setUsername(edtUsernameSignUp.getText().toString());
            appUser.setPassword(edtPasswordSignUp.getText().toString());
            appUser.signUpInBackground(exception -> {
                if (exception == null) {
                    FancyToast.makeText(SignUpLoginActivity.this,
                            appUser.get("username") + " is signed up successfully",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.SUCCESS,
                            true)
                            .show();
                } else {
                    FancyToast.makeText(SignUpLoginActivity.this,
                            exception.getMessage(),
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,
                            true)
                            .show();
                }
            });
        });

        btnLogin.setOnClickListener(view -> {
            ParseUser.logInInBackground(edtUsernameLogin.getText().toString(),
                edtPasswordLogin.getText().toString(),
                ((user, e) -> {
                    if (user != null && e == null) {
                        FancyToast.makeText(SignUpLoginActivity.this,
                                user.get("username") + " is logged in successfully",
                                FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS,
                                true)
                                .show();
                    } else {
                        FancyToast.makeText(SignUpLoginActivity.this,
                                e.getMessage(),
                                FancyToast.LENGTH_SHORT,
                                FancyToast.ERROR,
                                true)
                                .show();
                    }
                }));
        });
    }
}
