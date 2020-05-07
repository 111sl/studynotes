package com.example.uncledrew.studynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {

    public static final String TAG = "ThirdActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //打印每次启动的实例ID
        Log.d(TAG, "onCreate" + this.toString());
        //打印所在栈ID
        Log.d(TAG, "TaskID: "+getTaskId());
        Button button = findViewById(R.id.activity_third_test);
        Button tmp_button = findViewById(R.id.third_tmp_button);

        //singleTask模式测试
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        //中转按钮，启动其他活动再从其他活动启动此活动验证singleTask模式是否会启动新实例
        tmp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
