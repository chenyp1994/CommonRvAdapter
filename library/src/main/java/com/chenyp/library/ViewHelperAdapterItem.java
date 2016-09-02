package com.chenyp.library;

import android.support.v7.widget.RecyclerView;

import com.chenyp.library.util.ViewHelper;

/**
 * Created by chenyp on 16/8/31.
 */

public abstract class ViewHelperAdapterItem<T> implements RvConvertViewHolder.AdapterItem<T> {

    private ViewHelper helper;
    protected RvConvertViewHolder holder;

    @Override
    public void initViewHolder(RecyclerView.ViewHolder holder) {
        this.helper = new ViewHelper(holder.itemView);
        this.holder = (RvConvertViewHolder) holder;
    }

    @Override
    public void convert(T t, int position) {
        convert(helper, t, position);
    }

    public abstract void convert(ViewHelper helper, T t, int position);

}
