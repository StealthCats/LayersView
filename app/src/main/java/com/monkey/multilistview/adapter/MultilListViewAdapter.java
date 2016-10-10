package com.monkey.multilistview.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monkey.adapter.BaseListViewAdapter;
import com.monkey.control.Positions;
import com.monkey.multilistview.DataBean;

/**
 * Created by monkey on 16-5-22.
 */
public class MultilListViewAdapter extends BaseListViewAdapter {

    int[] color = new int[]{Color.BLACK,Color.WHITE,Color.RED,Color.CYAN,Color.BLUE,Color.GREEN,Color.BLACK};

    private DataBean mRoot;

    public MultilListViewAdapter(){
        initData();
    }
    private void initData() {
        mRoot = new DataBean("  ");
        mRoot.initData(mRoot,0,5);
    }

    @Override
    public int getCount(Positions position) {
        if(mRoot==null){
           initData();
        }
        DataBean data = mRoot;
        for (int i = 0; i < position.length(); i++) {
            data = data.getChildren().get(position.getIntAt(i));
        }
        return data.getChildren().size();
    }

    @Override
    public View getView(View convertView, ViewGroup parent, Positions position) {
        TextView tv = null;
        if(convertView!=null&&convertView instanceof TextView){
            tv = (TextView) convertView;
        }else {
            tv=  new TextView(parent.getContext());
            tv.setPadding(5,10,0,10);
        }
        Log.d("TAG", "getView: position"+position);
        tv.setBackgroundColor(color[position.length()-1]);
        tv.setTextColor(color[position.length()]);
        DataBean data = mRoot;
        for (int i = 0; i < position.length(); i++) {
            data = data.getChildren().get(position.getIntAt(i));
        }
        tv.setText(toStrings(position)+" data title ="+data.getDataTitle());
        return tv;
    }



    public String toStrings(Positions position) {
        return "第"+position.length()+"层："+position.toString();
    }
}
