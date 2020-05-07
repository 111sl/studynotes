package com.example.uncledrew.studynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //打印每次启动的实例ID
        Log.d(TAG, "onCreate" + this.toString());
        //打印所在栈ID
        Log.d(TAG, "TaskID: " + getTaskId());
        setContentView(R.layout.activity_second);
        Button button = findViewById(R.id.activity_second_test);
        Button tmp_button = findViewById(R.id.second_tmp_button);
        Button singleInstance = findViewById(R.id.second_third_test);

        //singleTop模式测试
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        //中转按钮，跳转到其他活动，再从其他活动跳转回来验证singleTop模式在非栈顶活动时状况
        tmp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        //跳转到singleInstance模式的活动验证栈ID不同
        singleInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,FourthActivity.class);
                startActivity(intent);
            }
        });
    }


    //配合验证singleTask使用的
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
