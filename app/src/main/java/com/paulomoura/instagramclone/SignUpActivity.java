package com.paulomoura.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupViews();
    }

    private void setupViews() {
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> btnSaveClicked());
    }

    private void btnSaveClicked() {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.valueOf(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.valueOf(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.valueOf(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.valueOf(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(e -> {
                if (e == null) {
                    FancyToast.makeText(SignUpActivity.this,
                            kickBoxer.get("name") + " is saved to server",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.SUCCESS,
                            true)
                            .show();
                } else {
                    FancyToast.makeText(SignUpActivity.this,
                            kickBoxer.get("name") + " has not been saved/n" + e.getMessage(),
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,
                            true)
                            .show();
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUpActivity.this,
                    e.getMessage(),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    true)
                    .show();
        }
    }
}
