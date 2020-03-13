package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FinalScoreActivity extends AppCompatActivity {

    private int scoreFinal;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        Bundle b = getIntent().getExtras();
        scoreFinal = b.getInt("scoreFinal");

        textView = findViewById(R.id.tvScore);
        textView.setText(scoreFinal);
    }
}
