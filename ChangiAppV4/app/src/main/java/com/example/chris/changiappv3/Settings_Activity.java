package com.example.chris.changiappv3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new Settings_Fragment())
                .commit();
    }
}