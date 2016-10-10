package com.monkey.control;

import android.util.Log;

import com.monkey.interfaces.MuiltiInterface;

import java.util.HashMap;

/**
 * Created by runmonkey on 16/7/16.
 */

public class BaseMuitiControl {

    private final String TAG = this.getClass().getName();

    private Node mRoot;
    private MuiltiInterface mMuitilImpl;

    private static final int CACHE_COUNT = 30;

    private HashMap<Integer, Positions> postionCache = new HashMap<>(CACHE_COUNT * 2);//位置緩存

    public BaseMuitiControl(MuiltiInterface mMuitilImpl) {
        this.mRoot = new Node();
        this.mMuitilImpl = mMuitilImpl;
        this.mRoot.setChildCount(mMuitilImpl);
    }

    public int getCount() {
        return mRoot.getAllVisibleCount();
    }

    /**
     * 展开对应条目
     *
     * @param position
     */
    public void onItemClick(Positions position) {
        if (position == null) {
            return;
        }

        Node node = getNode(position);
        int posInParent = position.lastInt();
        boolean isExpand = node.fatherNode.expandChildren.contains(posInParent);//當前節點是否是展開
        if (isExpand) {
            node.fatherNode.expandChildren.remove(posInParent);
        } else {
            node.setChildCount(mMuitilImpl);
            if (node.getAllVisibleCount() == 0) {
                return;
            }
            node.fatherNode.expandChildren.add(posInParent);//父節點標志當前是展開狀態
        }
        postionCache.clear();
        mMuitilImpl.notifyChange();
    }

    /**
     * 返回position對應的節點
     *
     * @param pos
     * @return
     */
    private Node getNode(Positions pos) {
        Node node = mRoot;
        Node fatherNode = null;

        Positions.InnerIterator iterator = pos.iterator();
        while (iterator.hasNext()) {
            fatherNode = node;
            node = node.childNodes.get(iterator.next());
        }

        if (node == null) {
            node = new Node(pos.toPos());
            node.fatherNode = fatherNode;
            node.fatherNode.childNodes.put(pos.lastInt(), node);
        }
        return node;
    }


    public Positions getPosition(int position) {
        if (!postionCache.containsKey(position)) {
            updateCache(position);
        }
        lastPosition = position;
        return postionCache.get(position);
    }


    private int lastPosition = 0;

    /**
     * 更新缓存
     *
     * @param position
     */
    private void updateCache(int position) {

        if (postionCache.containsKey(position)) {
            return;
        }
        int start = 0;
        int end = 0;
        if (position - lastPosition >= 0) {
            start = position;
            end = Math.min(mRoot.getAllVisibleCount(), position + CACHE_COUNT);
        } else {
            start = Math.max(0, position - CACHE_COUNT);
            end = position;
        }
        Log.d(TAG, "updateCache: start = " + start + " end = " + end);
        refreshPositoin(0, start, end, mRoot);//刷新緩存

    }


    private int refreshPositoin(int curPostion, int statPosition, int endPostion, Node node) {
        if (curPostion > endPostion) {
            return curPostion;
        }

        Positions child;
        for (int i = 0; i < node.childCount; i++) {
            if (curPostion >= statPosition && curPostion <= endPostion) {

                child = node.nodePosition.createChild(i);
                Log.d(TAG, "refreshPositoin: curPostion = " + curPostion + " child =" + child);
                postionCache.put(curPostion, child);
            }
            curPostion++;
            if (curPostion > endPostion) {
                return curPostion;
            }
            if (node.expandChildren.contains(i)) {
                curPostion = refreshPositoin(curPostion, statPosition, endPostion, node.childNodes.get(i));
            }
        }
        return curPostion;
    }

}
