package com.chenyp.library;

import java.util.List;

/**
 * Created by chenyp on 16/8/30.
 */

public abstract class BaseCommonRvHelperAdapter<T> extends BaseCommonRvAdapter<T> {

    public BaseCommonRvHelperAdapter(List<T> data) {
        super(data);
    }

    @Override
    protected RvConvertViewHolder.AdapterItem onCreateAdapterItem(int viewType) {
        return onCreateAdapterItemHelper(viewType);
    }

    protected abstract ViewHelperAdapterItem onCreateAdapterItemHelper(int viewType);

}
