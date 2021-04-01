package pt.up.fe.ssin.pexplorer.data;

import java.io.IOException;
import java.util.Scanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GlobalSchema {

    public static final String PERMISSION_TABLE_NAME = "permissions";
    private static final String CREATE_SCRIPT_FILE = "data.sql";

    public static void createSchema(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PERMISSION_TABLE_NAME + " ( "
                + PermissionExplorerContentProvider.PERMISSION_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PermissionExplorerContentProvider.PERMISSION_NAME
                + " VARCHAR(255), "
                + PermissionExplorerContentProvider.PERMISSION_COMMON
                + " INTEGER ,"
                + PermissionExplorerContentProvider.PERMISSION_DESCRIPTION
                + " VARCHAR(255));");
    }

    public static void readDataFromDBFile(SQLiteDatabase db, Context context,
            String dataBaseName) {
        Scanner sc = null;
        try {
            sc = new Scanner(context.getAssets().open(CREATE_SCRIPT_FILE));
            sc.useDelimiter(";");
            while (sc.hasNext()) {
                String sqlCmd = sc.next();
                Log.i("SQL Command", sqlCmd);
                db.execSQL(sqlCmd);
            }
        } catch (IOException e) {
            Log.e("DB " + dataBaseName, "Failed to read script file.");
            e.printStackTrace();
            return;
        } finally {
            if (sc != null)
                sc.close();
        }
    }
}
