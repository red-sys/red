package com.haha.baidumap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiDetailInfo;

import java.util.List;

/**
 * Created by Zs on 2019/4/3.
 */

public class BottomCommentAdapter extends RecyclerView.Adapter<BottomCommentAdapter.ViewHolder>{
private Context context;
private List<PoiDetailInfo> list;
    public BottomCommentAdapter(Context context,List<PoiDetailInfo> list){
        this.list =list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bottomcomment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.overallRating.setText(String.valueOf(list.get(position).getOverallRating()));
        holder.address.setText(list.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,overallRating,address;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            overallRating = itemView.findViewById(R.id.overallRating);
            address = itemView.findViewById(R.id.address);
        }
    }
}
