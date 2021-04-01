package pt.up.fe.ssin.pexplorer.utils.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NonEventBlockingScrollView extends ScrollView {

    public NonEventBlockingScrollView(Context context) {
        super(context);
    }

    public NonEventBlockingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonEventBlockingScrollView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}