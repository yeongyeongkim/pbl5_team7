package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import pt.up.fe.ssin.pexplorer.utils.ui.InputDialogBuilder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

public class PhoneCallAction extends PermissionAction {

    public PhoneCallAction() {
        super(R.string.call_phone_label, R.string.call_phone_desc,
                PermissionAction.WARN);
    }

    @Override
    protected void doAction(final Context context) {
        InputDialogBuilder builder = new InputDialogBuilder(context);
        final EditText inputText = builder.getEditText();
        inputText.setInputType(InputType.TYPE_CLASS_PHONE);

        builder.setMessage(R.string.call_phone_dialog_label);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.call,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"
                                    + inputText.getText().toString()));
                            context.startActivity(callIntent);
                        } catch (ActivityNotFoundException activityException) {
                            Log.e("Phone Call Failed", "It Failed");
                        }
                    }

                }).setNegativeButton(R.string.cancel, null);
        builder.setTitle(R.string.call_phone_title);
        builder.create().show();
    }
}
