package pt.up.fe.ssin.pexplorer.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;

public class ReadCalendarAction extends PermissionAction {

    public static final DateFormat dateFormater = new SimpleDateFormat(
            "yyyy-MM-dd");

    public ReadCalendarAction() {
        super(R.string.read_calendar_label, R.string.read_calendar_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        String id = new String();
        String contentProvider = null;

        if (Build.VERSION.RELEASE.contains("2.2")
                || Build.VERSION.RELEASE.contains("2.3"))
            contentProvider = "com.android.calendar";
        else
            contentProvider = "calendar";

        final Cursor cursor = contentResolver.query(
                Uri.parse("content://" + contentProvider + "/calendars"),
                (new String[] { "_id" }), null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                break;
            }
        }

        Uri.Builder builder = Uri.parse(
                "content://" + contentProvider + "/instances/when").buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);

        Cursor eventCursor = contentResolver.query(builder.build(),
                new String[] { "title", "begin", "end", "allDay" },
                "Calendars._id=" + id, null, "startDay ASC, startMinute ASC");

        String title = new String();
        Date begin = new Date(), end = new Date();
        while (eventCursor.moveToNext()) {
            title = eventCursor.getString(0);
            begin = new Date(eventCursor.getLong(1));
            end = new Date(eventCursor.getLong(2));
            if (title.length() > 0)
                break;
        }

        new AlertDialog.Builder(context)
                .setTitle(R.string.read_calendar_title)
                .setMessage(
                        String.format(
                                context.getString(R.string.read_calendar_entry),
                                title, dateFormater.format(begin),
                                dateFormater.format(end)))
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }
}
