package com.e.project_UI_03.UI;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.e.project_UI_03.R;
import com.e.project_UI_03.collector.apk_list_activity;

public class menu extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button appListOpen = (Button) findViewById(R.id.bnt_app_list);
        appListOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.bnt_app_list:
                Intent intent_appList = new Intent(this, apk_list_activity.class);
                startActivity(intent_appList);
                break;
        }
    }
}
