package pt.up.fe.ssin.pexplorer.actions;

import java.util.Iterator;
import java.util.List;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.app.PermissionAction;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class RetrieveRunningTasksAction extends PermissionAction {

    public RetrieveRunningTasksAction() {
        super(R.string.retrive_running_tasks_label,
                R.string.retrive_running_tasks_label,
                PermissionAction.DO_NOTHING);
    }

    @Override
    protected void doAction(final Context context) {
        ActivityManager mgr = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningTaskInfo> tasks = mgr.getRunningTasks(100);

        String text = context.getString(R.string.retrieve_running_tasks_intro);

        for (Iterator<RunningTaskInfo> i = tasks.iterator(); i.hasNext();) {
            RunningTaskInfo p = (RunningTaskInfo) i.next();
            text += p.baseActivity.flattenToShortString() + "\n";
        }

        new AlertDialog.Builder(context)
                .setTitle(R.string.retrive_running_tasks_label)
                .setMessage(text)
                .setCancelable(true)
                .setPositiveButton(R.string.continue_,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }
}
