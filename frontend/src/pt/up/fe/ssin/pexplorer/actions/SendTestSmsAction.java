package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import pt.up.fe.ssin.pexplorer.utils.ui.InputDialogBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.text.InputType;
import android.widget.EditText;

public class SendTestSmsAction extends PermissionAction {

    public SendTestSmsAction() {
        super(R.string.send_test_sms_label, R.string.send_test_sms_desc,
                PermissionAction.WARN);
    }

    @Override
    protected void doAction(final Context context) {
        InputDialogBuilder builder = new InputDialogBuilder(context);
        final EditText inputText = builder.getEditText();
        inputText.setInputType(InputType.TYPE_CLASS_PHONE);

        builder.setMessage(R.string.send_test_sms_dialog_label);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.send,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsManager
                                .getDefault()
                                .sendTextMessage(
                                        inputText.getText().toString(),
                                        null,
                                        context.getString(R.string.send_test_sms_sms_text),
                                        null, null);
                    }
                }).setNegativeButton(R.string.cancel, null);
        builder.setTitle(R.string.send_test_sms_title);
        builder.create().show();
    }
}
