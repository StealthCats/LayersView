package com.monkey.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.monkey.control.BaseMuitiControl;
import com.monkey.control.Positions;
import com.monkey.interfaces.MuiltiInterface;
import com.monkey.library.R;

/**
 * Created by RnMonkey on 16-5-21.
 */
public abstract class BaseListViewAdapter extends BaseAdapter implements MuiltiInterface, View.OnClickListener {

    private static final String TAG = "BaseListViewAdapter";
    private BaseMuitiControl mControl;

    public BaseListViewAdapter() {
        mControl = new BaseMuitiControl(this);
    }

    @Override
    public final int getCount() {
        return mControl.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public final View getView(int i, View view, ViewGroup viewGroup) {

        Positions positions = mControl.getPosition(i);
        Log.d(TAG, "getView: pos ="+i+" change ="+positions);
        view = getView(view, viewGroup, positions);
        view.setTag(R.string.app_name,positions);
        view.setOnClickListener(this);
        return view;
    }


    @Override
    public void notifyChange() {
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        mControl.onItemClick((Positions) view.getTag(R.string.app_name));
    }
}
