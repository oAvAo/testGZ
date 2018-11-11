package com.beibei.drawlayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Space;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

/**
 * @author anbeibei
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    ImageView ivMenu;
    Space space_status_bar;
    RecyclerView rv;
    TwinklingRefreshLayout trl;
    ImageView iv_scan;

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ivMenu = findViewById(R.id.iv_head_left);
        space_status_bar = findViewById(R.id.space_status_bar_drawer);
        rv = findViewById(R.id.rv);
        trl = findViewById(R.id.trl);
        iv_scan = findViewById(R.id.iv_scan);
    }

    private void initListener() {
        iv_scan.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();


        int mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, mStatusBarColor, 128);

        int screenWidthPixels = getResources().getDisplayMetrics().widthPixels;

        View leftDrawer = findViewById(R.id.left_drawer);
        ViewGroup.LayoutParams Params = leftDrawer.getLayoutParams();
        Params.width = screenWidthPixels;
        leftDrawer.setLayoutParams(Params);

        ViewGroup.LayoutParams layoutParams = space_status_bar.getLayoutParams();
        layoutParams.height = getStatusBarHeight();
        space_status_bar.setLayoutParams(layoutParams);

//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        ivMenu.setOnClickListener(this);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        initRefreshView();
        LeftDrawerAdapter leftDrawerAdapter = new LeftDrawerAdapter();
        rv.setAdapter(leftDrawerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("热门搜索" + i);
        }
        leftDrawerAdapter.setListData(strings);

        trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                trl.finishRefreshing();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_left: {
//                drawerLayout.openDrawer(Gravity.START);
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case R.id.iv_scan: {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, ScanQRCodeActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initRefreshView() {
        HeadRefreshView headRefreshView = new HeadRefreshView(this);
        trl.setHeaderView(headRefreshView);
    }
}
