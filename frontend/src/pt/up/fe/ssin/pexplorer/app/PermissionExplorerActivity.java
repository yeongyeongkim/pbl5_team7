package pt.up.fe.ssin.pexplorer.app;

import java.util.List;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.data.PermissionCatalog;
import pt.up.fe.ssin.pexplorer.utils.ui.FilterTextWatcher;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PermissionInfo;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PermissionExplorerActivity extends ListActivity {

    public static final int FILTER_REQ_CODE = 0;

    private PermissionCatalog catalog;
    private List<PermissionInfo> permissions;
    private PermissionListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        catalog = PermissionCatalog.getInstance(this);
        loadPreferences();

        drawActivity();
    }

    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(Keys.PREFS_FILE,
                MODE_PRIVATE);

        String[] groupNames = prefs.contains(Keys.PREFS_GROUPS) ? prefs
                .getString(Keys.PREFS_GROUPS, null).split(";") : null;
        int level = prefs.getInt(Keys.PREFS_LEVEL, Keys.DEFAULT_LEVEL);
        int relevance = prefs.getInt(Keys.PREFS_RELEVANCE,
                Keys.DEFAULT_RELEVANCE);

        permissions = catalog.filter(catalog.getAll(), groupNames, level,
                relevance);
    }

    private void drawActivity() {
        setContentView(R.layout.perm_list);

        adapter = new PermissionListAdapter(this, permissions);
        adapter.registerDataSetObserver(new ListCounterDataSetObserver());
        setListAdapter(adapter);

        EditText filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(new FilterTextWatcher(adapter));

        updateListCounter();
    }

    private void updateListCounter() {
        TextView tv = (TextView) findViewById(R.id.list_count);
        tv.setText(String.format(getString(R.string.msg_perm_list_count),
                adapter.getCount()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        PermissionInfo perm = (PermissionInfo) getListAdapter().getItem(
                position);
        Intent intent = new Intent(this, PermissionInfoActivity.class);
        intent.putExtra(Keys.INTENT_EXT_NAME, perm.name);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perm_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case R.id.filter: {
            Intent intent = new Intent(this, FilterConfigActivity.class);
            startActivityForResult(intent, FILTER_REQ_CODE);
            return true;
        }

        case R.id.reload: {
            PermissionCatalog.getInstance(this).reload();
            Intent intent = new Intent(this, PermissionExplorerActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

        case FILTER_REQ_CODE:
            if (resultCode != RESULT_OK)
                return;
            loadPreferences();
            drawActivity();
            return;

        default:
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    private class ListCounterDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            updateListCounter();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            updateListCounter();
        }
    }
}