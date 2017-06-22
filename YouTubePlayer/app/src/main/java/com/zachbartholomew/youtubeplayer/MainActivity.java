package com.zachbartholomew.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPlaySingle = (Button) findViewById(R.id.buttonPlaySingle);
        Button buttonStandAlone = (Button) findViewById(R.id.buttonStandAlone);
        buttonPlaySingle.setOnClickListener(this);
        buttonStandAlone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.buttonPlaySingle:
                intent = new Intent(this, YouTubeActivity.class);
                break;

            case R.id.buttonStandAlone:
                intent = new Intent(this, StandaloneActivity.class);
                break;

            default:
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
