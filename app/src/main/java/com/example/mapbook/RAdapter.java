package com.example.mapbook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.mViewHolder>{

    private List<String> list;

    public RAdapter(List<String> list){

        this.list = list;

    }


    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        TextView text = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup, false);
        mViewHolder mViewHolder = new mViewHolder(text);
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder viewHolder, int i) {
        viewHolder.text.setText(list.get(i));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public mViewHolder(TextView item){
            super(item);
            text = item;
        }

    }
}
