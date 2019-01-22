package com.example.linkgame.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class BaseHolder extends RecyclerView.ViewHolder {
    private View convertView;

    public BaseHolder(View itemView) {
        super(itemView);
        convertView = itemView;
    }

    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    public View getView() {
        return convertView;
    }

    protected <T extends View> T retrieveView(int viewId) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(viewId);
        if (null == childView) {
            childView = convertView.findViewById(viewId);
            viewHolder.put(viewId, childView);
        }
        return (T) childView;
    }

    public void setText(int viewId, String content) {
        TextView textView = (TextView) retrieveView(viewId);
        textView.setText(content);
    }

    public TextView getTextView(@IdRes int id) {
        return (TextView) getView(id);
    }

}
