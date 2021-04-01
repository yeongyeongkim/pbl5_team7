package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class WriteSettingsAction extends PermissionAction {

    public WriteSettingsAction() {
        super(R.string.write_settings_label, R.string.write_settings_desc,
                PermissionAction.WARN);
    }

    @Override
    protected void doAction(final Context context) {

        boolean isEnabled = Settings.System.getInt(
                context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON,
                0) == 1;
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !isEnabled);
        context.sendBroadcast(intent);

        new AlertDialog.Builder(context)
                .setTitle(R.string.write_settings_label)
                .setMessage(R.string.write_settings_changed_msg)
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }
}
