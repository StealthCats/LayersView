package com.monkey.multilistview.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monkey.multilistview.DataBean;
import com.monkey.adapter.BaseMultiLevelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 16-5-22.
 */
public class MultiLevelAdapter extends BaseMultiLevelAdapter {

    int[] color = new int[]{Color.BLACK,Color.WHITE,Color.RED,Color.CYAN,Color.BLUE,Color.GREEN,Color.BLACK};

    private DataBean mRoot;

    public MultiLevelAdapter(){
        initData();
    }
    private void initData() {
        mRoot = new DataBean("  ");
        createChildrenDataBean(mRoot,0,6);
        Log.d("", "initData: mRoot ="+mRoot);
    }

    private void createChildrenDataBean(DataBean father,int level,int count){
        if(level >=5){
            return;
        }
        DataBean data =null;
        List<DataBean> childrenList = new ArrayList<>();
        for (int i = 0; i < count; i++) {//第一层的数据
            data = new DataBean("第--"+level+"层 第 "+i+"个 ");
            createChildrenDataBean(data,level+1,count+1);
            childrenList.add(data);
        }
        father.setChildren(childrenList);
    }

    @Override
    protected int getCountByPosition(int... position) {
        if(mRoot==null){
           initData();
        }
        DataBean data = mRoot;
        for (int i = 0; i < position.length; i++) {
            data = data.getChildren().get(position[i]);
        }
        return data.getChildren().size();
    }

    @Override
    protected View getViewByPosition(View convertView, ViewGroup parent, int... position) {
        TextView tv = null;
        if(convertView!=null&&convertView instanceof TextView){
            tv = (TextView) convertView;
        }else {
            tv=  new TextView(parent.getContext());
            tv.setPadding(5,10,0,10);
        }
        tv.setBackgroundColor(color[position.length-1]);
        tv.setTextColor(color[position.length]);
        DataBean data = mRoot;
        for (int i = 0; i < position.length; i++) {
            data = data.getChildren().get(position[i]);
        }
        tv.setText(PostoStrings(position)+" data title ="+data.getDataTitle());
        return tv;
    }



    public String PostoStrings(int... position) {
        String str = "第"+position.length+"层：";
        String temp = " ";
        for (int i = 0; i < position.length; i++) {
            temp += "| -";
            str += " -" + position[i];
        }
        return temp+str;
    }
}
