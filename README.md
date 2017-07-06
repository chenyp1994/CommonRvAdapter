# CommonRvAdapter
CommonRvAdapter

Usage
-----
  ```
  compile "com.chenyp.adapter:adapter:1.0.8"
  ```
  
* Get your AdapterItem
 ```java
  private class TextItem implements RvConvertViewHolder.AdapterItem<String> {

        private TextView text;

        @Override
        public void convert(String string, int position) {
            text.setText(string);
        }

        @Override
        public void initViewHolder(RecyclerView.ViewHolder holder) {
            text = (TextView) holder.itemView.findViewById(R.id.text);
        }

        @NonNull
        @Override
        public View onCreateView(ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
        }
    }
 ```
* Set your Adapter
 ```java
  recyclerView.setAdapter(new BaseCommonRvAdapter<String>(data) {

            @Override
            protected RvConvertViewHolder.AdapterItem onCreateAdapterItem(int viewType) {
                return new TextItem();
            }

        });
        
        recyclerView.setAdapter(new BaseCommonRvAdapter<String>(data) {

            @Override
            protected RvConvertViewHolder.AdapterItem onCreateAdapterItem(int viewType) {
                if(ITEM_TYPE_2){
                    return new TextItem2()
                }
                return new TextItem();
            }
            
            public int getItemViewType(int position) {
                if(condition){
                    return ITEM_TYPE_2
                }
                return ITEM_TYPE_1
            }

        })
        
        adapter.getHelper().addHeaderView();
        adapter.getHelper().addFooterView();
        adapter.getHelper().setEmptyView();
        adapter.getHelper().setOnLoadMoreUIReachListener();
        adapter.getHelper().setOnLoadMoreListener();
  ```
