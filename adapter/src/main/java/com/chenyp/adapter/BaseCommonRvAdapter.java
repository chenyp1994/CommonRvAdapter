package com.chenyp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chenyp.adapter.loadmore.LoadMoreRvViewHolder;
import com.chenyp.adapter.util.HFELHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyp on 16/8/16.
 */

public abstract class BaseCommonRvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0x00000111;
    public static final int LOAD_MORE = 0x00000222;
    public static final int FOOTER = 0x00000333;
    public static final int EMPTY = 0x00000555;

    protected List<T> mData;

    private HFELHelper helper;

    public BaseCommonRvAdapter(List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        helper = new HFELHelper(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEADER:
                holder = new SimpleViewHolder(helper.getHeaderLayout());
                break;
            case LOAD_MORE:
                LoadMoreRvViewHolder.AdapterItem loadMoreItem = helper.getLoadMoreItem();
                holder = new LoadMoreRvViewHolder(loadMoreItem.onCreateView(parent), loadMoreItem);
                break;
            case FOOTER:
                holder = new SimpleViewHolder(helper.getFooterLayout());
                break;
            case EMPTY:
                holder = new SimpleViewHolder(helper.getEmptyView());
                break;
            default:
                RvConvertViewHolder.AdapterItem item = onCreateAdapterItem(viewType);
                holder = new RvConvertViewHolder(item.onCreateView(parent), item);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RvConvertViewHolder) {
            RvConvertViewHolder holder = (RvConvertViewHolder) viewHolder;
            //noinspection unchecked
            holder.item.convert(getItem(position), position - helper.getHeaderLayoutCount());
        } else if (viewHolder instanceof LoadMoreRvViewHolder) {
            LoadMoreRvViewHolder holder = (LoadMoreRvViewHolder) viewHolder;
        } else if (viewHolder instanceof SimpleViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (helper.getHeaderLayout() != null && position == 0) {
            return HEADER;
        }
        if (mData.size() == 0 && helper.isEmptyEnable() && helper.getEmptyView() != null && position <= 2) {
            if (((helper.isHeadAndEmptyEnable() || helper.isFootAndEmptyEnable()) && position == 1)) {
                if (helper.getHeaderLayout() == null && helper.getFooterLayout() != null) {
                    return FOOTER;
                } else if (helper.getHeaderLayout() != null) {
                    return EMPTY;
                }
            } else if (position == 0) {
                if (helper.getHeaderLayout() == null) {
                    return EMPTY;
                } else if (helper.getFooterLayout() != null) {
                    return EMPTY;
                }
            }
        } else if (position == 2 && ((helper.isHeadAndEmptyEnable() || helper.isFootAndEmptyEnable())) && helper.getHeaderLayout() != null) {
            return FOOTER;
        } else if (position == mData.size() + getHelper().getHeaderLayoutCount()) {
            return helper.isLoadMoreEnable() ? LOAD_MORE : FOOTER;
        } else if (position > mData.size() + helper.getHeaderLayoutCount()) {
            return FOOTER;
        }
        return super.getItemViewType(position - helper.getHeaderLayoutCount());
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY || type == HEADER || type == FOOTER || type == LOAD_MORE) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(helper.getOnScrollListener());
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    boolean isOtherType = type == EMPTY || type == HEADER || type == FOOTER || type == LOAD_MORE;
                    if (helper.getSpanSizeLookup() == null) {
                        return isOtherType ? gridLayoutManager.getSpanCount() : 1;
                    } else {
                        return isOtherType ? gridLayoutManager.getSpanCount() :
                                helper.getSpanSizeLookup().getSpanSize(gridLayoutManager, position - helper.getHeaderLayoutCount());
                    }
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(helper.getOnScrollListener());
    }

    public int getItemCount() {
        int itemCount = helper.isLoadMoreEnable() ? 1 : 0;
        itemCount += mData.size() + helper.getHeaderLayoutCount() + helper.getFooterLayoutCount();
        if (mData.size() == 0 && helper.getEmptyView() != null) {
            if (itemCount == 0 && (!helper.isHeadAndEmptyEnable() || helper.isFootAndEmptyEnable())) {
                itemCount += helper.getEmptyViewCount();
            } else if (helper.isHeadAndEmptyEnable() || helper.isFootAndEmptyEnable()) {
                itemCount += helper.getEmptyViewCount();
            }

            if ((helper.isHeadAndEmptyEnable() && helper.getHeaderLayoutCount() == 1 && itemCount == 1) || itemCount == 0) {
                helper.setEmptyEnable(true);
                itemCount += helper.getEmptyViewCount();
            }
        }
        return itemCount;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getItem(int position) {
        return mData.get(position - helper.getHeaderLayoutCount());
    }

    public List<T> getData() {
        return mData;
    }

    public void add(T t) {
        add(mData.size() - 1, t);
    }

    public void add(int position, T t) {
        mData.add(position, t);
        notifyItemInserted(position);
    }

    public void remove(T t) {
        remove(mData.indexOf(t));
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(List<T> data) {
        addAll(mData.size() - 1, data);
    }

    public void addAll(int position, List<T> data) {
        mData.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    public void setData(@NonNull List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public HFELHelper getHelper() {
        return helper;
    }

    protected abstract RvConvertViewHolder.AdapterItem onCreateAdapterItem(int viewType);

}
