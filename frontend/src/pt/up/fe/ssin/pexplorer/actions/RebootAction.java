package pt.up.fe.ssin.pexplorer.actions;

import java.io.IOException;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.content.Context;
import android.widget.Toast;

public class RebootAction extends PermissionAction {

    public RebootAction() {
        super(R.string.reboot_label, R.string.reboot_desc,
                PermissionAction.WARN);
    }

    @Override
    protected void doAction(Context context) {
        try {
            Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot" });
        } catch (IOException e) {
            Toast.makeText(context, R.string.no_root, Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
