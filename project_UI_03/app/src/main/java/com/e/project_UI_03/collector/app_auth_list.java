package com.e.project_UI_03.collector;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.project_UI_03.R;
import com.e.project_UI_03.UI.auth_setting_popup;

public class app_auth_list extends Activity implements OnClickListener {
    TextView appLabel;
    ImageView appIcon;
    PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_auth_list);

        findViewsById();
        app_data appData = (app_data) getApplicationContext();
        packageInfo = appData.getPackageInfo();
        setValues();

        View View = (View) findViewById(R.id.popup_auth_setting);
        TextView appAuthList = (TextView) findViewById(R.id.txt_authName);
        appAuthList.setOnClickListener(this);
    }

    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.authList_appName);
        appIcon = (ImageView) findViewById(R.id.authList_appIcon);
    }

    private void setValues() {
        appLabel.setText(getPackageManager().getApplicationLabel(packageInfo.applicationInfo));
        appIcon.setImageDrawable(getPackageManager().getApplicationIcon(packageInfo.applicationInfo));
    }

    public void RadioClick(View v) {
        final String [] items = {"30%", "50%", "70%", "허용/거부"};
        new AlertDialog.Builder(this).setTitle("비율 선택").setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK",null).setNegativeButton("cancel", null).show();
    }

    public void OnPopupClick(View v){
        Intent intent = new Intent(this, auth_setting_popup.class);
        intent.putExtra("data","Popup");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.txt_authName:
                OnPopupClick(v);
                break;
        }
    }

}
