package com.example.yurdaer.exe;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yurdaer.exe.GroupFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * Created by Yurdaer on 2017-11-02.
 */
public class MygroupRecyclerViewAdapter extends RecyclerView.Adapter<MygroupRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;

    public MygroupRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position));
        Log.i("item",Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
