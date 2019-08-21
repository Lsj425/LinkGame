package com.example.linkgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {
    private Button bt_submit;
    private EditText ed_username;
    private EditText ed_feedback;
    private OwlView mOwlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it = getIntent();
        String theme = it.getStringExtra("theme");
        switch (theme) {
            case "blue":
                setTheme(R.style.AppTheme);
                break;
            case "gold":
                setTheme(R.style.AppTheme_Gold);
                break;
            case "brown":
                setTheme(R.style.AppTheme_Brown);
                break;
            case "green":
                setTheme(R.style.AppTheme_Green);
                break;
            case "black":
                setTheme(R.style.AppTheme_Black);
                break;
        }
        setContentView(R.layout.activity_feed_back);

        // 初始化顶部任务头像
        mOwlView = findViewById(R.id.feedback_owl_view);

        // 初始化文本框
        ed_username = findViewById(R.id.ed_username);
        ed_feedback = findViewById(R.id.ed_feedback);

        // 文本框点击事件响应
        ed_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    mOwlView.open();
                } else {//失去焦点
                    mOwlView.close();
                }
            }
        });

        //提交按钮点击事件响应
        bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_username.getText().toString().length() > 15) {// 判断用户名输入是否合法
                    Toast.makeText(FeedBackActivity.this, "用户名大于15字！", Toast.LENGTH_SHORT).show();
                } else if (ed_username.getText().toString().length() == 0) {
                    Toast.makeText(FeedBackActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                } else {
                    if (ed_feedback.getText().toString().length() > 500) {// 判断反馈意见输入是否合法
                        Toast.makeText(FeedBackActivity.this, "意见反馈大于500字！", Toast.LENGTH_SHORT).show();
                    } else if (ed_feedback.getText().toString().length() == 0) {
                        Toast.makeText(FeedBackActivity.this, "请输入反馈意见！", Toast.LENGTH_SHORT).show();
                    } else {
                        String emailTitle = "用户\"" + ed_username.getText().toString() + "\"关于响指连连看的反馈";
                        String emailContent = ed_feedback.getText().toString();
                        Intent email = new Intent(android.content.Intent.ACTION_SEND);
                        email.setType("plain/text");
                        String[] emailReciver = new String[]{"913348891@qq.com"};
                        email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);//设置邮件地址
                        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailTitle);//设置邮件标题
                        email.putExtra(android.content.Intent.EXTRA_TEXT, emailContent);//设置发送的内容
                        startActivity(Intent.createChooser(email, "请选择QQ邮箱发送"));//调用系统的邮件系统
                    }
                }
            }
        });
    }
}
