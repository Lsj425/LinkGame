package com.example.linkgame.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerviewAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    protected List<T> mList;
    protected Context context;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerviewAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public BaseRecyclerviewAdapter(Context context, List<T> list) {
        this.context = context;
        this.mList = list;
    }


    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseHolder(LayoutInflater.from(parent.getContext()).inflate(getContentView(viewType), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, final int position) {
        onInitView(holder, getItem(position), position);

        if (null != mOnItemClickListener) { // 实现item点击事件回调
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }

        if (null != mOnItemLongClickListener) { // 实现item点击事件回调
            holder.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    public void setOnClickListener(BaseHolder holder, @IdRes int id, final int position) {
        if (null != mOnItemClickListener) { // 实现item点击事件回调
            holder.getView(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setOnLongClickListener(BaseHolder holder, @IdRes int id, final int position) {
        if (null != mOnItemLongClickListener) { // 实现item点击事件回调
            holder.getView(id).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract int getContentView(int viewType);

    public abstract void onInitView(BaseHolder holder, T object, int position);

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (position >= 0 && mList.size() > position) {
            mList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mList.size() - position);
        }
    }

    public void add(int position, T object) {
        if (object != null) {
            mList.add(position, object);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mList.size() - position);
        }
    }

    public void addLast(T object) {
        if (object != null) {
            this.mList.add(object);
            notifyItemInserted(mList.size());
        }
    }

    public void addHead(T object) {
        if (object != null) {
            this.mList.add(0, object);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public T getItem(int position) {
        return mList.get(position);
    }

}
