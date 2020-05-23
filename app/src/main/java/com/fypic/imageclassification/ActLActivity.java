package com.fypic.imageclassification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActLActivity extends AppCompatActivity {
    private static String usern;
    private static boolean loggedin;
    DatabaseHelper db;
    Button b1,b2;
    EditText t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_l);

        db = new DatabaseHelper(this);
        b1 = findViewById(R.id.actlbuttonl);
        t1 = findViewById(R.id.usernamel);
        t2 = findViewById(R.id.passwordl);
        loggedin = false;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = t1.getText().toString();
                String pass = t2.getText().toString();
                usern = t1.getText().toString();


                if (db.verifyUser(user, pass)) {

                    Intent intent = new Intent(ActLActivity.this, MainActivityLogged.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    loggedin = true;
                    Toast.makeText(ActLActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();


                } else {
                    loggedin = false;
                    Toast.makeText(ActLActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static String getUser() {
        return usern;
    }

    public static boolean getLoginStatus() {
        return loggedin;
    }

    public static void logout(){
        loggedin = false;
    }


}

