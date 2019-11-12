package mobo.andro.apps.ohmagiccamera.Camera;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class FontTextView extends AppCompatTextView {
    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/Aileron-Thin.otf"), 1);
    }
}
