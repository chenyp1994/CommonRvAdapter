package com.chenyp.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chenyp on 16/8/31.
 */

public interface BaseAdapterItem {

    void initViewHolder(RecyclerView.ViewHolder holder);

    @LayoutRes
    int getLayoutRes();

}
