package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    ImageView image;
    TextView title;
    Button btncallSignIn, btnSignUp;
    TextInputLayout Regusername, Regpassword, Regemail, Regaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
