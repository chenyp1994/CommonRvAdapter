package com.chenyp.commonrvadapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenyp.adapter.BaseCommonRvAdapter;
import com.chenyp.adapter.RvConvertViewHolder;

/**
 * Created by chenyp on 16/9/5.
 */

public abstract class BaseRvAdapterItem<T> implements RvConvertViewHolder.AdapterItem<T> {

    protected BaseCommonRvAdapter<T> adapter;

    protected RecyclerView.ViewHolder holder;

    @NonNull
    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
    }

    @LayoutRes
    public abstract int getLayoutRes();

    @Override
    public void initViewHolder(RecyclerView.ViewHolder holder) {
        this.holder = holder;
    }

    @Override
    public void bind(BaseCommonRvAdapter<T> adapter) {
        this.adapter = adapter;
    }
}
