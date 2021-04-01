package pt.up.fe.ssin.pexplorer.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.up.fe.ssin.pexplorer.actions.AccessFineLocationAction;
import pt.up.fe.ssin.pexplorer.actions.AccessNetworkStateAction;
import pt.up.fe.ssin.pexplorer.actions.ChangeWifiStateAction;
import pt.up.fe.ssin.pexplorer.actions.GetAccountsAction;
import pt.up.fe.ssin.pexplorer.actions.InternetAccessAction;
import pt.up.fe.ssin.pexplorer.actions.PhoneCallAction;
import pt.up.fe.ssin.pexplorer.actions.ReadCalendarAction;
import pt.up.fe.ssin.pexplorer.actions.ReadContactsAction;
import pt.up.fe.ssin.pexplorer.actions.ReadPhoneStateAction;
import pt.up.fe.ssin.pexplorer.actions.RebootAction;
import pt.up.fe.ssin.pexplorer.actions.RetrieveRunningTasksAction;
import pt.up.fe.ssin.pexplorer.actions.SendTestSmsAction;
import pt.up.fe.ssin.pexplorer.actions.ShellCommandAction;
import pt.up.fe.ssin.pexplorer.actions.TakePictureAction;
import pt.up.fe.ssin.pexplorer.actions.VibrateAction;
import pt.up.fe.ssin.pexplorer.actions.WriteCalendarAction;
import pt.up.fe.ssin.pexplorer.actions.WriteExternalStorageAction;
import pt.up.fe.ssin.pexplorer.actions.WriteSettingsAction;

public class ActionRegistry {

    private static ActionRegistry instance;

    public static ActionRegistry getInstance() {
        if (instance == null)
            instance = new ActionRegistry();
        return instance;
    }

    private Map<String, List<PermissionAction>> actions = new HashMap<String, List<PermissionAction>>();

    // register all actions implemented for each permission
    static {
        getInstance().addAction("android.permission.SEND_SMS",
                new SendTestSmsAction());
        getInstance().addAction("android.permission.CALL_PHONE",
                new PhoneCallAction());
        getInstance().addAction("android.permission.READ_CONTACTS",
                new ReadContactsAction());
        getInstance().addAction("android.permission.READ_PHONE_STATE",
                new ReadPhoneStateAction());
        getInstance().addAction("android.permission.ACCESS_FINE_LOCATION",
                new AccessFineLocationAction());
        getInstance().addAction("android.permission.INTERNET",
                new InternetAccessAction());
        getInstance().addAction("android.permission.ACCESS_NETWORK_STATE",
                new AccessNetworkStateAction());
        getInstance().addAction("android.permission.CHANGE_WIFI_STATE",
                new ChangeWifiStateAction());
        getInstance().addAction("android.permission.WRITE_SETTINGS",
                new WriteSettingsAction());
        getInstance().addAction("android.permission.WRITE_EXTERNAL_STORAGE",
                new WriteExternalStorageAction());
        getInstance().addAction("android.permission.VIBRATE",
                new VibrateAction());
        getInstance().addAction("android.permission.CAMERA",
                new TakePictureAction());
        getInstance().addAction("android.permission.READ_CALENDAR",
                new ReadCalendarAction());
        getInstance().addAction("android.permission.WRITE_CALENDAR",
                new WriteCalendarAction());
        getInstance().addAction("android.permission.GET_TASKS",
                new RetrieveRunningTasksAction());
        getInstance().addAction("android.permission.GET_ACCOUNTS",
                new GetAccountsAction());
        getInstance().addAction("com.noshufou.android.su.provider.READ",
                new RebootAction(), new ShellCommandAction());
        getInstance().addAction("com.noshufou.android.su.provider.WRITE",
                new RebootAction(), new ShellCommandAction());
    }

    private ActionRegistry() {
    }

    public List<PermissionAction> getPermissionActions(String permName) {
        if (actions.containsKey(permName))
            return actions.get(permName);

        List<PermissionAction> permActions = new ArrayList<PermissionAction>();
        actions.put(permName, permActions);
        return permActions;
    }

    public void addAction(String permName, PermissionAction... actions) {
        for (PermissionAction action : actions)
            addAction(permName, action, -1);
    }

    public void addAction(String permName, PermissionAction action, int position) {
        List<PermissionAction> permActions;
        if (!actions.containsKey(permName)) {
            permActions = new ArrayList<PermissionAction>();
            actions.put(permName, permActions);
        } else
            permActions = actions.get(permName);

        if (position >= 0)
            permActions.add(position, action);
        else
            permActions.add(action);
    }
}
