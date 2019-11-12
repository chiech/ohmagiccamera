package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomSquareImageView extends ImageView {
    public CustomSquareImageView(Context context) {
        super(context);
    }

    public CustomSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }
}
