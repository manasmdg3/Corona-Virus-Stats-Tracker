package com.manas.coronavirusstats;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity
            extends AppCompatActivity {

    ImageView ivNurse, ivFever, ivCough, ivSore, ivHands, ivMask, ivClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //Add Back Button to Action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivNurse = (ImageView) findViewById(R.id.nurse);
        ivFever = (ImageView) findViewById(R.id.fever);
        ivCough = (ImageView) findViewById(R.id.caugh);
        ivSore = (ImageView) findViewById(R.id.sore);
        ivHands = (ImageView) findViewById(R.id.hands);
        ivMask = (ImageView) findViewById(R.id.masks);
        ivClean = (ImageView) findViewById(R.id.clean);



    }

    //For Back Button to work
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

