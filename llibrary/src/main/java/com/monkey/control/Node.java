package com.monkey.control;

import android.util.SparseArray;

import com.monkey.interfaces.MuiltiInterface;

import java.util.TreeSet;

/**
 * Created by runmonkey on 16/7/16.
 */

public class Node {

    public Positions nodePosition;

    public Node(int...nodePosition){
        this.nodePosition = new Positions(nodePosition);
    }
    public int childCount;//孩子節點
    public SparseArray<Node> childNodes = new SparseArray<>();
    public TreeSet<Integer> expandChildren = new TreeSet<>();//展開的節點
    public Node fatherNode;//父節點


    public void setChildCount(MuiltiInterface mMuitilImpl){

        int count = mMuitilImpl.getCount(nodePosition);

        if(this.childCount > count){
            initChildrenNode(this.childCount,count);
        }
        this.childCount = count;
        for (Integer exChild:expandChildren) {//所有以及展開的節點，都重新獲取節點數量
            Node node = childNodes.get(exChild);
            if(node!=null){
                node.setChildCount(mMuitilImpl);
            }
        }
    }

    /**
     * 初始化子節點的信息
     * @param baforeCount
     * @param afterCount
     */
    private void initChildrenNode(int baforeCount,int afterCount) {
        if(baforeCount<=afterCount){
            return;
        }
        for (int i = afterCount; i < baforeCount; i++) {
            childNodes.remove(i);//移除多餘的節點
            expandChildren.remove(i);
        }
    }


    /**
     *
     * @return 返回該節點下，搜索的可見節點的數量
     */
    public int getAllVisibleCount(){
        int total = childCount;
        for (Integer exChild:expandChildren) {
            Node node = childNodes.get(exChild);
            if(node!=null){
                total+=node.getAllVisibleCount();
            }
        }

        return total;
    }

}
