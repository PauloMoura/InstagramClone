package com.paulomoura.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private Button btnSave, btnGetAllData, btnNextActivity;
    private TextView txtGetData;

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
        txtGetData = findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(v -> txtGetDataClicked());
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnGetAllData.setOnClickListener(view -> {
            ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

            queryAll.whereGreaterThan("punchPower", 300);
            queryAll.whereStartsWith("name", "Joh");

            queryAll.findInBackground(((objects, e) -> {
                if (e == null) {
                    if (objects.size() > 0) {

                        StringBuilder allKickBoxers = new StringBuilder();

                        for(ParseObject parseObject : objects) {
                            allKickBoxers.append(parseObject.get("name"))
                                .append(" - Punch Speed: ")
                                .append(parseObject.get("punchSpeed"))
                                .append(" Punch Power: ")
                                .append(parseObject.get("punchPower"))
                                .append("\n");
                        }

                        FancyToast.makeText(SignUpActivity.this,
                                allKickBoxers.toString(),
                                FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS,
                                true)
                                .show();
                    } else {
                        FancyToast.makeText(SignUpActivity.this,
                                e.getMessage(),
                                FancyToast.LENGTH_SHORT,
                                FancyToast.ERROR,
                                true)
                                .show();
                    }
                }
            }));
        });

        btnNextActivity = findViewById(R.id.btnNextActivity);
        btnNextActivity.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignUpLoginActivity.class);
            startActivity(intent);
        });
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

    private void txtGetDataClicked() {
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
        parseQuery.getInBackground("iAVdXtPTsk", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null && e == null) {
                    txtGetData.setText(object.get("name").toString() + " - Punch Power: " + object.get("punchPower") );
                }
            }
        });
        /*parseQuery.getInBackground("Jazl9rVmas", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null && e == null) {
                    txtGetData.setText(object.get("name").toString() +
                        "\nKick Speed: " + object.get("kickSpeed") +
                        "\nKick Power: " + object.get("kickPower") +
                        "\nPunch Speed: " + object.get("punchSpeed") +
                        "\nPunch Power: " + object.get("punchPower"));
                }
            }
        });*/
    }


}
