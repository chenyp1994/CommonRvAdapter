package com.chenyp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenyp on 16/8/30.
 */

public class RvConvertViewHolder extends RecyclerView.ViewHolder {

    public AdapterItem item;

    public RvConvertViewHolder(View itemView, AdapterItem item) {
        super(itemView);
        this.item = item;
        item.initViewHolder(this);
    }

    public interface AdapterItem<T> extends BaseAdapterItem {

        void convert(T t, int position);

    }

}
