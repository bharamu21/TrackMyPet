package com.benayah.app.trackmypet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by User on 18-07-2017.
 */

public class FindHomeActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Find home for your pet");

        text = (TextView) findViewById(R.id.frame_title);
        text.setText("FindHomeActivity");

    }
}
