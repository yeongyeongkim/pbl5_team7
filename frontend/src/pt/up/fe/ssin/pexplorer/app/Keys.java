package pt.up.fe.ssin.pexplorer.app;

import pt.up.fe.ssin.pexplorer.data.PermissionCatalog;
import android.content.pm.PermissionInfo;

public interface Keys {

    String INTENT_EXT_NAME = "name";

    String PREFS_FILE = "preferences";

    String PREFS_GROUPS = "groups";
    String PREFS_LEVEL = "level";
    String PREFS_RELEVANCE = "relevance";
    String PREFS_SHOW_ALL_APPS = "show_all_apps";

    String ALL_GROUPS_VALUE = "<ALL>";

    String DEFAULT_GROUPS = ALL_GROUPS_VALUE;
    int DEFAULT_LEVEL = PermissionInfo.PROTECTION_NORMAL;
    int DEFAULT_RELEVANCE = PermissionCatalog.RELEVANCE_COMMON;
    boolean DEFAULT_SHOW_ALL_APPS = false;
}
