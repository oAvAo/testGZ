package com.beibei.drawlayout.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joyou.smartcity.R;
import com.joyou.smartcity.common.constant.Constant;
import com.joyou.smartcity.common.newNetwork.model.ChatPersonBean;

import java.lang.ref.WeakReference;

import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * Created by beibei on 2017/10/15.
 */
public class FriendListAdapter extends IndexableAdapter<ChatPersonBean> {
    private LayoutInflater mInflater;
    private int mMode;
    private WeakReference<Context> mContext;
    SparseBooleanArray checkboxUserIdList = new SparseBooleanArray();

    public FriendListAdapter(Context context, int mode) {
        mContext = new WeakReference<>(context);
        mInflater = LayoutInflater.from(mContext.get());
        mMode = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, ChatPersonBean entity, int position) {
        final ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.component3());//nickName
        vh.sdv_pic.setImageURI(entity.component4());//userUrl
        if (mMode == Constant.MODE_NORMAL) {
            vh.cb.setVisibility(View.GONE);
        } else {
            vh.cb.setVisibility(View.VISIBLE);
            vh.cb.setTag(position);
            vh.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int tag = (int) buttonView.getTag();
                    if (isChecked) {
                        checkboxUserIdList.put(tag, true);
                    } else {
                        checkboxUserIdList.delete(tag);
                    }
                }
            });
            vh.cb.setChecked(checkboxUserIdList.get(position, false));
            vh.cb.setEnabled(true);
        }
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;
        CheckBox cb;
        SimpleDraweeView sdv_pic;
        LinearLayout llRoot;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_friend_item_root);
            cb.setEnabled(false);
            cb.setClickable(false);
//            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            sdv_pic = (SimpleDraweeView) itemView.findViewById(R.id.sdv_pic);
        }
    }
}
