package pt.up.fe.ssin.pexplorer.utils.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class SimpleObjectAdapter<T> extends BaseAdapter implements Filterable {

    protected Context context;
    protected List<T> objects;
    protected List<T> originalObjects;
    protected int rowId;

    protected Filter filter;

    public SimpleObjectAdapter(Context context, int rowId, List<T> objects) {
        this.context = context;
        this.originalObjects = objects;
        this.objects = originalObjects;
        this.rowId = rowId;
    }

    public SimpleObjectAdapter(Context context, int rowId, T[] objects) {
        this(context, rowId, Arrays.asList(objects));
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, rowId, null);
        return getView(convertView, objects.get(position));
    }

    public View getView(View inflatedView, T object) {
        if (inflatedView instanceof TextView)
            ((TextView) inflatedView).setText(object.toString());
        return inflatedView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new ObjectFilter();
        return filter;
    }

    public boolean isFilterMatch(CharSequence constraint, T obj) {
        return obj != null
                && obj.toString()
                        .toUpperCase(Locale.getDefault())
                        .startsWith(
                                constraint.toString().toUpperCase(
                                        Locale.getDefault()));
    }

    private class ObjectFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<T> resultList = new ArrayList<T>();

            for (T obj : originalObjects)
                if (isFilterMatch(constraint, obj))
                    resultList.add(obj);

            results.values = resultList;
            results.count = resultList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                FilterResults results) {
            objects = (List<T>) results.values;
            if (results.count > 0)
                notifyDataSetChanged();
            else
                notifyDataSetInvalidated();
        }
    }
}
