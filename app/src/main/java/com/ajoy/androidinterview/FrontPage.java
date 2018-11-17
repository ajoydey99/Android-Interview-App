package com.ajoy.androidinterview;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FrontPage extends AppCompatActivity implements View.OnClickListener {

    Button bsimple, btough, bseeother, brateapp;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);

        bsimple = findViewById(R.id.bsq);
        bsimple.setOnClickListener(this);

        btough = findViewById(R.id.btq);
        btough.setOnClickListener(this);

        bseeother = findViewById(R.id.botherapp);
        bseeother.setOnClickListener(this);

        brateapp = findViewById(R.id.brate);
        brateapp.setOnClickListener(this);

        layout = findViewById(R.id.front_titlebar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bsq:

                try {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                    Intent isq = new Intent(FrontPage.this, SimpleQuestion.class);
                    isq.putExtra(Constants.KEY_ANIME_TYPE, Constants.TransitionType.SlideXML);
                    startActivity(isq, options.toBundle());
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Activity not found",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btq:

                try {
                    ActivityOptions options2 = ActivityOptions.makeSceneTransitionAnimation(this);
                    Intent itq = new Intent(FrontPage.this, ToughQuestion.class);
                    itq.putExtra(Constants.KEY_ANIME_TYPE, Constants.TransitionType.SlideXML);
                    startActivity(itq, options2.toBundle());
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Activity not found",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.botherapp:

                Uri projectUri = Uri.parse("http://github.com/ajoydey99?tab=repositories");
                Intent gotoProject = new Intent(Intent.ACTION_VIEW, projectUri);
                startActivity(gotoProject);
                break;

            case R.id.brate:

                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent gotoMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(gotoMarket);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName());
                    Intent gotoMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(gotoMarket);
                }
                break;
        }
    }
}
