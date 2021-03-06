package hu.velorum;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import hu.velorum.ConCopy.R;

import java.io.Serializable;

public class RoboButton extends Button implements Serializable {

	private static final long serialVersionUID = -7816685707888388856L;

	private String ttfName = "Regular";

	public RoboButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupFont(attrs);
	}

	public RoboButton(Context context) {
		super(context);
	}

	public RoboButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupFont(attrs);
	}

    private void setupFont(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RobotoTextView);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.RobotoTextView_ttf: {
                    ttfName = a.getString(i);
                }
                break;
            }
        }
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-" + ttfName + ".ttf");
        setTypeface(font);
    }

    public void setFont(String font) {
        ttfName = font;
        init();
    }
}
