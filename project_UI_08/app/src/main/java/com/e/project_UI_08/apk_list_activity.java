package com.e.project_UI_08;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class apk_list_activity extends Activity implements OnItemClickListener {

    PackageManager packageManager;
    ListView apkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);

        packageManager = getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages( PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        for (PackageInfo packageInfo : packageList) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
            if (intent!=null) {
                packageList1.add(packageInfo);
            }
        }

        apkList = (ListView) findViewById(R.id.app_list);
        apkList.setAdapter(new apk_adapter(this, packageList1, packageManager));

        apkList.setOnItemClickListener(this);
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

