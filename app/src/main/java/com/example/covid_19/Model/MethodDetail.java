package com.example.covid_19.Model;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid_19.HelperClasses.MethodAdapter;
import com.example.covid_19.HelperClasses.MethodHelperClass;
import com.example.covid_19.HelperClasses.ProductAdapter;
import com.example.covid_19.HelperClasses.ProductHelperClass;
import com.example.covid_19.Methods;
import com.example.covid_19.R;

import java.util.ArrayList;

public class MethodDetail extends AppCompatActivity {

    CardView card1;
    TextView title1;
    TextView desc1;
    ImageView img1;

    RecyclerView methodRecycler;
    RecyclerView.Adapter methodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_method_detail);

        Hooks();
        //goAction();
        methodRecycler();
    }

    public void goBackMethodfromDetail(View view) {
        startActivity(new Intent(MethodDetail.this, Methods.class));
        finish();
    }

    private void methodRecycler() {
        methodRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<MethodHelperClass> methods = new ArrayList<>();

        methods.add(new MethodHelperClass(R.drawable.plus_icon, "What is a coronavirus?", "Coronaviruses are a large family of viruses which may cause illness in animals or humans.  In humans, several coronaviruses are known to cause respiratory infections ranging from the common cold to more severe diseases such as Middle East Respiratory Syndrome (MERS) and Severe Acute Respiratory Syndrome (SARS). The most recently discovered coronavirus causes coronavirus disease COVID-19."));
        methods.add(new MethodHelperClass(R.drawable.plus_icon, "What is COVID-19?", "COVID-19 is the infectious disease caused by the most recently discovered coronavirus. This new virus and disease were unknown before the outbreak began in Wuhan, China, in December 2019. COVID-19 is now a pandemic affecting many countries globally."));
        methods.add(new MethodHelperClass(R.drawable.plus_icon, "What are the symptoms of COVID-19?", "The most common symptoms of COVID-19 are fever, dry cough, and tiredness. Other symptoms that are less common and may affect some patients include aches and pains, nasal congestion, headache, conjunctivitis, sore throat, diarrhea, loss of taste or smell or a rash on skin or discoloration of fingers or toes. These symptoms are usually mild and begin gradually. Some people become infected but only have very mild symptoms.\n \nMost people (about 80%) recover from the disease without needing hospital treatment. Around 1 out of every 5 people who gets COVID-19 becomes seriously ill and develops difficulty breathing. Older people, and those with underlying medical problems like high blood pressure, heart and lung problems, diabetes, or cancer, are at higher risk of developing serious illness.  However, anyone can catch COVID-19 and become seriously ill. \n\n People of all ages who experience fever and/or  cough associated withdifficulty breathing/shortness of breath, chest pain/pressure, or loss of speech or movement should seek medical attention immediately. If possible, it is recommended to call the health care provider or facility first, so the patient can be directed to the right clinic."));

        methodAdapter = new MethodAdapter(methods);
        methodRecycler.setAdapter(methodAdapter);
    }

    private void Hooks(){
        img1 = findViewById(R.id.methodImg);
        card1 = findViewById(R.id.cardDetail1);
        title1 = findViewById(R.id.methodTitle);
        desc1 = findViewById(R.id.methodDesc);
        //
        methodRecycler = findViewById(R.id.methodRecycler);
    }

    private void goAction(){
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Drawable test1 = img1.getResources().getDrawable(R.drawable.plus_icon);
                    if(img1.getDrawable().equals(test1)){
                        desc1.setVisibility(View.VISIBLE);
                        title1.setTextColor(Color.parseColor("#E74C3C"));
                        img1.setImageDrawable(getResources().getDrawable(R.drawable.minus_icon));
                    }
                    else if (img1.getDrawable() == img1.getResources().getDrawable(R.drawable.minus_icon)){
                        desc1.setVisibility(View.GONE);
                        title1.setTextColor(Color.parseColor("#000000"));
                        img1.setImageDrawable(getResources().getDrawable(R.drawable.plus_icon));
                    }
                }
            }
        });
    }
}
