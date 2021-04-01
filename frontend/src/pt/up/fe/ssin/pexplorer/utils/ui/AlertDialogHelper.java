package pt.up.fe.ssin.pexplorer.utils.ui;

import pt.up.fe.ssin.pexplorer.R;
import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogHelper {

    private static AlertDialogHelper instance;

    public static AlertDialogHelper getInstance(Context context) {
        if (instance == null)
            instance = new AlertDialogHelper(context);
        else
            instance.context = context;
        return instance;
    }

    private Context context;

    private AlertDialogHelper(Context context) {
        this.context = context;
    }

    public AlertDialog createOkDialog(int labelResId, int textResId) {
        return createOkDialog(context.getString(labelResId),
                context.getString(textResId));
    }

    public AlertDialog createOkDialog(String label, String text) {
        return createOkDialog(label, text, 0);
    }

    public AlertDialog createOkDialog(String label, String text, int iconResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(text);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.ok, null);
        builder.setTitle(label);
        if (iconResId > 0)
            builder.setIcon(iconResId);
        return builder.create();
    }
}
