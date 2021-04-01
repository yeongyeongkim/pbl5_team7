package pt.up.fe.ssin.pexplorer.app;

import java.util.List;
import java.util.Locale;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.data.PermissionCatalog;
import pt.up.fe.ssin.pexplorer.utils.PermissionUtils;
import pt.up.fe.ssin.pexplorer.utils.ui.SimpleObjectAdapter;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.view.View;
import android.widget.TextView;

public class PermissionListAdapter extends SimpleObjectAdapter<PermissionInfo> {

    PermissionCatalog catalog;

    public PermissionListAdapter(Context context, List<PermissionInfo> objects) {
        super(context, R.layout.perm_row, objects);
        catalog = PermissionCatalog.getInstance(context);
    }

    @Override
    public View getView(View inflatedView, PermissionInfo perm) {
        String shortName = PermissionUtils.getShortName(perm);

        TextView tv = (TextView) inflatedView.findViewById(android.R.id.text1);
        tv.setText(shortName);

        tv = (TextView) inflatedView.findViewById(android.R.id.text2);
        tv.setText(perm.loadLabel(catalog.getPackageManager()));

        return inflatedView;
    }

    @Override
    public boolean isFilterMatch(CharSequence constraint, PermissionInfo perm) {
        String[] prefixes = constraint.toString()
                .toLowerCase(Locale.getDefault()).split(" ");
        String[] words = PermissionUtils.getShortName(perm).split("_");

        for (String prefix : prefixes) {
            boolean hasPrefix = false;
            for (String word : words)
                if (word.toLowerCase(Locale.getDefault()).startsWith(prefix)) {
                    hasPrefix = true;
                    break;
                }
            if (!hasPrefix)
                return false;
        }
        return true;
    }
}