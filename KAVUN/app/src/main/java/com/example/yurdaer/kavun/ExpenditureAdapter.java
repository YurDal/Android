package com.example.yurdaer.kavun;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by YURDAER on 2017-10-11.
 */

public class ExpenditureAdapter extends ArrayAdapter<Expenditure> {
    private LayoutInflater inflater;
    int[] myImageList = new int[]{R.drawable.food, R.drawable.leisure, R.drawable.travel, R.drawable.accommodation, R.drawable.other};

    public ExpenditureAdapter(Context context, Expenditure[] expenditures) {
        super(context, R.layout.fragment_item_inc, expenditures);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = (LinearLayout) inflater.inflate(R.layout.fragment_item_inc, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate_inc);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ic_inc);
            viewHolder.tvTittle = (TextView) convertView.findViewById(R.id.tvTitle_inc);
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount_inc);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDate.setText(this.getItem(position).getDate());
        viewHolder.tvTittle.setText(this.getItem(position).getTittle());
        viewHolder.tvAmount.setText(this.getItem(position).getAmount());
        viewHolder.ivImage.setImageResource(myImageList[choiseImage(this.getItem(position).getCategory())]);
        viewHolder.tvAmount.setText(this.getItem(position).getAmount());
        viewHolder.tvAmount.setTextColor(Color.parseColor("#ce0000"));
        return convertView;
    }

    private int choiseImage(String category) {
        if (category.equals("Food"))
            return 0;
        if (category.equals("Leisure"))
            return 1;
        if (category.equals("Travel"))
            return 2;
        if (category.equals("Accommodation"))
            return 3;
        else
            return 4;
    }

    class ViewHolder {
        TextView tvDate;
        ImageView ivImage;
        TextView tvTittle;
        TextView tvAmount;
    }
}