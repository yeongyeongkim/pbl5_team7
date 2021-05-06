package com.e.project_UI_03.UI;

import android.app.Activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.project_UI_03.R;
import com.e.project_UI_03.collector.AppData;

import java.util.ArrayList;
import java.util.List;

public class ApkInfo extends Activity {
    TextView appLabel;
    ImageView appIcon;
    TextView permissions;
    TextView features;

    PackageInfo packageInfo;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_auth);

        findViewsById();
        AppData appData = (AppData) getApplicationContext();
        packageInfo = appData.getPackageInfo();
        setValues();

    }

    private void findViewsById() {
        appLabel = (TextView) findViewById(R.id.authList_appName);
        appIcon = (ImageView) findViewById(R.id.authList_appIcon);
        permissions = (TextView) findViewById(R.id.req_permission);
        features = (TextView) findViewById(R.id.req_feature);
    }

    private void setValues() {

        // app name
        appLabel.setText(getPackageManager().getApplicationLabel(packageInfo.applicationInfo));

        // app icon
        appIcon.setImageDrawable(getPackageManager().getApplicationIcon(packageInfo.applicationInfo));

        // permission
        if (packageInfo.requestedPermissions != null)
            permissions.setText(getPermissions(packageInfo.requestedPermissions));
        else
            permissions.setText("-");

        // permission-granted
        if (packageInfo.requestedPermissionsFlags != null)
            features.setText(getGrantedPermissions(packageInfo.packageName));
        else
            features.setText("-");
    }

    private String getPermissions(String[] requestedPermissions) {
        String permission = "";
        for (int i = 0; i < requestedPermissions.length; i++) {
            permission = permission + requestedPermissions[i] + ",\n";
        }
        return permission;
    }

    public String getGrantedPermissions(final String appPackage) {
        List<String> granted = new ArrayList<String>();

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    granted.add(pi.requestedPermissions[i]);
                }
            }
        } catch (Exception e) {}

        String gPermission = "";
        for(int i = 0; i < granted.size(); i++){
            gPermission = gPermission + granted.get(i) + ",\n";
            Log.d("PERMISSION_GRANTED", granted.get(i));
        }
        return gPermission;
    }

}
