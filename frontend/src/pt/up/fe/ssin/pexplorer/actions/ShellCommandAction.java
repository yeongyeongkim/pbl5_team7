package pt.up.fe.ssin.pexplorer.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import pt.up.fe.ssin.pexplorer.utils.ui.InputDialogBuilder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShellCommandAction extends PermissionAction {
    private static final int LINES_PER_DIALOG = Integer.MAX_VALUE;

    public ShellCommandAction() {
        super(R.string.shell_command_label, R.string.shell_command_desc,
                PermissionAction.WARN_DANGEROUS);
    }

    @Override
    protected void doAction(final Context context) {
        InputDialogBuilder builder = new InputDialogBuilder(context);
        final EditText inputText = builder.getEditText();
        builder.setTitle(R.string.shell_command_input_title);
        builder.setMessage(R.string.shell_command_input_msg);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.execute,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startCommand(context, inputText.getText().toString());
                    }
                }).setNegativeButton(R.string.cancel, null);
        builder.create().show();

    }

    private void startCommand(Context context, String command) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(
                    new String[] { "su", "-c", command });
        } catch (IOException e) {
            Toast.makeText(context, R.string.no_root, Toast.LENGTH_SHORT).show();
            return;
        }

        showOutputDialog(context, new Scanner(new BufferedReader(
                new InputStreamReader(process.getInputStream()))));
    }

    private AlertDialog showOutputDialog(final Context context, final Scanner sc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.shell_command_output);
        builder.setCancelable(false);

        View v = View.inflate(context, R.layout.console_dialog_view, null);
        ((TextView) v.findViewById(R.id.console_text)).setText(fetchText(sc));
        builder.setView(v);

        if (sc.hasNextLine())
            builder.setPositiveButton(R.string.continue_,
                    new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showOutputDialog(context, sc);
                        }
                    });
        builder.setNegativeButton(R.string.close, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sc.close();
            }
        });

        return builder.show();
    }

    private String fetchText(Scanner sc) {
        StringBuffer strBuf = new StringBuffer();
        int counter = LINES_PER_DIALOG;
        while (sc.hasNextLine() && counter-- > 0) {
            strBuf.append(sc.nextLine());
            strBuf.append('\n');
        }
        return strBuf.toString();
    }
}
