package eu.trails2education.trails.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Žiga on 19. 09. 2017.
 *
 * Creates a view with a custom font
 */

public class MyTextViewBold extends MyTextView {

    public MyTextViewBold(Context context) {
        super(context);
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setFont("nexa_bold.otf");
    }
}