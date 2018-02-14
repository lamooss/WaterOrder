package com.techdmz.lamooss.waterorder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DBC on 2017-11-30.
 */

public class WaterListAdapter extends BaseAdapter {

    private int layout;
    private Context context;
    private List<Water> waterList;
    private Fragment parent;

    private SparseArray<WeakReference<View>> viewArray;
    private LayoutInflater inflater;

    public ArrayList<Integer> quantity = new ArrayList<Integer>();
    public ArrayList<Water> waterdata;


    public WaterListAdapter(Context context, List<Water> waterList, Fragment parent) {
        this.context = context;
        this.waterList = waterList;
        this.parent = parent;

        this.viewArray = new SparseArray<WeakReference<View>>(waterList.size());
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int dd = waterList.size();

        for(int i =0; i<waterList.size(); i++)
        {
            quantity.add(0);
        }
    }

    @Override
    public int getCount() {
        return waterList.size();
    }

    @Override
    public Object getItem(int position) {
        return waterList.get(position).getWater_id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final int pos = position;
        View myView = convertView;

        //if this is the first time coming here
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(myView == null){
            //myView = inflater.inflate(R.layout.)
        }


        View v = View.inflate(context, R.layout.water, null);




        TextView water_id = (TextView) v.findViewById(R.id.water_id);
        TextView water_name = (TextView) v.findViewById(R.id.water_name);
        TextView water_size = (TextView) v.findViewById(R.id.water_size);
        TextView water_type = (TextView) v.findViewById(R.id.water_type);

        water_id.setText(String.valueOf(waterList.get(position).getWater_id()));
        water_name.setText(waterList.get(position).getWater_name());
        water_size.setText(String.valueOf(waterList.get(position).getWater_size()));
        water_type.setText(waterList.get(position).getWater_type());

        if(viewArray != null && viewArray.get(position) != null)
        {
            convertView = viewArray.get(position).get();
            if(convertView != null)
            {
                return convertView;
            }
        }

        /*
        if(convertView == null)
        {
            convertView = inflater.inflate(layout, viewGroup, false);
        }
        */

        Button incrementButton = (Button)v.findViewById(R.id.incrementButton);
        incrementButton.setTag(position);
        incrementButton.setOnClickListener(mOnClickListenerIncBtn);

        Button decrementButton = (Button)v.findViewById(R.id.decrementButton);
        decrementButton.setTag(position);
        decrementButton.setOnClickListener(mOnClickListenerDecBtn);

        return v;
    }

    Button.OnClickListener mOnClickListenerIncBtn = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Water water = waterList.get(position);


            TextView quantityTextView = (TextView) v.findViewById(R.id.quantity_text_view);


            int iOrgQuantity = waterList.get(position).getWater_quantity();
            iOrgQuantity++;
            water.setWater_quantity(iOrgQuantity);

            System.out.print("11111222222");
            quantityTextView.setText(iOrgQuantity+"");
            System.out.print("11111333333");


            //quantity.set(position, iOrgQuantity);

            //waterList.set(position, Integer.toString(iOrgQuantity)).;
            //String sQuantity = quantityTextView.

        }
    };

    Button.OnClickListener mOnClickListenerDecBtn = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            System.out.print("2222222");
        }
    };


    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
