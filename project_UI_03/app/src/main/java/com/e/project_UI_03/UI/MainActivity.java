package com.e.project_UI_03.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.e.project_UI_03.R;
import com.e.project_UI_03.collector.apk_list_activity;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, loading.class);
        startActivity(intent);

        Button lv3more = (Button) findViewById(R.id.lv3_more);
        lv3more.setOnClickListener(this);
        Button lv2more = (Button) findViewById(R.id.lv2_more);
        lv3more.setOnClickListener(this);
        Button lv1more = (Button) findViewById(R.id.lv1_more);
        lv3more.setOnClickListener(this);
        Button menuOpen = (Button) findViewById(R.id.bnt_menu_open);
        menuOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.lv3_more:
                Intent intent3 = new Intent(this, lv3_more.class);
                startActivity(intent3);
                break;
            case R.id.lv2_more:
                Intent intent2 = new Intent(this, lv2_more.class);
                startActivity(intent2);
                break;
            case R.id.lv1_more:
                Intent intent1 = new Intent(this, lv1_more.class);
                startActivity(intent1);
                break;
            case R.id.bnt_menu_open:
                Intent intent_menu = new Intent( this, menu.class);
                startActivity(intent_menu);
                break;
            case R.id.bnt_app_list:
                Intent intent_appList = new Intent(this, apk_list_activity.class);
                startActivity(intent_appList);
                break;
        }
    }
}
