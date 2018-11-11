package com.beibei.drawlayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * author: anbeibei
 * <p>
 * date: 2018/11/11
 * <p>
 * desc:
 */
public class ScanQRCodeActivity extends Activity implements QRCodeView.Delegate, View.OnClickListener {

    private ZBarView zBarView;
    private LinearLayout ll_light;
    private TextView tv_turn_light;
    private ImageView iv_turn_light;

    private boolean isOpenLight;//是否开启闪光灯

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        zBarView = findViewById(R.id.zbarview);
        ll_light = findViewById(R.id.ll_light);
        tv_turn_light = findViewById(R.id.tv_turn_light);
        iv_turn_light = findViewById(R.id.iv_turn_light);
        zBarView.setDelegate(this);
        ll_light.setOnClickListener(this);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Toast.makeText(this, "扫码结果：" + result, Toast.LENGTH_SHORT).show();
        zBarView.startSpot(); // 延迟0.5秒后开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        zBarView.startCamera();// 打开后置摄像头开始预览，但是并未开始识别
        zBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        super.onStop();
        zBarView.stopCamera();// 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zBarView.onDestroy(); // 销毁二维码扫描控件
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_light: {
                try {
                    if (!isOpenLight) {
                        zBarView.openFlashlight();
                        iv_turn_light.setImageResource(R.mipmap.scan_shine_pressed_button);
                        tv_turn_light.setText("关闭手电筒");
                    } else {
                        zBarView.closeFlashlight();
                        iv_turn_light.setImageResource(R.mipmap.scan_shine_normal_button);
                        tv_turn_light.setText("打开手电筒");
                    }
                    isOpenLight = !isOpenLight;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
