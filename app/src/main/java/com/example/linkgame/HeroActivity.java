package com.example.linkgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.Arrays;

public class HeroActivity extends AppCompatActivity implements CardStackView.ItemExpendListener {
    private CardStackView mStackView;
    private CardStackAdapter mCardStackAdapter;

    // 子项颜色数字初始化
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it=getIntent();
        String theme=it.getStringExtra("theme");
        switch (theme){
            case "blue":
                setTheme(R.style.AppTheme_Blue);
                break;
            case "gold":
                Log.i("theme is","gold");
                setTheme(R.style.AppTheme_GoldActionBar);
                break;
            case "brown":
                setTheme(R.style.AppTheme_BrownActionBar);
                break;
            case "green":
                setTheme(R.style.AppTheme_GreenActionBar);
                break;
            case "black":
                setTheme(R.style.AppTheme_BlackActionBar);
                break;
        }
        setContentView(R.layout.activity_hero);
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mStackView.setItemExpendListener(this);
        mCardStackAdapter = new CardStackAdapter(this);
        mStackView.setAdapter(mCardStackAdapter);

        // 延时更新卡片内的内容
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mCardStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 50
        );
    }

    // 创建Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu点击事件响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pre:
                mStackView.pre();
                break;
            case R.id.menu_next:
                mStackView.next();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 子项点击事件响应
    @Override
    public void onItemExpend(boolean expend) {
    }
}
