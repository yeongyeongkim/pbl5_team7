package com.e.project_UI_03;

import android.app.Application;
import android.content.pm.PackageInfo;

public class app_data extends Application {

    PackageInfo packageInfo;

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }
}