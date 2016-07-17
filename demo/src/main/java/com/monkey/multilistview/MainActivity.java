package com.monkey.multilistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ((ListView)findViewById(R.id.listview)).setAdapter(new MultilListViewAdapter());
        findViewById(R.id.btn_listview).setOnClickListener(this);
        findViewById(R.id.btn_recycleview).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_listview:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
            case R.id.btn_recycleview:
                startActivity(new Intent(this, RecycleViewActivity.class));
                break;
        }
    }
}
