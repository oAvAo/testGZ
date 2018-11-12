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
public class MenuSonAdapter extends RecyclerView.Adapter<MenuSonAdapter.ViewHolder> {
    private List<ThirdBean> mListData = new ArrayList<>();

    public MenuSonAdapter(List<ThirdBean> list) {
        this.mListData = list;
    }

    @Override
    public MenuSonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_son_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuSonAdapter.ViewHolder holder, int position) {
        ThirdBean thirdBean = mListData.get(position);
        holder.tv.setText(thirdBean.component1());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_title);
        }
    }
}
