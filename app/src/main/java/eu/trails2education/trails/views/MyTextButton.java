package eu.trails2education.trails.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Ziga on 07-Jun-18.
 */

public class MyTextButton extends AppCompatButton {

    public MyTextButton(Context context) {
        super(context);
    }

    public MyTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setFont("nexa_bold.otf");
    }


    /**
     * Sets a new font for the view
     * */
    protected void setFont(String path){
        Typeface face=Typeface.createFromAsset(getContext().getAssets(), path);
        this.setTypeface(face);
    }
}
