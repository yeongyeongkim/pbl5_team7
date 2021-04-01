package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;

public class ChangeWifiStateAction extends PermissionAction {

    public ChangeWifiStateAction() {
        super(R.string.wifi_state_label, R.string.write_calendar_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String message = new String();

        if (wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(false);
            message = context.getString(R.string.wifi_state_shutdown);
        } else {
            wifi.setWifiEnabled(true);
            message = context.getString(R.string.wifi_state_activated);
        }

        new AlertDialog.Builder(context)
                .setTitle(R.string.wifi_state_label)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }
}
