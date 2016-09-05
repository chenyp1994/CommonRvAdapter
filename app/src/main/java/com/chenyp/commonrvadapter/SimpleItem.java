package com.chenyp.commonrvadapter;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenyp.adapter.RvConvertViewHolder;

/**
 * Created by chenyp on 16/9/5.
 */

public abstract class SimpleItem<T> implements RvConvertViewHolder.AdapterItem<T> {

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
    }

    @LayoutRes
    public abstract int getLayoutRes();

}
