package com.chenyp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenyp on 16/8/30.
 */

public class RvConvertViewHolder extends RecyclerView.ViewHolder {

    public AdapterItem item;

    public RvConvertViewHolder(View itemView, AdapterItem item, BaseCommonRvAdapter adapter) {
        super(itemView);
        this.item = item;
        item.initViewHolder(this);
        //noinspection unchecked
        item.bind(adapter);
    }

    public interface AdapterItem<T> extends BaseAdapterItem {

        void bind(BaseCommonRvAdapter<T> adapter);

        void convert(T t, int position);

    }

}
