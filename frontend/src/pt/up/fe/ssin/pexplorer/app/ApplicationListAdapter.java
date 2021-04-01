package pt.up.fe.ssin.pexplorer.app;

import java.util.List;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.data.PermissionCatalog;
import pt.up.fe.ssin.pexplorer.utils.ui.SimpleObjectAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationListAdapter extends
        SimpleObjectAdapter<ApplicationInfo> {

    private PermissionCatalog catalog;

    public ApplicationListAdapter(Context context, List<ApplicationInfo> objects) {
        super(context, R.layout.app_row, objects);
        catalog = PermissionCatalog.getInstance(context);
    }

    @Override
    public View getView(View inflatedView, ApplicationInfo app) {

        TextView tv = (TextView) inflatedView.findViewById(R.id.app_name);
        tv.setText(app.loadLabel(catalog.getPackageManager()));

        ImageView iv = (ImageView) inflatedView.findViewById(R.id.app_icon);
        iv.setImageDrawable(app.loadIcon(catalog.getPackageManager()));

        tv = (TextView) inflatedView.findViewById(R.id.app_package);
        tv.setText(app.packageName);

        return inflatedView;
    }
}