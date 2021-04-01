package pt.up.fe.ssin.pexplorer.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class WriteExternalStorageAction extends PermissionAction {

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public WriteExternalStorageAction() {
        super(R.string.write_external_storage_label,
                R.string.write_external_storage_desc, PermissionAction.WARN);
    }

    @Override
    protected void doAction(final Context context) {
        try {
            File root = Environment.getExternalStorageDirectory();
            if (root.canWrite()) {
                File PermissionExplorer = new File(root,
                        "PermissionExplorer.txt");
                FileWriter permissionWriter = new FileWriter(PermissionExplorer);
                BufferedWriter out = new BufferedWriter(permissionWriter);
                Cursor cc = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, null);
                if (cc.getCount() > 0)
                    while (cc.moveToNext())
                        out.write(cc.getString(cc
                                .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                                + "\n");
                else
                    out.write(R.string.write_external_file_no_photos);
                out.close();
            }
        } catch (IOException e) {

        }

        Toast.makeText(context, R.string.write_external_file_storage,
                Toast.LENGTH_SHORT).show();
    }
}
