package pt.up.fe.ssin.pexplorer.actions;

import java.util.Date;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

public class WriteCalendarAction extends PermissionAction {

    public WriteCalendarAction() {
        super(R.string.write_calendar_label, R.string.write_calendar_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        String contentProvider = new String();
        String id = new String();
        if (Build.VERSION.RELEASE.contains("2.2")
                || Build.VERSION.RELEASE.contains("2.3"))
            contentProvider = "com.android.calendar";
        else
            contentProvider = "calendar";

        final Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://" + contentProvider + "/calendars"),
                (new String[] { "_id" }), null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                break;
            }
        }

        createEvent(context, id, contentProvider);
        Toast.makeText(context, R.string.write_calendar_success,
                Toast.LENGTH_SHORT).show();
    }

    private void createEvent(Context context, String id, String contentProvider) {
        long startTime = new Date().getTime();
        long endTime = startTime + 60000;
        ContentValues event = new ContentValues();
        event.put("calendar_id", id);
        event.put("title",
                context.getString(R.string.write_calendar_event_title));
        event.put("description",
                context.getString(R.string.write_calendar_event_desc));
        event.put("eventLocation",
                context.getString(R.string.write_calendar_event_location));
        event.put("dtstart", startTime);
        event.put("dtend", endTime);
        event.put("visibility", 0);
        event.put("hasAlarm", 1);
        Uri eventsUri = Uri.parse("content://" + contentProvider + "/events");
        context.getContentResolver().insert(eventsUri, event);
    }

}