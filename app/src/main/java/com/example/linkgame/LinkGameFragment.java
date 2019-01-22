package com.example.linkgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.example.linkgame.base.OnItemClickListener;
import com.example.linkgame.bean.Item;
import com.example.linkgame.bean.Ranking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

public class LinkGameFragment extends Fragment {
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int DIFFICULTY = 3;

    public static final int ROW = 9;
    public static final int COLUMN = 8;

    private int[][] map = new int[ROW][COLUMN];
    private RecyclerView recyclerview;
    private LinkGameAdapter linkGameAdapter;
    private List<Item> itemList = new ArrayList<>();
    private MediaPlayer sound;
    private ImageView bomb;
    private boolean isMute;
    private View v1, v2;// 顶部菜单栏按钮
    private View tool;// 游戏道具
    MainFragment mainFragment;

    private int lastClick = -1;
    private long startTime = 0, endTime = 0;
    private boolean isBomb = false;
    private int leftBomb = 2;
    private int leftShuffle = 3;

    private MainActivity mainActivity;

    public LinkGameFragment() {
    }

    public static LinkGameFragment newInstance() {
        LinkGameFragment fragment = new LinkGameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_link_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        mainFragment = new MainFragment();
        if (((MainActivity) getActivity()).isGuide()) {
            v1 = getActivity().findViewById(R.id.newGame);
            v2 = getActivity().findViewById(R.id.shuffle);
            tool = getActivity().findViewById(R.id.bomb);

            // 添加新手指引蒙版
            NewbieGuide.with(this)
                    .setLabel("guide1")
                    .alwaysShow(true)//总是显示
                    .setOnGuideChangedListener(new OnGuideChangedListener() {
                        @Override
                        public void onShowed(Controller controller) {
                        }

                        @Override
                        public void onRemoved(Controller controller) {
                            shuffle();
                            leftShuffle++;
                        }
                    })
                    .addGuidePage(//添加引导页
                            GuidePage.newInstance()//创建一个实例
                                    .addHighLight(v1)//添加高亮的view
                                    .setLayoutRes(R.layout.guide_test)//设置引导页布局
                    )
                    .addGuidePage(//添加引导页
                            GuidePage.newInstance()
                                    .addHighLight(v2)
                                    .setLayoutRes(R.layout.guide_test2)
                    )
                    .addGuidePage(
                            GuidePage.newInstance()
                                    .addHighLight(tool)
                                    .setLayoutRes(R.layout.guide_test3)
                                    .setEverywhereCancelable(true)// 点击任意位置退出导引
                    )
                    .show();
        } else {
            init(view);
        }
    }

