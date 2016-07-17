package com.monkey.multilistview;

import android.app.ListActivity;
import android.os.Bundle;

import com.monkey.multilistview.adapter.MultilListViewAdapter;

/**
 * Created by runmonkey on 16/7/17.
 */

public class ListViewActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListView().setAdapter(new MultilListViewAdapter());
    }
}
