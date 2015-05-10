package com.beatitudes.mybaby.Baby;

/**
 * Created by user on 10-05-2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VaccineItemAdapter extends ArrayAdapter<VaccineItem> {

    int resource;

    public VaccineItemAdapter(Context context,
                           int resource,
                           List<VaccineItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout todoView;

        VaccineItem item = getItem(position);

        String vaccinenameString = item.getVaccinename();
        Date vaccinenameDate = item.getVaccinedate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(vaccinenameDate);

        if (convertView == null) {
            todoView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, todoView, true);
        } else {
            todoView = (LinearLayout) convertView;
        }

      ////  TextView dateView = (TextView)todoView.findViewById(R.id.rowDate);
        ////TextView vaccineView = (TextView)todoView.findViewById(R.id.row);

       //// dateView.setText(dateString);
        ////vaccineView.setText(vaccinenameString);

        return todoView;
    }
}