    // 初始化游戏
    private void init(@NonNull View view) {
        recyclerview = view.findViewById(R.id.recyclerview);

        bomb = view.findViewById(R.id.bomb);
        bomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bomb.setImageResource(R.drawable.bomb_selected);
                isBomb = true;
            }
        });

        loadData(getActivity().getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1));

        linkGameAdapter = new LinkGameAdapter(getActivity(), itemList, ExplosionField.attach2Window(getActivity()));
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), COLUMN));
        recyclerview.setAdapter(linkGameAdapter);
        linkGameAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                printMap();
                Item item = itemList.get(position);
                if (item.isEliminated()) return;

                item.setSelect(!item.isSelect());
                linkGameAdapter.setShuffle(false);
                linkGameAdapter.notifyItemChanged(position);

                if (isBomb) {
                    isBomb = false;
                    bomb.setImageResource(R.drawable.bomb);
                    if (leftBomb <= 0) {
                        Toast.makeText(mainActivity, "每局只能打两次响指！", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        bomb(itemList.get(position).getId());
                        Log.d("onclick", "return");
                        leftBomb--;
                        return;
                    }
                }
                if (lastClick != -1) {
                    eliminable(lastClick, position);
                } else {
                    lastClick = position;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    // 图片消去
    private void eliminable(int first, int second) {
        lastClick = -1;
        isMute = ((MainActivity) getActivity()).isMusicStop();

        //计算点击位置所在 行和列
        int rowOne = first / COLUMN;
        int columnOne = first - rowOne * COLUMN;

        int rowTwo = second / COLUMN;
        int columnTwo = second - rowTwo * COLUMN;

        int id1 = itemList.get(first).getId(), id2 = itemList.get(second).getId();

        Log.d("onclick", "id1:" + id1 + " id2:" + id2 + " rowOne:" + rowOne + " columnOne:" + columnOne
                + " rowTwo:" + rowTwo + " columnTwo:" + columnTwo);
        Pair<Integer, Integer> pointOne = new Pair<>(rowOne, columnOne);
        Pair<Integer, Integer> pointTwo = new Pair<>(rowTwo, columnTwo);

        if (itemList.get(first).getId() == itemList.get(second).getId() && Util.linkable(map, rowOne, columnOne, rowTwo, columnTwo)) {
            itemList.get(first).setEliminated(true);
            itemList.get(second).setEliminated(true);
            map[rowOne][columnOne] = 0;
            map[rowTwo][columnTwo] = 0;

            if (!isMute) {
                sound = MediaPlayer.create(getActivity(), R.raw.eliminate);
                sound.start();
            }
            judgeGameOver();
        } else {
            itemList.get(first).setSelect(false);
            itemList.get(second).setSelect(false);
            if (!isMute) {
                sound = MediaPlayer.create(getActivity(), R.raw.again);
                sound.start();
            }
        }
        linkGameAdapter.setShuffle(false);
        linkGameAdapter.notifyItemChanged(first);
        linkGameAdapter.notifyItemChanged(second);
    }

    // 游戏结算
    private void judgeGameOver() {
        if (isGameOver()) {
            endTime = System.currentTimeMillis();
            final long duration = endTime - startTime;

            new MaterialDialog.Builder(getActivity())
                    .title("添加新纪录(" + duration / 1000 + "s)")
                    .content("英雄请留下姓名!")
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .cancelable(false)
                    .input(0, 0, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            String userName = input.toString().trim();

                            if (TextUtils.isEmpty(userName))
                                userName = "匿名玩家";

                            Ranking ranking = new Ranking(userName,
                                    duration, getActivity().getSharedPreferences("linkgame",
                                    Context.MODE_PRIVATE).getInt("rank", 1), getTime());
                            App.getDaoSession().getRankingDao().insertOrReplace(ranking);
                            mainActivity.showMainFragment();
                        }
                    }).show();

        }
    }

    // 获取游戏时间
    private String getTime() {
        String pattern = "yyyy-MM-dd HH:mm";
        return new SimpleDateFormat(pattern).format(new Date());
    }

    // 设置连连看图片及提示道具信息
    public void loadData(int rank) {
        itemList.clear();
        lastClick = -1;
        startTime = System.currentTimeMillis();
        isBomb = false;
        leftBomb = 2;
        leftShuffle = 3;
        int totalAnimal = 10;
        if (rank == EASY) {
            totalAnimal = 10;
        } else if (rank == MEDIUM) {
            totalAnimal = 15;
        } else if (rank == DIFFICULTY) {
            totalAnimal = 25;
        }
        for (int i = 0; i < (COLUMN * ROW / 2); i++) {
            //每次加两个，保证是偶数
            Item item1 = new Item(i % totalAnimal + 1, false, false);
            Item item2 = new Item(i % totalAnimal + 1, false, false);
            itemList.add(item1);
            itemList.add(item2);
        }
        shuffle();
    }

    // 重置图片矩阵
    private void resetLinkGameMatrix() {
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            int row = i / COLUMN;
            int column = i - row * COLUMN;
            if (itemList.get(i).isEliminated()) {
                map[row][column] = 0;
            } else {
                map[row][column] = 1;
            }
        }
    }

    // 判断游戏是否结束
    private boolean isGameOver() {
        boolean gameOver = true;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (map[i][j] == 1) return false;
            }
        }
        return gameOver;
    }

    // 图片重排
    public void shuffle() {

        if (leftShuffle <= 0) {
            Toast.makeText(mainActivity, "每局只能重排2次！", Toast.LENGTH_LONG).show();
            return;
        }

        if (lastClick != -1) {
            itemList.get(lastClick).setSelect(false);
            lastClick = -1;
        }

        Collections.shuffle(itemList);
        resetLinkGameMatrix();
        if (linkGameAdapter != null) {
            linkGameAdapter.setShuffle(true);
            linkGameAdapter.notifyDataSetChanged();
        }
        leftShuffle--;
    }

    // 消去道具
    private void bomb(int id) {
        isMute = ((MainActivity) getActivity()).isMusicStop();
        if (!isMute) {
            sound = MediaPlayer.create(getActivity(), R.raw.bomb);
            sound.start();
        }
        linkGameAdapter.setShuffle(false);

        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getId() == id) {
                itemList.get(i).setEliminated(true);
                int rowOne = i / COLUMN;
                int columnOne = i - rowOne * COLUMN;
                map[rowOne][columnOne] = 0;
                linkGameAdapter.notifyItemChanged(i);
            }
        }
        judgeGameOver();
    }

    private void printMap() {
        for (int i = 0; i < ROW; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < COLUMN; j++) {
                sb.append(map[i][j] + " ");
            }
            System.out.println(sb.toString());
        }
        System.out.println("分割----------------分割\n\n");
    }

}
