package com.chenyp.adapter.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenyp.adapter.R;


public class DefaultLoadMoreItem implements LoadMoreRvViewHolder.AdapterItem {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private LoadMoreRvViewHolder holder;
    private View.OnClickListener onClickListener;

    @Override
    public void initViewHolder(RecyclerView.ViewHolder holder) {
        this.holder = (LoadMoreRvViewHolder) holder;
        mTextView = (TextView) holder.itemView.findViewById(R.id.load_more_default_footer_text_view);
        mProgressBar = (ProgressBar) holder.itemView.findViewById(R.id.load_more_default_footer_progress_bar);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.load_more_default_footer;
    }

    @Override
    public void onLoading() {
        holder.itemView.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.load_more_loading);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinish(boolean empty, boolean hasMore) {
        if (!hasMore) {
            holder.itemView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
            if (empty) {
                mTextView.setText(R.string.load_more_loaded_empty);
            } else {
                mTextView.setText(R.string.load_more_loaded_no_more);
            }
        } else {
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore() {
        holder.itemView.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.load_more_click_to_load_more);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {
        mTextView.setText(R.string.load_more_error);
        mProgressBar.setVisibility(View.GONE);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
