package com.monkey.multilistview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 16-5-22.
 */
public class DataBean {

    private final String TAG=this.getClass().getSimpleName();
    private String dataTitle;
    private List<DataBean> children;

    public DataBean(String dataTitle){
     this.dataTitle = dataTitle;
    }


    public String getDataTitle() {
        if(dataTitle==null){
            return "";
        }
        return dataTitle;
    }

    public void initData(DataBean father,int level,int count){
        if(level >=4){
            return;
        }
        DataBean data;
        List<DataBean> childrenList = new ArrayList<>();
        for (int i = 0; i < count; i++) {//第一层的数据
            data = new DataBean("第--"+level+"层 第 "+i+"个 ");
            initData(data,level+1,count+1);
            childrenList.add(data);
        }
        father.setChildren(childrenList);
        if(level==0){
            Log.d(TAG, "initData: father ="+father);
        }
    }

    public void setDataTitle(String dataTitle) {

        this.dataTitle = dataTitle;
    }

    public List<DataBean> getChildren() {
        if(children==null){
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<DataBean> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "dataTitle='" + dataTitle + '\'' +
                ", children=" + children +
                '}';
    }
}
