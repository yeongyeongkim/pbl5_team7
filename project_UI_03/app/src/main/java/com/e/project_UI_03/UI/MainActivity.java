package com.e.project_UI_03.UI;

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

import com.e.project_UI_03.R;
import com.e.project_UI_03.collector.ApkAdapter;
import com.e.project_UI_03.collector.AppData;

public class MainActivity extends Activity implements OnItemClickListener {

    PackageManager packageManager;
    ListView apkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent loadingIntent = new Intent(this, Loading.class);
        startActivity(loadingIntent);

        packageManager = getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages( PackageManager.GET_PERMISSIONS );
        List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();

        for (PackageInfo packageInfo : packageList) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
            if (intent!=null) {
                packageList1.add(packageInfo);
            }
        }

        apkList = (ListView) findViewById(R.id.app_list);
        apkList.setAdapter(new ApkAdapter(this, packageList1, packageManager));
        apkList.setOnItemClickListener(this);
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags ) != 0) ? true
                : false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long row) {
        PackageInfo packageInfo = (PackageInfo) parent.getItemAtPosition(position);
        AppData appData = (AppData) getApplicationContext();
        appData.setPackageInfo(packageInfo);

        Intent intent_authList = new Intent(this, ApkInfo.class);
        startActivity(intent_authList);
    }

}
