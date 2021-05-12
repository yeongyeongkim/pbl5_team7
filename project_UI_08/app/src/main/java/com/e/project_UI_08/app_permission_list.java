package com.e.project_UI_08;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class app_permission_list extends Activity implements OnClickListener {
    TextView appLabel;
    ImageView appIcon;
    PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_permission_list);

        findViewsById();
        app_data appData = (app_data) getApplicationContext();
        packageInfo = appData.getPackageInfo();
        setValues();

        View View = (View) findViewById(R.id.popup_permission_setting);
        TextView appPermissionList = (TextView) findViewById(R.id.txt_permissionName);
        appPermissionList.setOnClickListener(this);
    }

    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.permissionList_appName);
        appIcon = (ImageView) findViewById(R.id.permissionList_appIcon);
    }

    private void setValues() {
        appLabel.setText(getPackageManager().getApplicationLabel(packageInfo.applicationInfo));
        appIcon.setImageDrawable(getPackageManager().getApplicationIcon(packageInfo.applicationInfo));
    }

    public void OnPopupClick(View v){
        Intent intent = new Intent(this, permission_setting_popup.class);
        intent.putExtra("data","Popup");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.txt_permissionName:
                OnPopupClick(v);
                break;
        }
    }

}
