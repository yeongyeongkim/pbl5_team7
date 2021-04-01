package pt.up.fe.ssin.pexplorer.actions;

import java.util.Random;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import pt.up.fe.ssin.pexplorer.entities.Contact;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ReadContactsAction extends PermissionAction {

    public ReadContactsAction() {
        super(R.string.read_contact_label, R.string.read_calendar_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {

        Contact randomContact = getRandomContact(context);

        new AlertDialog.Builder(context)
                .setTitle(R.string.read_contact_title)
                .setMessage(
                        String.format(
                                context.getString(R.string.read_contact_entry),
                                randomContact.getName(),
                                randomContact.getPhoneNumber()))
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }

    public static Contact getRandomContact(Context context) {
        Cursor cur = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int random = new Random().nextInt((cur.getCount() - 1) + 1);
        String name = null, phoneNumber = context
                .getString(R.string.phone_number_not_available);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                if (cur.getPosition() == random) {
                    String id = cur.getString(cur
                            .getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur
                            .getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer
                            .parseInt(cur.getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = context
                                .getContentResolver()
                                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + " = ?", new String[] { id },
                                        null);
                        while (pCur.moveToNext()) {
                            phoneNumber = pCur
                                    .getString(pCur
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        pCur.close();
                    }
                    break;
                }
            }
        }
        return new Contact(name, phoneNumber, "");
    }
}
