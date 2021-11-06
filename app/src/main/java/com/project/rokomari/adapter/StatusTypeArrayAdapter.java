package com.project.rokomari.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.rokomari.R;
import com.project.rokomari.model.Status;

import java.util.List;

public class StatusTypeArrayAdapter extends ArrayAdapter<Status> {

    LayoutInflater flater;

    public StatusTypeArrayAdapter(Activity context, int resouceId, int textviewId, List<Status> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        Status status = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_custom_spineer_layout, null, false);

            holder.txtStatus = (TextView) rowview.findViewById(R.id.tvItemName);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        holder.txtStatus.setText(status.statusTypeName);

        return rowview;
    }

    private class viewHolder{
        TextView txtStatus;

    }
}

