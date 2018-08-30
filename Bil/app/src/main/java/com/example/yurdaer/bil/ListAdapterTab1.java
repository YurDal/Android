package com.example.yurdaer.bil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;


public class ListAdapterTab1 extends RecyclerView.Adapter<ListAdapterTab1.GroupViewHolder> implements View.OnClickListener {
    private List<String> vehicleDataList = new ArrayList<>();
    private Tab1Fragment tab1Fragment;


    public ListAdapterTab1(List<String> vehicleDataList, Tab1Fragment tab1Fragment) {
        this.vehicleDataList = vehicleDataList;
        this.tab1Fragment = tab1Fragment;
    }


    @Override
    public int getItemViewType(int position) {
        return (position == vehicleDataList.size()) ? R.layout.btn_end_of_list : R.layout.row_list;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == R.layout.row_list) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.btn_end_of_list, parent, false);
        }
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        if (position == vehicleDataList.size()) {
            holder.button.setOnClickListener(this);
        } else {
            holder.tvContent.setText(vehicleDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return vehicleDataList.size() + 1; // +1 for the button at the end of the list
    }


    @Override
    public void onClick(View v) {
        tab1Fragment.onBackPressed();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        Button button;

        private GroupViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            button = (Button) itemView.findViewById(R.id.btn_back);
        }
    }

}
