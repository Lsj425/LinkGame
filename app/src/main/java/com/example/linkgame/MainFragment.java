package com.example.linkgame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainFragment extends Fragment {
    private Button start;
    private Button guide;
    private MainActivity mainActivity;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 开始游戏按钮点击事件响应
        start = view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setGuide(false);
                mainActivity.showLinkGameFragment();
                mainActivity.setOptionMenuOn(true);
                mainActivity.checkOptionMenu();
            }
        });

        // 新手指引按钮点击事件响应
        guide = view.findViewById(R.id.guide);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setGuide(true);
                mainActivity.showLinkGameFragment();
                mainActivity.setOptionMenuOn(true);
                mainActivity.checkOptionMenu();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
