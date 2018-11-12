package com.beibei.drawlayout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<SecondBean> mListData = new ArrayList<>();
    private Context mContext;

    public MenuAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_drawer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        SecondBean secondBean = mListData.get(position);
        holder.tv.setText(secondBean.component1());
        if (holder.rv_son_menu.getAdapter() == null) {
            MenuSonAdapter sonMenuAdapter = new MenuSonAdapter(secondBean.component3());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            holder.rv_son_menu.setLayoutManager(gridLayoutManager);
            holder.rv_son_menu.setAdapter(sonMenuAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setListData(List<SecondBean> listData) {
        this.mListData = listData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private RecyclerView rv_son_menu;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title_drawer);
            rv_son_menu = itemView.findViewById(R.id.rv_son_menu);
        }
    }
}
