package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView image;
    TextView title;
    Button btncallSignIn, btnSignUp;
    TextInputLayout Regusername, Regpassword, Regemail, Regaddress;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        db = new DatabaseHelper(this);
        setContentView(R.layout.activity_sign_up);

        Hooks();

        btncallSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //effect
                Intent intent = new Intent(SignUp.this, Login.class);

                Pair[] pairs = new Pair[6];

                pairs[0] = new Pair<View,String>(image, "logo_image");
                pairs[1] = new Pair<View,String>(title, "logo_text");
                pairs[2] = new Pair<View,String>(Regusername, "username_tran");
                pairs[3] = new Pair<View,String>(Regpassword, "password_tran");
                pairs[4] = new Pair<View,String>(btnSignUp, "button_tran");
                pairs[5] = new Pair<View,String>(btncallSignIn, "login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                    startActivity(intent,options.toBundle());

                }
            }
        });
    }

    private void Hooks(){
        image = findViewById(R.id.logoImage);
        title = findViewById(R.id.logoText);
        Regusername = findViewById(R.id.username);
        Regpassword = findViewById(R.id.password);
        Regemail = findViewById(R.id.email);
        Regaddress = findViewById(R.id.address);
        btncallSignIn = findViewById(R.id.btnReturnSignin);
        btnSignUp = findViewById(R.id.btnRegSignUp);
        registerUser();
    }

    public void registerUser () {
        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = Regusername.getEditText().getText().toString().trim();
                        String password = Regpassword.getEditText().getText().toString().trim();
                        String email = Regemail.getEditText().getText().toString().trim();
                        String address = Regaddress.getEditText().getText().toString().trim();
                        boolean isSuccess = db.insertUser(username, password, email, address);
                        if(isSuccess) {
                            Toast.makeText(SignUp.this,"SignUp Successfully", Toast.LENGTH_LONG).show();
                            btncallSignIn.performClick();
                        } else {
                            Toast.makeText(SignUp.this,"Username or email already existed", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
