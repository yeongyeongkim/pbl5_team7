package pt.up.fe.ssin.pexplorer.data;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class PermissionExplorerContentProvider extends ContentProvider {

    private static final String TAG = "PermissionExplorerContentProvider";

    private static final String DATABASE_NAME = "security.db";

    private static final int DATABASE_VERSION = 5;

    public static final String AUTHORITY = "pt.up.fe.ssin.pexplorer.data.permissionexplorercontentprovider";

    private static final UriMatcher sUriMatcher;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/permissions");

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ssin.permissions";

    private static HashMap<String, String> permissionProjectionMap;

    public static final String PERMISSION_ID = "id";

    public static final String PERMISSION_NAME = "name";

    public static final String PERMISSION_DESCRIPTION = "description";

    public static final String PERMISSION_COMMON = "common";

    private static final int PERMISSION = 1;

    private static final int PERM_ID = 2;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            GlobalSchema.createSchema(db);
            GlobalSchema.readDataFromDBFile(db, this.context, DATABASE_NAME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "
                    + GlobalSchema.PERMISSION_TABLE_NAME);
            onCreate(db);
        }
    }

    private static DatabaseHelper dbHelper;

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case PERMISSION:
            count = db.delete(GlobalSchema.PERMISSION_TABLE_NAME, where,
                    whereArgs);
            break;
        case PERM_ID:
            String id = uri.getLastPathSegment();
            count = db.delete(GlobalSchema.PERMISSION_TABLE_NAME,
                    PermissionExplorerContentProvider.PERMISSION_ID + "= ?",
                    new String[] { id });
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
        case PERMISSION:
            return PermissionExplorerContentProvider.CONTENT_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != PERMISSION) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db
                .insert(GlobalSchema.PERMISSION_TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
                    PermissionExplorerContentProvider.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        qb.setTables(GlobalSchema.PERMISSION_TABLE_NAME);
        qb.setProjectionMap(permissionProjectionMap);
        Cursor c = null;

        switch (sUriMatcher.match(uri)) {
        case PERMISSION:
            c = qb.query(db, projection, selection, selectionArgs, null, null,
                    sortOrder);
            break;

        case PERM_ID:
            String id = uri.getLastPathSegment();
            c = qb.query(db, projection,
                    PermissionExplorerContentProvider.PERMISSION_ID + "= ?",
                    new String[] { id }, null, null, null);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case PERMISSION:
            count = db.update(GlobalSchema.PERMISSION_TABLE_NAME, values,
                    where, whereArgs);
            break;
        case PERM_ID:
            String id = uri.getLastPathSegment();
            count = db.update(GlobalSchema.PERMISSION_TABLE_NAME, values,
                    PermissionExplorerContentProvider.PERMISSION_ID + "= ?",
                    new String[] { id });
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, GlobalSchema.PERMISSION_TABLE_NAME,
                PERMISSION);

        permissionProjectionMap = new HashMap<String, String>();
        permissionProjectionMap.put(PERMISSION_ID, PERMISSION_ID);
        permissionProjectionMap.put(PERMISSION_NAME, PERMISSION_NAME);
        permissionProjectionMap.put(PERMISSION_DESCRIPTION,
                PERMISSION_DESCRIPTION);
        permissionProjectionMap.put(PERMISSION_COMMON, PERMISSION_COMMON);
    }
}