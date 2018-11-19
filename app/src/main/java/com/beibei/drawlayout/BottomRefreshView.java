package com.beibei.drawlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * 项目名称：demeter-Android
 * 包名：com.dashen.demeter.view
 * 创建人：dashen
 * 创建时间：2016/12/28 16:51
 * 类描述：底部刷新view
 * 备注：
 */
public class BottomRefreshView extends FrameLayout implements IBottomView {
    private ImageView loadingPic;

    public BottomRefreshView(Context context) {
        this(context, null);
    }

    public BottomRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = View.inflate(getContext(), R.layout.view_refresh_bottom, null);
        loadingPic = inflate.findViewById(R.id.iv_loading_pic);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        inflate.setLayoutParams(params);
        addView(inflate);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        ((AnimationDrawable)loadingPic.getDrawable()).start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
