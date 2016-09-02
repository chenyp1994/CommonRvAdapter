package com.chenyp.library;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenyp on 16/8/31.
 */

public interface BaseAdapterItem {

    void initViewHolder(RecyclerView.ViewHolder holder);

    @LayoutRes
    int getLayoutRes();

}
