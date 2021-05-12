package com.e.project_UI_08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

public class permission_setting_popup extends Activity implements View.OnClickListener {
    int default_ratio = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.permission_setting_popup);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.permission_radioGroup);

        View popup_permissionSetting = (View) findViewById(R.id.popup_permission_setting);
        Intent intent = getIntent();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.radio_allow:
            case R.id.radio_deny:
        }
    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result","Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }
}

