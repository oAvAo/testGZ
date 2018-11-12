package com.beibei.drawlayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: anbeibei
 * <p>
 * date: 2018/11/11
 * <p>
 * desc:
 *
 * @author anbeibei
 */
public class LeftDrawerAdapter extends RecyclerView.Adapter<LeftDrawerAdapter.ViewHolder> {
    private List<String> mListData = new ArrayList<>();

    @Override
    public LeftDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_drawer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeftDrawerAdapter.ViewHolder holder, int position) {
        String s = mListData.get(position);
        holder.tv.setText(s);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setListData(List<String> listData) {
        this.mListData = listData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title_drawer);
        }
    }
}
