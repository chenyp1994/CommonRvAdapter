package com.chenyp.library;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by chenyp on 16/8/30.
 */

public class RvConvertViewHolder extends RecyclerView.ViewHolder {

    public AdapterItem item;

    public RvConvertViewHolder(ViewGroup parent, AdapterItem item) {
        super(LayoutInflater.from(parent.getContext()).inflate(item.getLayoutRes(), parent, false));
        this.item = item;
        item.initViewHolder(this);
    }

    public interface AdapterItem<T> extends BaseAdapterItem {

        void convert(T t, int position);

    }

}
