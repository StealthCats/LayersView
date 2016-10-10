package com.monkey.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.monkey.control.Positions;

/**
 * Created by runmonkey on 16/7/16.
 */

public interface MuiltiInterface {

    int getCount(Positions positions);
    View getView(View convertView, ViewGroup parent, Positions positions);
    void notifyChange();
}
