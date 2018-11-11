package com.beibei.drawlayout;

import android.app.Activity;
import android.os.Bundle;

/**
 * author: anbeibei
 * <p>
 * date: 2018/11/11
 * <p>
 * desc:
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
