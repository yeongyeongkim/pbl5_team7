package pt.up.fe.ssin.pexplorer.utils.ui;

import pt.up.fe.ssin.pexplorer.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InputDialogBuilder extends Builder {

    private View content;

    public InputDialogBuilder(Context context) {
        super(context);
        buildBase(context);
    }

    public EditText getEditText() {
        return (EditText) content.findViewById(R.id.input);
    }

    @Override
    public Builder setMessage(int messageId) {
        return setMessage(content.getContext().getString(messageId));
    }

    @Override
    public Builder setMessage(CharSequence message) {
        ((TextView) content.findViewById(R.id.label)).setText(message);
        return this;
    }

    private void buildBase(Context context) {
        content = View.inflate(context, R.layout.input_dialog, null);
        setCancelable(true);
        setView(content);
    }
}
