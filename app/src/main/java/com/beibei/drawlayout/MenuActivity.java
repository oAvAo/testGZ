package com.beibei.drawlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

/**
 * author: anbeibei
 * <p>
 * date: 2018/11/11
 * <p>
 * desc:
 */
public class MenuActivity extends Activity {
    private RecyclerView rv;
    private TwinklingRefreshLayout trl;
    private MenuAdapter mAdapterHots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        trl = findViewById(R.id.trl);

        HeadRefreshView headRefreshView = new HeadRefreshView(this);
        trl.setHeaderView(headRefreshView);
        trl.setEnableOverScroll(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        mAdapterHots = new MenuAdapter(this);
        rv.setAdapter(mAdapterHots);
    }

    private void initData() {
        ArrayList<SecondBean> secondList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<ThirdBean> thirdList = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                ThirdBean thirdBean = new ThirdBean("小标题" + j, "", "");
                thirdList.add(thirdBean);
            }
            SecondBean secondBean = new SecondBean("中标题" + i, "", thirdList);
            secondList.add(secondBean);
        }

        ArrayList<SecondBean> oneHot = new ArrayList<>();
        ArrayList<ThirdBean> thirdList = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            ThirdBean thirdBean = new ThirdBean("小标题", "", "");
            thirdList.add(thirdBean);
        }
        SecondBean secondBean = new SecondBean("hots中标题", "", thirdList);
        oneHot.add(secondBean);

        FirstBean hots = new FirstBean("大标题", oneHot);
        FirstBean forums = new FirstBean("大标题", secondList);
        FirstBean sans = new FirstBean("大标题", secondList);

        ArrayList<SecondBean> allList = new ArrayList<>();
        allList.addAll(oneHot);
        allList.addAll(secondList);
        allList.addAll(secondList);
        mAdapterHots.setListData(allList);
    }

    private void initListener() {
        trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                trl.finishRefreshing();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
