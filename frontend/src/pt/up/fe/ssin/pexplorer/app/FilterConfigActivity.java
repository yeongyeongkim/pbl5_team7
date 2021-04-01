package pt.up.fe.ssin.pexplorer.app;

import java.util.Arrays;
import java.util.List;

import pt.up.fe.ssin.pexplorer.R;
import pt.up.fe.ssin.pexplorer.data.PermissionCatalog;
import pt.up.fe.ssin.pexplorer.utils.PermissionUtils;
import pt.up.fe.ssin.pexplorer.utils.ui.SimpleObjectAdapter;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PermissionGroupInfo;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

public class FilterConfigActivity extends ListActivity {

    private static final String TAB_SPEC_GROUP = "group";
    private static final String TAB_SPEC_LEVEL = "level";
    private static final String TAB_SPEC_RELEVANCE = "relevance";

    private PermissionCatalog catalog;

    private String selectedGroupNames;
    private List<PermissionGroupInfo> groups;

    private int selectedLevel = 0;
    private int numLevels;

    private int selectedRelevance = 0;
    private int numRelevances;

    private TabHost tabHost;
    private PermissionGroupListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        catalog = PermissionCatalog.getInstance(this);
        SharedPreferences prefs = getSharedPreferences(Keys.PREFS_FILE,
                MODE_PRIVATE);

        selectedGroupNames = prefs.getString(Keys.PREFS_GROUPS,
                Keys.DEFAULT_GROUPS);
        groups = catalog.getAllGroups();
        selectedLevel = prefs.getInt(Keys.PREFS_LEVEL, Keys.DEFAULT_LEVEL);
        numLevels = catalog.getNumberOfLevels();
        selectedRelevance = prefs.getInt(Keys.PREFS_RELEVANCE,
                Keys.DEFAULT_RELEVANCE);
        numRelevances = catalog.getNumberOfRelevances();

        drawActivity();
    }

    private void drawActivity() {
        setContentView(R.layout.filter_config);
        drawTabs();

        adapter = new PermissionGroupListAdapter(this, groups);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        setSelectedGroups();

        RadioGroup rg = (RadioGroup) findViewById(R.id.level);
        for (int i = 0; i < numLevels; i++) {
            RadioButton rb = (RadioButton) View.inflate(this,
                    R.layout.filter_config_radio, null);
            rb.setId(i);
            rb.setText(getResources().getStringArray(R.array.level_labels)[i]);
            if (i == selectedLevel)
                rb.setChecked(true);
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedLevel = checkedId;
                updateLevelDescription();
            }
        });
        updateLevelDescription();

        rg = (RadioGroup) findViewById(R.id.relevance);
        for (int i = 0; i < numRelevances; i++) {
            RadioButton rb = (RadioButton) View.inflate(this,
                    R.layout.filter_config_radio, null);
            rb.setId(i);
            rb.setText(getResources().getStringArray(R.array.relevance_labels)[i]);
            if (i == selectedRelevance)
                rb.setChecked(true);
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedRelevance = checkedId;
                updateRelevanceDescription();
            }
        });
        updateRelevanceDescription();
    }

    private void drawTabs() {
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        tabHost.addTab(tabHost
                .newTabSpec(TAB_SPEC_GROUP)
                .setContent(android.R.id.list)
                .setIndicator(getString(R.string.tab_filter_groups),
                        getResources().getDrawable(R.drawable.ic_tab_group)));

        tabHost.addTab(tabHost
                .newTabSpec(TAB_SPEC_LEVEL)
                .setContent(R.id.filter_level)
                .setIndicator(getString(R.string.tab_filter_level),
                        getResources().getDrawable(R.drawable.ic_tab_level)));

        tabHost.addTab(tabHost
                .newTabSpec(TAB_SPEC_RELEVANCE)
                .setContent(R.id.filter_relevance)
                .setIndicator(getString(R.string.tab_filter_relevance),
                        getResources().getDrawable(R.drawable.ic_tab_relevance)));
    }

    private void setSelectedGroups() {
        boolean isAll = selectedGroupNames.equals(Keys.ALL_GROUPS_VALUE);
        List<String> groupNames = Arrays.asList(selectedGroupNames.split(";"));

        for (int i = 0; i < groups.size(); i++)
            if (isAll || groupNames.contains(groups.get(i).name))
                getListView().setItemChecked(i, true);
    }

    private void updateLevelDescription() {
        TextView tv = (TextView) findViewById(R.id.level_description);
        tv.setText(getResources().getStringArray(R.array.level_descriptions)[selectedLevel]);
    }

    private void updateRelevanceDescription() {
        TextView tv = (TextView) findViewById(R.id.relevance_description);
        tv.setText(getResources()
                .getStringArray(R.array.relevance_descriptions)[selectedRelevance]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_group, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return tabHost.getCurrentTabTag().equals(TAB_SPEC_GROUP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case R.id.check_all: {
            for (int i = 0; i < getListAdapter().getCount(); i++)
                getListView().setItemChecked(i, true);
            return true;
        }

        case R.id.uncheck_all: {
            for (int i = 0; i < getListAdapter().getCount(); i++)
                getListView().setItemChecked(i, false);
            return true;
        }

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        SparseBooleanArray selectedItems = getListView()
                .getCheckedItemPositions();
        StringBuffer groupsStrBuf = new StringBuffer();
        for (int i = 0; i < groups.size(); i++)
            if (selectedItems.get(i, false)) {
                if (groupsStrBuf.length() != 0)
                    groupsStrBuf.append(';');
                groupsStrBuf.append(groups.get(i).name);
            }

        SharedPreferences.Editor prefs = getSharedPreferences(Keys.PREFS_FILE,
                MODE_PRIVATE).edit();

        prefs.putString(Keys.PREFS_GROUPS, groupsStrBuf.toString());
        prefs.putInt(Keys.PREFS_LEVEL, selectedLevel);
        prefs.putInt(Keys.PREFS_RELEVANCE, selectedRelevance);
        prefs.commit();

        setResult(RESULT_OK, new Intent());
        super.finish();
    }

    public class PermissionGroupListAdapter extends
            SimpleObjectAdapter<PermissionGroupInfo> {

        public PermissionGroupListAdapter(Context context,
                List<PermissionGroupInfo> objects) {
            super(context, R.layout.simple_list_item_2_multiple_choice, objects);
        }

        @Override
        public View getView(View inflatedView, PermissionGroupInfo group) {
            String name = PermissionUtils.getShortName(group);

            TextView tv = (TextView) inflatedView
                    .findViewById(android.R.id.text1);
            tv.setText(name);

            tv = (TextView) inflatedView.findViewById(android.R.id.text2);
            tv.setText(group.loadDescription(catalog.getPackageManager()));

            return inflatedView;
        }
    }
}
