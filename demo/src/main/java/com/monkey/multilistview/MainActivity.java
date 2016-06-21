package com.monkey.multilistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.monkey.multilistview.adapter.MultiLevelAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listview);
        MultiLevelAdapter adapter = new MultiLevelAdapter();
        mListView.setAdapter(adapter);
    }
}
