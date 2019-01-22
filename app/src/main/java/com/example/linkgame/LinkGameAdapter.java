package com.example.linkgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.example.linkgame.base.BaseHolder;
import com.example.linkgame.base.BaseRecyclerviewAdapter;
import com.example.linkgame.bean.Item;

import java.io.IOException;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

public class LinkGameAdapter extends BaseRecyclerviewAdapter<Item> {
    private ExplosionField explosionField;
    private boolean shuffle = false;

    public LinkGameAdapter(Context context, List<Item> list, ExplosionField explosionField) {
        super(context, list);
        this.explosionField = explosionField;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    @Override
    public int getContentView(int viewType) {
        return R.layout.item_link_game;
    }

    @Override
    public void onInitView(final BaseHolder holder, Item object, int position) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("image/" + mList.get(position).getId() + ".png"));
            ImageView imageView = ((ImageView) holder.getView(R.id.image));
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (object.isSelect()) {
            holder.getView(R.id.select).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.select).setVisibility(View.GONE);
        }
        if (object.isEliminated()) {
            final ImageView imageView = ((ImageView) holder.getView(R.id.image));
            holder.getView(R.id.select).setVisibility(View.GONE);
            if (!shuffle) {
                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        explosionField.explode(imageView);
                        imageView.setVisibility(View.GONE);
                    }
                }, 10);
            } else {
                imageView.setVisibility(View.GONE);
            }
        } else {
            holder.getView(R.id.image).setVisibility(View.VISIBLE);
        }
    }
}
