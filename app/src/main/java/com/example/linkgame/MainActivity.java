package com.example.linkgame;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

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
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View drawerHeader;
    public ImageView drawerHeaderImage;
    public TextView drawerHeaderNickName;
    public TextView drawerHeaderName;
    public String activityTheme="blue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.getInstance().init(this);
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

        // 初始化侧滑导航栏
        drawer = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(nOnNavigationItemSelectedListener);
        drawerHeader = navigationView.inflateHeaderView(R.layout.drawer_header);

        drawerHeaderImage=drawerHeader.findViewById(R.id.drawer_iv);
        drawerHeaderNickName=drawerHeader.findViewById(R.id.drawer_iv_nickname);
        drawerHeaderName=drawerHeader.findViewById(R.id.drawer_iv_name);
        setDrawerHeader();

        // 初始化顶部菜单栏
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("菜单");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);// 初始化侧滑导航栏呼出按钮
        toggle.syncState();

        // 初始化底部导航栏
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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

    // 存储侧滑栏头部信息
    public void storeDrawerHeader(int position){
        switch (position) {
            case 0:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("header", 1).apply();
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("theme", 1).apply();
                break;
            case 1:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("header", 2).apply();
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("theme", 2).apply();
                break;
            case 2:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("header", 3).apply();
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("theme", 3).apply();
                break;
            case 3:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("header", 4).apply();
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("theme", 4).apply();
                break;
            case 4:
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("header", 5).apply();
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("theme", 5).apply();
                break;
        }
    }

    // 设置侧滑栏头部信息
    public void setDrawerHeader() {
        int position=getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("header", 1);
        switch (position) {
            case 1:
                drawerHeaderImage.setImageDrawable(getResources().getDrawable
                        ((R.mipmap.ic_drawer_head_captain_america)));
                drawerHeaderNickName.setText("美国队长");
                drawerHeaderName.setText("史蒂夫·罗杰斯");
                break;
            case 2:
                drawerHeaderImage.setImageDrawable(getResources().getDrawable
                        ((R.mipmap.ic_drawer_head_ironman)));
                drawerHeaderNickName.setText("钢铁侠");
                drawerHeaderName.setText("托尼·斯塔克");
                break;
            case 3:
                drawerHeaderImage.setImageDrawable(getResources().getDrawable
                        ((R.mipmap.ic_drawer_head_thor)));
                drawerHeaderNickName.setText("雷神");
                drawerHeaderName.setText("索尔·奥丁森");
                break;
            case 4:
                drawerHeaderImage.setImageDrawable(getResources().getDrawable
                        ((R.mipmap.ic_drawer_head_hulk)));
                drawerHeaderNickName.setText("浩克");
                drawerHeaderName.setText("布鲁斯·班纳");
                Log.i("case3","here!");
                break;
            case 5:
                drawerHeaderImage.setImageDrawable(getResources().getDrawable
                        ((R.mipmap.ic_drawer_head_venom)));
                drawerHeaderNickName.setText("毒液");
                drawerHeaderName.setText("埃迪·布洛克");
                break;
        }
    }

    //显示修改主题色 Dialog
    private void showUpdateThemeDialog() {
        String[] themes = ThemeManager.getInstance().getThemes();
        new MaterialDialog.Builder(this)
                .title("魔性配色")
                .titleGravity(GravityEnum.CENTER)
                .items(themes)
                .negativeText("取消")
                .itemsCallback(((dialog, itemView, position, text) -> {
                    storeDrawerHeader(position);
                    ThemeManager.getInstance().setTheme(this, themes[position]);
//                    linearlayout.setBackgroud(getResources().getColor(R.color.bg1));
                    this.recreate();
                }))
                .show();
    }

    // 获取图片uri
    public Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }

    // 分享
    private void share() {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "大扎好，我系重修哥，\"响指连连看\"即将上线酷安应用分享平台，" +
                "介四里没有挽过的船新版本，挤需体验三番钟，里造会肝我一样，爱象节款游戏！");
        startActivity(Intent.createChooser(textIntent, "分享快乐"));
    }

    // 退出dialog
    private void exit() {
        new MaterialDialog.Builder(this)// 初始化建造者
                .title("确认退出")// 标题
                .content("真的要离开人家嘛？我好舍不得~")// 内容
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        finish();
                    }
                })
                .negativeText("取消")
                .show();// 显示对话框
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 顶部菜单栏子项选择状态设置
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

    // 顶部菜单栏点击事件响应
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

    // 侧滑导航栏点击事件响应
    private NavigationView.OnNavigationItemSelectedListener nOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("theme", 1)){
                case 1:
                    activityTheme="blue";
                    break;
                case 2:
                    activityTheme="gold";
                    break;
                case 3:
                    activityTheme="brown";
                    break;
                case 4:
                    activityTheme="green";
                    break;
                case 5:
                    activityTheme="black";
                    break;
            }

            switch (item.getItemId()) {
                case R.id.nav_static:// 显示英雄资料卡界面
                    Intent intent1=new Intent(MainActivity.this, HeroActivity.class);
                    intent1.putExtra("theme",activityTheme);
                    startActivity(intent1);
                    break;
                case R.id.nav_theme:// 更改应用主题
                    showUpdateThemeDialog();
                    break;
                case R.id.nav_feedback:// 用户反馈
                    Intent intent2=new Intent(MainActivity.this, FeedBackActivity.class);
                    intent2.putExtra("theme",activityTheme);
                    startActivity(intent2);
                    break;
                case R.id.nav_share:// 分享给好友
                    share();
                    break;
                case R.id.nav_about:// 关于本应用
                    Intent intent3=new Intent(MainActivity.this, AboutActivity.class);
                    intent3.putExtra("theme",activityTheme);
                    startActivity(intent3);
                    break;
                case R.id.nav_exit:// 退出
                    exit();
                    break;
                default:
                    break;
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    // 底部导航栏点击事件响应
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
}

