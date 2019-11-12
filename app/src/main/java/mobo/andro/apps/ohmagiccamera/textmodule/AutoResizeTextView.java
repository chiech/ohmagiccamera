package mobo.andro.apps.ohmagiccamera.textmodule;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

public class AutoResizeTextView extends AppCompatTextView {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private float _minTextSize;
    private TextPaint _paint;
    private final SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private int _widthLimit;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutoResizeTextView$1 */
    class C08781 implements SizeTester {
        final RectF textRect = new RectF();

        C08781() {
        }

        @TargetApi(16)
        public int onTestSize(int suggestedSize, RectF availableSpace) {
            String text;
            AutoResizeTextView.this._paint.setTextSize((float) suggestedSize);
            TransformationMethod transformationMethod = AutoResizeTextView.this.getTransformationMethod();
            if (transformationMethod != null) {
                text = transformationMethod.getTransformation(AutoResizeTextView.this.getText(), AutoResizeTextView.this).toString();
            } else {
                text = AutoResizeTextView.this.getText().toString();
            }
            if (AutoResizeTextView.this.getMaxLines() == 1) {
                this.textRect.bottom = AutoResizeTextView.this._paint.getFontSpacing();
                this.textRect.right = AutoResizeTextView.this._paint.measureText(text);
            } else {
                StaticLayout layout = new StaticLayout(text, AutoResizeTextView.this._paint, AutoResizeTextView.this._widthLimit, Alignment.ALIGN_NORMAL, AutoResizeTextView.this._spacingMult, AutoResizeTextView.this._spacingAdd, true);
                if (AutoResizeTextView.this.getMaxLines() != -1 && layout.getLineCount() > AutoResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) layout.getHeight();
                int maxWidth = -1;
                int lineCount = layout.getLineCount();
                for (int i = 0; i < lineCount; i++) {
                    int end = layout.getLineEnd(i);
                    if (i < lineCount - 1 && end > 0 && !AutoResizeTextView.this.isValidWordWrap(text.charAt(end - 1), text.charAt(end))) {
                        return 1;
                    }
                    if (((float) maxWidth) < layout.getLineRight(i) - layout.getLineLeft(i)) {
                        maxWidth = ((int) layout.getLineRight(i)) - ((int) layout.getLineLeft(i));
                    }
                }
                this.textRect.right = (float) maxWidth;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            if (availableSpace.contains(this.textRect)) {
                return -1;
            }
            return 1;
        }
    }

    public AutoResizeTextView(Context context) {
        this(context, null, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        this._minTextSize = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());
        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new C08781();
        this._initialized = true;
    }

    public boolean isValidWordWrap(char before, char after) {
        return before == ' ' || before == '-';
    }

    public void setAllCaps(boolean allCaps) {
        super.setAllCaps(allCaps);
        adjustTextSize();
    }

    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
        adjustTextSize();
    }

    public void setTextSize(float size) {
        this._maxTextSize = size;
        adjustTextSize();
    }

    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        this._maxLines = maxLines;
        adjustTextSize();
    }

    public int getMaxLines() {
        return this._maxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        adjustTextSize();
    }

    public void setSingleLine(boolean singleLine) {
        super.setSingleLine(singleLine);
        if (singleLine) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        adjustTextSize();
    }

    public void setLines(int lines) {
        super.setLines(lines);
        this._maxLines = lines;
        adjustTextSize();
    }

    public void setTextSize(int unit, float size) {
        Resources r;
        Context c = getContext();
        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
        adjustTextSize();
    }

    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        this._spacingMult = mult;
        this._spacingAdd = add;
    }

    public void setMinTextSize(float minTextSize) {
        this._minTextSize = minTextSize;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int startSize = (int) this._minTextSize;
            int heightLimit = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._widthLimit > 0) {
                this._paint = new TextPaint(getPaint());
                this._availableSpaceRect.right = (float) this._widthLimit;
                this._availableSpaceRect.bottom = (float) heightLimit;
                superSetTextSize(startSize);
            }
        }
    }

    private void superSetTextSize(int startSize) {
        super.setTextSize(0, (float) binarySearch(startSize, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;
            int midValCmp = sizeTester.onTestSize(i, availableSpace);
            if (midValCmp < 0) {
                lastBest = lo;
                lo = i + 1;
            } else if (midValCmp <= 0) {
                return i;
            } else {
                hi = i - 1;
                lastBest = hi;
            }
        }
        return lastBest;
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        adjustTextSize();
    }

    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);
        if (width != oldwidth || height != oldheight) {
            adjustTextSize();
        }
    }
}
