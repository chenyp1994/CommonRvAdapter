package com.chenyp.adapter.util;

/**
 * Copyright 2016 陈宇明
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.chenyp.adapter.loadmore.DefaultLoadMoreItem;
import com.chenyp.adapter.loadmore.LoadMoreRvViewHolder;
import com.chenyp.adapter.loadmore.OnLoadMoreListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by chenyp on 16/8/30.
 */

public class HFELHelper {

    private boolean mEmptyEnable;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;

    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private LinearLayout mCopyHeaderLayout = null;
    private LinearLayout mCopyFooterLayout = null;

    /**
     * View to show if there are no items to show.
     */
    private View mEmptyView;
    private View mCopyEmptyLayout;

    private RecyclerView.Adapter adapter;
    private SpanSizeLookup mSpanSizeLookup;

    // LoadMore
    private boolean mLoadMoreEnable = true;
    private boolean mIsLoading = false;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;

    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;

    private LoadMoreRvViewHolder.AdapterItem mLoadMoreItem;
    private OnLoadMoreListener mOnLoadMoreListener;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        private boolean mIsEnd = false;

        private int[] firstPositions;

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mIsEnd) {
                    onReachBottom();
                }
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount = 0;
            int totalItemCount = 0;
            int firstVisibleItem = 0;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mStaggeredGridLayoutManager.getItemCount();
                if (firstPositions == null) {
                    firstPositions = new int[mStaggeredGridLayoutManager.getSpanCount()];
                }
                firstVisibleItem = findMax(firstPositions);
                mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstPositions);
            }
            //监听是否滑动到底部
            mIsEnd = firstVisibleItem + visibleItemCount >= totalItemCount - 1;
        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }

    };

    public HFELHelper(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public int getHeaderLayoutCount() {
        return mHeaderLayout != null ? 1 : 0;
    }

    public int getFooterLayoutCount() {
        return mFooterLayout != null ? 1 : 0;
    }

    public int getEmptyViewCount() {
        return mEmptyView != null ? 1 : 0;
    }

    public void addHeaderView(View headerView) {
        addHeaderView(headerView, -1);
    }

    public void addHeaderView(View headerView, int index) {
        if (headerView == null) {
            return;
        }
        if (mHeaderLayout == null) {
            if (mCopyHeaderLayout == null) {
                mHeaderLayout = new LinearLayout(headerView.getContext());
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                mCopyHeaderLayout = mHeaderLayout;
            } else {
                mHeaderLayout = mCopyHeaderLayout;
            }
        }
        index = index > mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(headerView, index);
        adapter.notifyDataSetChanged();
    }

    public void removeHeaderView(View headerView) {
        if (mHeaderLayout == null || headerView == null) {
            return;
        }
        mHeaderLayout.removeView(headerView);
        if (mHeaderLayout.getChildCount() == 0) {
            mHeaderLayout = null;
        }
        adapter.notifyDataSetChanged();
    }

    public void removeAllHeaderView() {
        if (mHeaderLayout == null) {
            return;
        }
        mHeaderLayout.removeAllViews();
        mHeaderLayout = null;
    }

    public void addFooterView(View footerView) {
        addFooterView(footerView, -1);
    }

    public void addFooterView(View footerView, int index) {
        if (footerView == null) {
            return;
        }
        if (mFooterLayout == null) {
            if (mCopyFooterLayout == null) {
                mFooterLayout = new LinearLayout(footerView.getContext());
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                mCopyFooterLayout = mFooterLayout;
            } else {
                mFooterLayout = mCopyFooterLayout;
            }
        }
        index = index > mFooterLayout.getChildCount() ? -1 : index;
        mFooterLayout.addView(footerView, index);
        adapter.notifyDataSetChanged();
    }

    public void removeFooterView(View footerView) {
        if (mFooterLayout == null || footerView == null) {
            return;
        }
        mFooterLayout.removeView(footerView);
        if (mFooterLayout.getChildCount() == 0) {
            mFooterLayout = null;
        }
        adapter.notifyDataSetChanged();
    }

    public void removeAllFooterView() {
        if (mFooterLayout == null) {
            return;
        }
        mFooterLayout.removeAllViews();
        mFooterLayout = null;
    }

    public void setEmptyView(View emptyView) {
        setEmptyView(false, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
        mEmptyView = emptyView;
        if (mCopyEmptyLayout == null) {
            mCopyEmptyLayout = emptyView;
        }
        mEmptyEnable = true;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public LinearLayout getFooterLayout() {
        return mFooterLayout;
    }

    public LinearLayout getHeaderLayout() {
        return mHeaderLayout;
    }

    public boolean isEmptyEnable() {
        return mEmptyEnable;
    }

    public void setEmptyEnable(boolean emptyEnable) {
        this.mEmptyEnable = emptyEnable;
    }

    public boolean isHeadAndEmptyEnable() {
        return mHeadAndEmptyEnable;
    }

    public void setHeadAndEmptyEnable(boolean headAndEmptyEnable) {
        this.mHeadAndEmptyEnable = headAndEmptyEnable;
    }

    public boolean isFootAndEmptyEnable() {
        return mFootAndEmptyEnable;
    }

    public void setFootAndEmptyEnable(boolean footAndEmptyEnable) {
        this.mFootAndEmptyEnable = footAndEmptyEnable;
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public LoadMoreRvViewHolder.AdapterItem getLoadMoreItem() {
        if (mLoadMoreItem == null) {
            DefaultLoadMoreItem defaultLoadMoreItem = new DefaultLoadMoreItem();
            defaultLoadMoreItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tryToPerformLoadMore();
                }
            });
            mShowLoadingForFirstPage = true;
            mLoadMoreItem = defaultLoadMoreItem;
        }
        return mLoadMoreItem;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    private void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreItem.onWaitToLoadMore();
            }
        }
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }
        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        if (mLoadMoreItem != null) {
            mLoadMoreItem.onLoading();
        }
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setShowLoadingForFirstPage(boolean showLoading) {
        this.mShowLoadingForFirstPage = showLoading;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        this.mAutoLoadMore = autoLoadMore;
    }

    public void setOnLoadMoreUIReachListener(LoadMoreRvViewHolder.AdapterItem loadMoreItem) {
        this.mLoadMoreItem = loadMoreItem;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreItem != null) {
            mLoadMoreItem.onLoadFinish(emptyResult, hasMore);
        }
    }

    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreItem != null) {
            mLoadMoreItem.onLoadError(errorCode, errorMessage);
        }
    }

    public boolean isLoadMoreEnable() {
        return mLoadMoreEnable;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.mLoadMoreEnable = loadMoreEnable;
    }

}
