package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.content.Context;
import android.os.Vibrator;

public class VibrateAction extends PermissionAction {

    public VibrateAction() {
        super(R.string.vibrate_action_label, R.string.vibrate_action_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        Vibrator v = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);
    }

}
