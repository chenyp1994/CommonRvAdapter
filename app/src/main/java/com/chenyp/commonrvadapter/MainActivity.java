package com.chenyp.commonrvadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chenyp.adapter.BaseCommonRvAdapter;
import com.chenyp.adapter.RvConvertViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("测试" + i);
        }
        BaseCommonRvAdapter adapter = new BaseCommonRvAdapter<String>(data) {

            @Override
            protected RvConvertViewHolder.AdapterItem onCreateAdapterItem(int viewType) {
                return new TextItem();
            }

        };
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.load_more_default_footer, null, false);
            TextView textView = (TextView) view.findViewById(R.id.load_more_default_footer_text_view);
            textView.setText(textView.getText().toString() + i);
            if (i == 1) {
                adapter.getHelper().addHeaderView(view, 0);
            } else {
                adapter.getHelper().addHeaderView(view);
            }
        }
        for (int i = 2; i >= 0; i--) {
            View view = LayoutInflater.from(this).inflate(R.layout.load_more_default_footer, null, false);
            TextView textView = (TextView) view.findViewById(R.id.load_more_default_footer_text_view);
            textView.setText(textView.getText().toString() + i);
            if (i == 1) {
                adapter.getHelper().addFooterView(view, 0);
            } else {
                adapter.getHelper().addFooterView(view);
            }
        }
        recyclerView.setAdapter(adapter);
    }

    private class TextItem extends SimpleItem<String> {

        private TextView text;

        @Override
        public void convert(String string, int position) {
            text.setText(string);
        }

        @Override
        public void initViewHolder(RecyclerView.ViewHolder holder) {
            text = (TextView) holder.itemView.findViewById(R.id.text);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.rv_item_test;
        }
    }
}

