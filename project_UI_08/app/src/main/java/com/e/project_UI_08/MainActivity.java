package com.e.project_UI_08;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {

    PackageManager packageManager;
    ListView apkList;

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, loading.class);
        startActivity(intent);


        Button settingOpen = (Button) findViewById(R.id.bnt_setting_open);
        settingOpen.setOnClickListener(this);

        packageManager = getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages( PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        for (PackageInfo packageInfo : packageList) {
            Intent intent_p = getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
            if (intent_p!=null) {
                packageList1.add(packageInfo);
            }
        }

        apkList = (ListView) findViewById(R.id.app_list);
        apkList.setAdapter(new apk_adapter(this, packageList1, packageManager));

        apkList.setOnItemClickListener(this);
    }

    public void OnPopupClick(View v){
        Intent intent = new Intent(this, ratio_setting_popup.class);
        intent.putExtra("data","Popup");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bnt_setting_open:
                Intent intent_setting = new Intent( this, setting.class);
                startActivity(intent_setting);
                break;
            case R.id.bnt_app_list:
                Intent intent_appList = new Intent(this, apk_list_activity.class);
                startActivity(intent_appList);
                break;
            case R.id.bnt_master:
                OnPopupClick(v);
        }
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags ) != 0) ? true
                : false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long row) {
        PackageInfo packageInfo = (PackageInfo) parent.getItemAtPosition(position);
        app_data appData = (app_data) getApplicationContext();
        appData.setPackageInfo(packageInfo);

        Intent intent_permissionList = new Intent(this, app_permission_list.class);
        startActivity(intent_permissionList);
    }

}
