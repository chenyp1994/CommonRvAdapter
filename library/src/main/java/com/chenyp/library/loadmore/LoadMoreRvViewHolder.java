package com.chenyp.library.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chenyp.library.BaseAdapterItem;

/**
 * Created by chenyp on 16/8/31.
 */

public class LoadMoreRvViewHolder extends RecyclerView.ViewHolder {

    protected AdapterItem item;

    public LoadMoreRvViewHolder(ViewGroup parent, AdapterItem item) {
        super(LayoutInflater.from(parent.getContext()).inflate(item.getLayoutRes(), parent, false));
        this.item = item;
        item.initViewHolder(this);
    }

    public interface AdapterItem extends BaseAdapterItem {

        void onLoading();

        void onLoadFinish(boolean empty, boolean hasMore);

        void onWaitToLoadMore();

        void onLoadError(int errorCode, String errorMessage);

    }

}
