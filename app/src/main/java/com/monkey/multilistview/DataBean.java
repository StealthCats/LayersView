package com.monkey.multilistview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 16-5-22.
 */
public class DataBean {

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
