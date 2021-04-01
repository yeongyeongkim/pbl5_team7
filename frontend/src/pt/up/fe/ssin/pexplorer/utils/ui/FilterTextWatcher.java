package pt.up.fe.ssin.pexplorer.utils.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Filterable;

public class FilterTextWatcher implements TextWatcher {
    private Filterable filterable;

    public FilterTextWatcher(Filterable filterable) {
        this.filterable = filterable;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterable.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
    }
}
