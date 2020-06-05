package com.example.bugsgame.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bugsgame.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyLog";
    Button start,rules,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InizializateComponents();
    }
    private void InizializateComponents()
    {
        start = findViewById(R.id.start);
        rules = findViewById(R.id.rule);
        exit = findViewById(R.id.exit);

        start.setOnClickListener(CreateOnClickListener());
        rules.setOnClickListener(CreateOnClickListener());
        exit.setOnClickListener(CreateOnClickListener());
    }
    private View.OnClickListener CreateOnClickListener()
    {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 switch (v.getId())
                 {
                     case R.id.start:
                         OnStartClick();break;
                     case R.id.rule:
                         OnRuleClick();break;
                     case R.id.exit:
                         OnExitClick();break;
                 }

            }

        };
        return onClickListener;

    }
    private void OnStartClick() {
        startActivity(new Intent(MainActivity.this,GameStarter.class));
    }
    private void OnRuleClick() {
        startActivity(new Intent(MainActivity.this,Rule.class));
    }
    private void OnExitClick() {
        this.finish();
    }
}
