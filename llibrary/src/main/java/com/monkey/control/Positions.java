package com.monkey.control;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by runmonkey on 16/7/16.
 * 位置信息,记录每一个节点的位置
 */

public class Positions {

    private int[] mPosition;

    public Positions(int... positions) {
        this.mPosition = positions;
        if (this.mPosition == null) {
            this.mPosition = new int[0];
        }
    }

    public int[] toPos() {
        return mPosition;
    }

    public int lastInt() {

        return getIntAt(mPosition.length-1);
    }

    public int getIntAt(int s) {
        if (s >= mPosition.length) {
            return -1;
        }
        return mPosition[s];
    }

    public int length() {
        return mPosition.length;
    }

    public Positions createChild(int s) {
        int[] newInt = new int[mPosition.length + 1];
        System.arraycopy(mPosition, 0, newInt, 0, mPosition.length);
        newInt[mPosition.length] = s;
        return new Positions(newInt);
    }

    @Override
    public String toString() {
        return "Positions{" +
                "mPosition=" + Arrays.toString(mPosition) +
                '}';
    }

    public InnerIterator iterator() {
        return new InnerIterator();
    }

    public class InnerIterator implements Iterator<Integer> {

        private int l;

        @Override
        public boolean hasNext() {
            return l < mPosition.length;
        }

        @Override
        public Integer next() {
            return getIntAt(l++);
        }

        @Override
        public void remove() {

        }
    }
}
