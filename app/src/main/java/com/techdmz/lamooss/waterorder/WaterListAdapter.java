package com.techdmz.lamooss.waterorder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DBC on 2017-11-30.
 */

public class WaterListAdapter extends BaseAdapter {

    private Context context;
    private List<Water> waterList;
    private Fragment parent;

    public WaterListAdapter(Context context, List<Water> waterList, Fragment parent) {
        this.context = context;
        this.waterList = waterList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return waterList.size();
    }

    @Override
    public Object getItem(int i) {
        return waterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.water, null);

        TextView water_id = (TextView) v.findViewById(R.id.water_id);
        TextView water_name = (TextView) v.findViewById(R.id.water_name);
        TextView water_size = (TextView) v.findViewById(R.id.water_size);
        TextView water_type = (TextView) v.findViewById(R.id.water_type);

        water_id.setText(String.valueOf(waterList.get(i).getWater_id()));
        water_name.setText(waterList.get(i).getWater_name());
        water_size.setText(String.valueOf(waterList.get(i).getWater_size()));
        water_type.setText(waterList.get(i).getWater_type());

        /*
        water_id.setText("1");
        water_name.setText("1");
        water_size.setText("1");
        water_type.setText("1");
        */

        //v.setTag("1");
        v.setTag(waterList.get(i).getWater_id());
        return v;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
