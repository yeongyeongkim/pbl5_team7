package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;

public class ReadPhoneStateAction extends PermissionAction {

    public ReadPhoneStateAction() {
        super(R.string.read_phone_state_label, R.string.read_phone_state_desc,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {

        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        String imei = mTelephonyMgr.getDeviceId();
        String phoneNumber = mTelephonyMgr.getLine1Number();
        String softwareVer = mTelephonyMgr.getDeviceSoftwareVersion();
        String simSerial = mTelephonyMgr.getSimSerialNumber();
        String subscriberId = mTelephonyMgr.getSubscriberId();

        new AlertDialog.Builder(context)
                .setTitle(R.string.read_phone_state_title)
                .setMessage(
                        String.format(context
                                .getString(R.string.read_phone_state_entry),
                                imei, phoneNumber, softwareVer, simSerial,
                                subscriberId))
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }

}
