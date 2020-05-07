package com.example.uncledrew.studynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FourthActivity extends AppCompatActivity {

    public static final String TAG = "FouthActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印所在栈ID
        Log.d(TAG, "TaskID: " + getTaskId());
        setContentView(R.layout.activity_fourth);
        Button button = findViewById(R.id.activity_fourth_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourthActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}
