package com.chenyp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenyp on 16/8/31.
 */

public interface BaseAdapterItem {

    void initViewHolder(RecyclerView.ViewHolder holder);

    View onCreateView(ViewGroup parent);

}
