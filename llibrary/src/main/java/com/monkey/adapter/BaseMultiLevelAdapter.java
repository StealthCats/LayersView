package com.monkey.adapter;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by RnMonkey on 16-5-21.
 */
public abstract class BaseMultiLevelAdapter extends BaseAdapter implements View.OnClickListener {

    private static final String TAG = "BaseMultiLevelAdapter";
    private static final int CACHE_COUNT = 30;
    private Node mRoot;//跟節點
    private int lastPosition =0;
    private HashMap<Integer,int[]> postionCache = new HashMap<>();//位置緩存

    public BaseMultiLevelAdapter(){
        mRoot = new Node();
        mRoot.setChildrenCount(getCountByPosition());//設置根節點
    }

    /**
     * 获取对应位置条目展开时的数量
     * @param position 位置信息，etc：position 没有值时，获取第一层的数量， 当position = 2 时，获取的是第一层的第2条展开时的数量，当positoin = 2,3 时，则获取的是第一层第2条展开后的第3条的展开后的数量，以此类推
     * @return
     */
    protected abstract int getCountByPosition(int ... position);

    /**
     * 获取对应条目对应的View @link:getCountByPosition
     * @param convertView
     * @param parent
     * @param position
     * @return
     */
    protected abstract View getViewByPosition(View convertView, ViewGroup parent,int ... position);

    @Override
    public int getCount() {
        return mRoot.getAllVisibleCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        Object position = v.getTag();
        if(position instanceof Integer){
            int[] pos = getPosByPostion((Integer) position);
            if(handleItemClick(v,pos)){
                return;
            }
            expandGroup(pos);
        }
    }

    /**
     * 对应节点是否展开
     * @param pos
     * @return
     */
    public boolean isNodeExpand(int...pos){
        try {
            Node node = getNodeByPosition(pos);
            if (node.fatherNode.expandChildren.contains(pos[pos.length - 1])) {
                return true;
            }
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }
    /**
     * 处理条目点击，返回false时，默认执行展开操作
     * @param v
     * @param pos
     * @return
     */
    public boolean handleItemClick(View v,int...pos){
        return false;
    };

    public void expandGroup(int...position) {
//        Node node = (Node) view.getTag();
        Node node = getNodeByPosition(position);
        int nodePositionInParent = node.nodePosition[node.nodePosition.length-1];
        boolean isExpand = node.fatherNode.expandChildren.contains(nodePositionInParent);//當前節點是否是展開
//        Log.d(TAG, "onItemClick: isExpand ="+isExpand +" nodePositionInParent ="+nodePositionInParent);
        if(isExpand){
            node.fatherNode.expandChildren.remove(nodePositionInParent);
        }else {
            int childrenCount = getCountByPosition(node.nodePosition);
            if(childrenCount==0){
                Log.d(TAG, "onItemClick: this node hasn't children");
                return;//沒有字節點
            }
            node.setChildrenCount(childrenCount);
            node.fatherNode.expandChildren.add(nodePositionInParent);//父節點標志當前是展開狀態
        }

        postionCache.clear();
        super.notifyDataSetChanged();
    }

    private int[] getPosByPostion(int position){
        int[] pos = postionCache.get(position);
        if(pos==null||pos.length==0){
            refreshPositionCache(lastPosition,position);
            pos = postionCache.get(position);
        }
        lastPosition = position;
        return pos;
    }
    /**
     * 返回position對應的節點
     * @param pos
     * @return
     */
    private Node getNodeByPosition(int...pos){
//        int[] pos = getPosByPostion(position);
        Node node = mRoot;
        Node fatherNode =null;
        for (int i = 0; i < pos.length; i++) {
            if(i==pos.length-1){
                fatherNode = node;
            }
            node = node.childrenNode.get(pos[i]);
        }
        if(node==null){
            node = new Node(pos);
            node.fatherNode = fatherNode;
            node.fatherNode.childrenNode.put(pos[pos.length-1],node);
        }
        return node;
    }

    private int[] translate(int[] first,int...second){

        int[] newInt = new int[first.length+second.length];
        for (int i = 0; i < newInt.length; i++) {
            if(i<first.length){
                newInt[i] = first[i];
            }else {
                newInt[i] = second[i-first.length];
            }
        }
        return newInt;
    }

    public int refreshPositoin(int curPostion,int statPosition,int endPostion,Node node){
            if(curPostion>endPostion){
                return curPostion;
            }
        for (int i = 0; i < node.childrenCount; i++) {
            if(curPostion>=statPosition&&curPostion<=endPostion){
//                String s = toStrings(translate(node.nodePosition, i));
//                Log.d(TAG, "refreshPositoin: position ="+curPostion+" pos ="+s);
                postionCache.put(curPostion,translate(node.nodePosition,i));
            }
            curPostion++;
            if(curPostion>endPostion){
                return curPostion;
            }
            if(node.expandChildren.contains(i)){
                curPostion = refreshPositoin(curPostion,statPosition,endPostion,node.childrenNode.get(i));
            }
        }
        return curPostion;
    }

    /**
     * 刷新20條位置信息
     * @param lastPosition
     * @param position
     */
    private void refreshPositionCache(int lastPosition, int position) {
        if(postionCache.containsKey(position)){
            return;
        }
        int start = 0;
        int end = 0;
        if(position-lastPosition>=0){
            start = position;
            end = Math.min(getCount(),position+CACHE_COUNT);
        }else {
            start = Math.max(0,position-CACHE_COUNT);
            end = position;
        }
        refreshPositoin(0,start,end,mRoot);//刷新緩存
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getViewByPosition(convertView, parent, getPosByPostion(position));
        view.setTag(position);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        mRoot.setChildrenCount(getCountByPosition());//設置根節點
        postionCache.clear();
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mRoot.setChildrenCount(getCountByPosition());//設置根節點
        postionCache.clear();
        super.notifyDataSetInvalidated();
    }

    private class Node{

        public Node(int...nodePosition){
        this.nodePosition = nodePosition;
        }
        public int[] nodePosition;
        private int childrenCount;//孩子節點
        public SparseArray<Node> childrenNode = new SparseArray<>();
        public TreeSet<Integer> expandChildren = new TreeSet<>();//展開的節點
        public Node fatherNode;//父節點
        public void setChildrenCount(int childrenCount){

            if(this.childrenCount > childrenCount){
                initChildrenNode(this.childrenCount,childrenCount);
            }
            this.childrenCount = childrenCount;
            for (Integer exChild:expandChildren) {//所有以及展開的節點，都重新獲取節點數量
                Node node = childrenNode.get(exChild);
                if(node!=null){
                    node.setChildrenCount(getCountByPosition(node.nodePosition));
                }
            }
        }

        /**
         *
         * @return 返回該節點下，搜索的可見節點的數量
         */
        public int getAllVisibleCount(){
            int total = childrenCount;
            for (Integer exChild:expandChildren) {
                Node node = childrenNode.get(exChild);
                if(node!=null){
                    total+=node.getAllVisibleCount();
                }
            }

            return total;
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
                childrenNode.remove(i);//移除多餘的節點
                expandChildren.remove(i);
            }
        }
    }
}
