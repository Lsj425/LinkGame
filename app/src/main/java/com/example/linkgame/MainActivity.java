package com.example.linkgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Thread thread;
    private String bgm;
    private Menu aMenu;
    private LinkGameFragment linkGameFragment = null;
    private MainFragment mainFragment = null;
    private RankingFragment rankingFragment = null;
    private Fragment mContent = null;
    private GuideFragment guideFragment = null;
    private static MediaPlayer mp = null;
    private boolean optionMenuOn = false;// 标示optionmenu显示状态
    private boolean isMusicStop;// 标示音效开关状态
    public boolean isGuide;// 标示新手指引选中状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取当前顶部菜单栏中音效开关选中状态
        int choose1 = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("music", 1);
        switch (choose1) {
            case 1:
                isMusicStop = false;
                break;
            case 2:
                isMusicStop = true;
                break;
        }

        // 获取当前顶部菜单栏中选中的音乐选中状态
        int choose2 = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("bgm", 1);
        switch (choose2) {
            case 1:
                bgm = "Avengers";
                break;
            case 2:
                bgm = "Assemble";
                break;
            case 3:
                bgm = "Helicarrier";
                break;
            case 4:
                bgm = "Heroes";
                break;
            case 5:
                bgm = "InfinityWar";
                break;
        }

        // 创建一个用于播放背景音乐的线程
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                playBGSound(bgm);// 播放背景音乐
            }
        });
        thread.start();

        // 创建底部导航栏
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 创建顶部菜单栏
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("LinkGame");

        // 设置默认fragment
        if (mContent == null)
            setDefaultFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isMusicStop) {
            mp.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mp.pause();
    }

    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.stop();// 停止播放
            mp.release();// 释放资源
            mp = null;
        }
        if (thread != null) {
            thread = null;
        }
        super.onDestroy();
    }

    // 获取音效开关选中状态
    public boolean isMusicStop() {
        return isMusicStop;
    }

    // 获取新手指引选中状态
    public boolean isGuide() {
        return isGuide;
    }

    // 设置新手指引选中状态
    public void setGuide(boolean guide) {
        isGuide = guide;
    }

    // 设置菜单栏信息显示状态
    public void setOptionMenuOn(boolean optionMenuOn) {
        this.optionMenuOn = optionMenuOn;
    }

    // 控制顶部菜单栏信息显示
    public void checkOptionMenu() {
        if (aMenu != null) {
            if (optionMenuOn) {
                for (int i = 0; i < 3; i++) {
                    aMenu.getItem(i).setVisible(true);
                    aMenu.getItem(i).setEnabled(true);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    aMenu.getItem(i).setVisible(false);
                    aMenu.getItem(i).setEnabled(false);
                }
            }
        }
    }

    // 播放背景音乐
    private void playBGSound(String music) {
        if (mp != null) {
            mp.release();// 释放资源
        }
        switch (music) {
            case "Avengers":
                mp = MediaPlayer.create(MainActivity.this, R.raw.appbgm1);
                break;
            case "Assemble":
                mp = MediaPlayer.create(MainActivity.this, R.raw.background);
                break;
            case "Helicarrier":
                mp = MediaPlayer.create(MainActivity.this, R.raw.appbgm2);
                break;
            case "Heroes":
                mp = MediaPlayer.create(MainActivity.this, R.raw.appbgm3);
                break;
            case "InfinityWar":
                mp = MediaPlayer.create(MainActivity.this, R.raw.appbgm4);
                break;
        }
        if (!isMusicStop) {
            mp.start();// 开始播放
            mp.setLooping(true);
        }

        // 为MediaPlayer添加播放完事件监听器
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    Thread.sleep(5000);// 线程休眠五秒钟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 将说明界面设置为默认fragment
    private void setDefaultFragment() {
        if (guideFragment == null)
            guideFragment = GuideFragment.newInstance();
        FragmentTransaction mFragmentTrans = getSupportFragmentManager().beginTransaction();
        mFragmentTrans.add(R.id.container, guideFragment).commit();
        mContent = guideFragment;
    }

    // 显示欢迎界面
    public void showMainFragment() {
        if (mainFragment == null)
            mainFragment = MainFragment.newInstance();
        switchContent(mainFragment);
    }

    // 显示游戏界面
    public void showLinkGameFragment() {
        if (linkGameFragment == null)
            linkGameFragment = LinkGameFragment.newInstance();
        switchContent(linkGameFragment);
    }

    // 显示说明界面
    public void showAboutFragment() {
        if (guideFragment == null)
            guideFragment = GuideFragment.newInstance();
        switchContent(guideFragment);
    }

    // 显示排行榜界面
    public void showRankingFragment() {
        if (rankingFragment == null)
            rankingFragment = RankingFragment.newInstance();
        switchContent(rankingFragment);
    }

    // 替换fragment
    private void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, to).setTransition(TRANSIT_FRAGMENT_FADE).commit();
            mContent = to;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 底部导航点击事件响应
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:
                    showAboutFragment();
                    // 隐藏Menu中游戏按钮
                    optionMenuOn = false;
                    checkOptionMenu();
                    return true;
                case R.id.navigation_game:
                    showMainFragment();
                    // 隐藏Menu中游戏按钮
                    optionMenuOn = false;
                    checkOptionMenu();
                    return true;
                case R.id.navigation_billboard:
                    showRankingFragment();
                    // 隐藏Menu中游戏按钮
                    optionMenuOn = false;
                    checkOptionMenu();
                    return true;
            }
            return false;
        }
    };

    // 菜单栏点击事件响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shuffle:// 刷新按钮
                if (mContent == linkGameFragment)
                    linkGameFragment.shuffle();
                break;
            case R.id.newGame:// 开始新游戏按钮
                if (mContent == linkGameFragment)
                    linkGameFragment.loadData(getSharedPreferences
                            ("linkgame", Context.MODE_PRIVATE).getInt("rank", 1));
                break;
            case R.id.easy:
                if (mContent == linkGameFragment)
                    linkGameFragment.loadData(LinkGameFragment.EASY);
                getSharedPreferences("linkgame", MODE_PRIVATE)
                        .edit().putInt("rank", LinkGameFragment.EASY).apply();
                break;
            case R.id.medium:
                if (mContent == linkGameFragment)
                    linkGameFragment.loadData(LinkGameFragment.MEDIUM);
                getSharedPreferences("linkgame", MODE_PRIVATE)
                        .edit().putInt("rank", LinkGameFragment.MEDIUM).apply();
                break;
            case R.id.diffculty:
                if (mContent == linkGameFragment)
                    linkGameFragment.loadData(LinkGameFragment.DIFFICULTY);
                getSharedPreferences("linkgame", MODE_PRIVATE)
                        .edit().putInt("rank", LinkGameFragment.DIFFICULTY).apply();
                break;
            case R.id.on:
                getSharedPreferences("linkgame", MODE_PRIVATE)
                        .edit().putInt("music", 1).apply();
                if (isMusicStop) {
                    mp.start();
                    isMusicStop = false;
                }
                break;
            case R.id.off:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("music", 2).apply();
                mp.pause();
                isMusicStop = true;
                break;
            case R.id.song1:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("bgm", 1).apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playBGSound("Avengers");// 播放背景音乐
                    }
                });
                thread.start();// 开启线程
                break;
            case R.id.song2:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("bgm", 2).apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playBGSound("Assemble");// 播放背景音乐
                    }
                });
                thread.start();// 开启线程
                break;
            case R.id.song3:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("bgm", 3).apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playBGSound("Helicarrier");// 播放背景音乐
                    }
                });
                thread.start();// 开启线程
                break;
            case R.id.song4:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("bgm", 4).apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playBGSound("Heroes");// 播放背景音乐
                    }
                });
                thread.start();// 开启线程
                break;
            case R.id.song5:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("bgm", 5).apply();
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playBGSound("InfinityWar");// 播放背景音乐
                    }
                });
                thread.start();// 开启线程
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 子菜单选择状态设置
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int rank = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1);
        int music = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("music", 1);
        int bgm = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("bgm", 1);
        switch (rank) {
            case LinkGameFragment.EASY:
                menu.findItem(R.id.easy).setChecked(true);
                break;
            case LinkGameFragment.MEDIUM:
                menu.findItem(R.id.medium).setChecked(true);
                break;
            case LinkGameFragment.DIFFICULTY:
                menu.findItem(R.id.diffculty).setChecked(true);
                break;

        }
        switch (music) {
            case 1:
                menu.findItem(R.id.on).setChecked(true);
                break;
            case 2:
                menu.findItem(R.id.off).setChecked(true);
                break;
        }
        switch (bgm) {
            case 1:
                menu.findItem(R.id.song1).setChecked(true);
                break;
            case 2:
                menu.findItem(R.id.song2).setChecked(true);
                break;
            case 3:
                menu.findItem(R.id.song3).setChecked(true);
                break;
            case 4:
                menu.findItem(R.id.song4).setChecked(true);
                break;
            case 5:
                menu.findItem(R.id.song5).setChecked(true);
                break;
        }
        // 获取当前menu并调用checkOptionMenu()方法显示menu
        aMenu = menu;
        checkOptionMenu();
        return super.onPrepareOptionsMenu(menu);
    }
}

