package pt.up.fe.ssin.pexplorer.utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public class CheckedRelativeLayout extends RelativeLayout implements Checkable {

    private Checkable checkable;

    public CheckedRelativeLayout(Context context) {
        super(context);
    }

    public CheckedRelativeLayout(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public CheckedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof Checkable) {
                checkable = (Checkable) v;
                return;
            }
        }
        throw new ClassCastException(
                "No Checkable child view found in CheckedRelativeLayout.");
    }

    @Override
    public boolean isChecked() {
        return checkable.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        checkable.setChecked(checked);
    }

    @Override
    public void toggle() {
        checkable.toggle();
    }
}
