package com.yb.qqhb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yb.qqhb.service.QiangHongBaoService;
import com.yb.qqhb1.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void all(View view){
    	QiangHongBaoService.HONGBAO_TYPE = 1;
    }
    
    public void fuli(View view){
    	QiangHongBaoService.HONGBAO_TYPE = 2;
    }
    
}
