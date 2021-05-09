package com.e.project_UI_03.view;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.e.project_UI_03.R;
import com.e.project_UI_03.collector.ApkAdapter;
import com.e.project_UI_03.collector.AppData;

public class MainActivity extends Activity implements OnItemClickListener {
    private static final String TAG = "SQLite";
    SQLiteDatabase database;
    String DBName = "PM";
    String tableName= "appPermStatus";

    PackageManager packageManager;
    ListView apkList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent loadingIntent = new Intent(this, Loading.class);
        startActivity(loadingIntent);

        Button button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(getApplicationContext(), Custom.class);
                startActivity(move);
            }
        });

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

        //SQLite Part
        openDatabase(DBName);
        createTable(tableName);
        insertRecord();
        executeQuery();

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



    //SQLite Part
// 데이터베이스 생성
    void openDatabase(String name) {
        Log.i(TAG,"openDatabase 호출됨.");

        try {
            // 1) Helper를 사용하지 않을 때
            database = openOrCreateDatabase(name, MODE_PRIVATE, null);

            // 2) Helper 사용할 때
            //dbHelper = new DatabaseHelper(this, name, null, 1); // 헬퍼 생성.
            //database = dbHelper.getWritableDatabase(); // 데이터베이스를 쓸 수 있는 권한 리턴(권한 가짐.).

            Log.i(TAG,"데이터베이스 생성함 : " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 테이블 생성
    void createTable(String tableName) {
        Log.i(TAG,"createTable 호출됨.");

        try{
            if (database == null) {
                Log.i(TAG,"데이터베이스를 먼저 생성하세요.");
                return;
            }
            // 어떤 데이터 들어오는지에 대해 따라 스키마 달라질 거임.
            //appId
            //permissionId
            //permissionStatus
            // integer 말고 packageManager.packageInfo 형태로 불러오는거 고민해야함.
            // packageInfo.packageName -> text
            // packageInfo.requestedPermissions -> text
            // getGrantedPermissions(packageInfo.packageName) -> List<String> grant

            String sql= "create table if not exists " + tableName + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " appId integer, "
                    + " permissionId integer, "
                    + " permissionStatus text)";

            database.execSQL(sql);

            Log.i(TAG,"테이블 생성함 : " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 레코드 삽입
    void insertRecord() {
        Log.i(TAG,"insertRecord 호출됨.");

        try {
            if (database == null) {
                Log.i(TAG,"데이터베이스를 먼저 생성하세요.");
                return;
            }
            if (tableName == null) {
                Log.i(TAG,"테이블을 먼저 생성하세요.");
                return;
            }

            // 어떤 데이터가 들어오는지에 따라 테이블 컬럼이 달리지므로 변경해야함.
            //appId
            //permissionId
            //permissionStatus
            String sql ="insert into " + tableName
                    + "(appId, permissionId, permissionStatus) "
                    + "values" + "('1', '2', 'aOff')";

            database.execSQL(sql);

            Log.i(TAG,"레코드 추가함.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 레코드 조회
    public void executeQuery() {
        Log.i(TAG,"executeQuery 호출됨.");

        try{
            if (database == null) {
                Log.i(TAG,"데이터베이스를 먼저 생성하세요.");
                return;
            }
            if (tableName == null) {
                Log.i(TAG,"테이블을 이름을 입력하세요.");
                return;
            }

            String sql = "select * from " + tableName;

            // 수많은 레코드를 넘겨가면서 접근할 수 있음.
            Cursor cursor = database.rawQuery(sql, null);
            // getCount()로 개수 셀 수 있음.
            int recordCount = cursor.getCount();
            Log.i(TAG,"레코드 개수 : " + recordCount);

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext(); // 그 다음 레코드로 넘어감.

                // integer 값으로 된 "첫번째 컬럼" 데이터에 접근할 수 있음.
                int id = cursor.getInt(0);
                // String 값으로 된 "두번째 컬럼" 데이터에 접근할 수 있음.
                int appId = cursor.getInt(1);
                // integer 값으로 된 "세번째 컬럼" 데이터에 접근할 수 있음.
                int permissionId = cursor.getInt(2);
                // String 값으로 된 "네번째 컬럼" 데이터에 접근할 수 있음.
                String permissionStatus = cursor.getString(3);

                Log.i(TAG,"레코드 #" + i + " : " + id + ", " + appId + ", " + permissionId + ", " + permissionStatus);
            }
            // cursor는 한정된 자원이라 닫아줘야함.
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
