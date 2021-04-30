package com.e.project_UI_03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class auth_setting_popup extends Activity implements View.OnClickListener {
    int default_ratio = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auth_setting_popup);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        View popup_authSetting = (View) findViewById(R.id.popup_auth_setting);
        Intent intent = getIntent();
    }

    @Override
    public void onClick(View v){
        Spinner spinner = (Spinner)findViewById(R.id.spinner_ratio);
        spinner.setSelection(default_ratio);
        ArrayAdapter ratioAdapter = ArrayAdapter.createFromResource(this,R.array.ratio, R.layout.spinner_layout);
        spinner.setAdapter((ratioAdapter));
        ratioAdapter.setDropDownViewResource((R.layout.spinner_layout));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        switch(v.getId()) {
            case R.id.radio_allow:
            case R.id.radio_deny:
                spinner.setVisibility(View.GONE);
                break;
            case R.id.radio_auto:
                spinner.setVisibility(View.VISIBLE);
        }
    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result","Close Popup");
        setResult(RESULT_OK, intent);
        finish();
    }
}
