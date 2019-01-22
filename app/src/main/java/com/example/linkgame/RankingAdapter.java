package com.example.linkgame;

import android.content.Context;

import com.example.linkgame.base.BaseHolder;
import com.example.linkgame.base.BaseRecyclerviewAdapter;
import com.example.linkgame.bean.Ranking;

import java.util.List;

public class RankingAdapter extends BaseRecyclerviewAdapter<Ranking> {
    public RankingAdapter(Context context, List<Ranking> list) {
        super(context, list);
    }

    @Override
    public int getContentView(int viewType) {
        return R.layout.item_ranking;
    }

    @Override
    public void onInitView(BaseHolder holder, Ranking object, int position) {
        position++;
        holder.setText(R.id.id, position + "");
        holder.setText(R.id.userName, object.getUserName());
        holder.setText(R.id.record, object.getRecord() / 1000 + "s");
        holder.setText(R.id.date, object.getDate());
        String type = "Easy";
        switch (object.getType()) {
            case LinkGameFragment.EASY:
                type = "简单";
                break;
            case LinkGameFragment.MEDIUM:
                type = "正常";
                break;
            case LinkGameFragment.DIFFICULTY:
                type = "困难";
                break;
        }
        holder.setText(R.id.type, type);
    }


}
