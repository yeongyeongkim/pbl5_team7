package pt.up.fe.ssin.pexplorer.actions;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.content.Context;
import android.content.Intent;

public class TakePictureAction extends PermissionAction {

    public TakePictureAction() {
        super(R.string.take_picture_label, R.string.take_picture_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        final Intent intent = new Intent(context, TakePictureActivity.class);
        context.startActivity(intent);
    }
}
