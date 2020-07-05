package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    ImageView image;
    TextView title;
    Button btnLogin, btnResetPassword, callSignUp;
    TextInputLayout username, password;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //database
        db = new DatabaseHelper(this);

        Hooks();

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[6];

                pairs[0] = new Pair<View,String>(image, "logo_image");
                pairs[1] = new Pair<View,String>(title, "logo_text");
                pairs[2] = new Pair<View,String>(username, "username_tran");
                pairs[3] = new Pair<View,String>(password, "password_tran");
                pairs[4] = new Pair<View,String>(btnLogin, "button_tran");
                pairs[5] = new Pair<View,String>(callSignUp, "login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        });
    }

    private void Hooks() {
        //Hook
        btnResetPassword = findViewById(R.id.btnResetPassword);
        image = findViewById(R.id.logoImage);
        title = findViewById(R.id.logoText);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        callSignUp = findViewById(R.id.btnCallSignUp);
        loginUser();
    }

    public void loginUser () {
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String usernamez = username.getEditText().getText().toString().trim();
                        String passwordz = password.getEditText().getText().toString().trim();
                        Cursor user = db.validateUser(usernamez, passwordz);
                        if (!(user.moveToFirst()) || user.getCount() == 0) {

                            // get user
                            //SharedPreferences sp1 = this.getSharedPreferences("LoggedUser", MODE_PRIVATE);
                            //String username = sp1.getString("Username", null);
                            Toast.makeText(Login.this,"Wrong Username or Password", Toast.LENGTH_LONG).show();
                        } else {
                            SharedPreferences sp = getSharedPreferences("LoggedUser", MODE_PRIVATE);
                            SharedPreferences.Editor Ed = sp.edit();
                            Ed.putString("Username",usernamez );
                            Ed.apply();
                            Toast.makeText(Login.this,"Login Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                    }
                }
        );
    }
}
