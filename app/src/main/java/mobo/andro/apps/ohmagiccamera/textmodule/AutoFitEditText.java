package mobo.andro.apps.ohmagiccamera.textmodule;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.widget.EditText;

public class AutoFitEditText extends EditText {
    private static final int NO_LINE_LIMIT = -1;
    private final RectF _availableSpaceRect;
    private boolean _enableSizeCache;
    private boolean _initiallized;
    private int _maxLines;
    private float _maxTextSize;
    private Float _minTextSize;
    private SizeTester _sizeTester;
    private float _spacingAdd;
    private float _spacingMult;
    private final SparseIntArray _textCachedSizes;
    private int _widthLimit;
    private TextPaint paint;

    private interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.AutoFitEditText$1 */
    class C08771 implements SizeTester {
        final RectF textRect = new RectF();

        C08771() {
        }

        @TargetApi(16)
        public int onTestSize(int suggestedSize, RectF availableSPace) {
            AutoFitEditText.this.paint.setTextSize((float) suggestedSize);
            String text = AutoFitEditText.this.getText().toString();
            if (AutoFitEditText.this.getMaxLines() == 1) {
                this.textRect.bottom = AutoFitEditText.this.paint.getFontSpacing();
                this.textRect.right = AutoFitEditText.this.paint.measureText(text);
            } else {
                StaticLayout layout = new StaticLayout(text, AutoFitEditText.this.paint, AutoFitEditText.this._widthLimit, Alignment.ALIGN_NORMAL, AutoFitEditText.this._spacingMult, AutoFitEditText.this._spacingAdd, true);
                if (AutoFitEditText.this.getMaxLines() != -1 && layout.getLineCount() > AutoFitEditText.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) layout.getHeight();
                int maxWidth = -1;
                for (int i = 0; i < layout.getLineCount(); i++) {
                    if (((float) maxWidth) < layout.getLineWidth(i)) {
                        maxWidth = (int) layout.getLineWidth(i);
                    }
                }
                this.textRect.right = (float) maxWidth;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            if (availableSPace.contains(this.textRect)) {
                return -1;
            }
            return 1;
        }
    }

    public AutoFitEditText(Context context) {
        this(context, null, 0);
    }

    public AutoFitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this._availableSpaceRect = new RectF();
        this._textCachedSizes = new SparseIntArray();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._enableSizeCache = true;
        this._initiallized = false;
        try {
            this._minTextSize = Float.valueOf(TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics()));
            this._maxTextSize = getTextSize();
            if (this._maxLines == 0) {
                this._maxLines = -1;
            }
            this._sizeTester = new C08771();
            this._initiallized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTypeface(Typeface tf) {
        if (this.paint == null) {
            this.paint = new TextPaint(getPaint());
        }
        this.paint.setTypeface(tf);
        super.setTypeface(tf);
    }

    public void setTextSize(float size) {
        this._maxTextSize = size;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        this._maxLines = maxlines;
        reAdjust();
    }

    public int getMaxLines() {
        return this._maxLines;
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        reAdjust();
    }

    public void setSingleLine(boolean singleLine) {
        super.setSingleLine(singleLine);
        if (singleLine) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        reAdjust();
    }

    public void setLines(int lines) {
        super.setLines(lines);
        this._maxLines = lines;
        reAdjust();
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
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        this._spacingMult = mult;
        this._spacingAdd = add;
    }

    public void setMinTextSize(Float minTextSize) {
        this._minTextSize = minTextSize;
        reAdjust();
    }

    public Float get_minTextSize() {
        return this._minTextSize;
    }

    private void reAdjust() {
        adjustTextSize();
    }

    private void adjustTextSize() {
        try {
            if (this._initiallized) {
                int startSize = Math.round(this._minTextSize.floatValue());
                int heightLimit = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
                this._widthLimit = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
                if (this._widthLimit > 0) {
                    this._availableSpaceRect.right = (float) this._widthLimit;
                    this._availableSpaceRect.bottom = (float) heightLimit;
                    super.setTextSize(0, (float) efficientTextSizeSearch(startSize, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEnableSizeCache(boolean enable) {
        this._enableSizeCache = enable;
        this._textCachedSizes.clear();
        adjustTextSize();
    }

    private int efficientTextSizeSearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        if (!this._enableSizeCache) {
            return binarySearch(start, end, sizeTester, availableSpace);
        }
        String text = getText().toString();
        int key = text == null ? 0 : text.length();
        int size = this._textCachedSizes.get(key);
        if (size != 0) {
            return size;
        }
        size = binarySearch(start, end, sizeTester, availableSpace);
        this._textCachedSizes.put(key, size);
        return size;
    }

    private int binarySearch(int start, int end, SizeTester sizeTester, RectF availableSpace) {
        int lastBest = start;
        int lo = start;
        int hi = end - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            try {
                int midValCmp = sizeTester.onTestSize(mid, availableSpace);
                if (midValCmp < 0) {
                    lastBest = lo;
                    lo = mid + 1;
                } else if (midValCmp <= 0) {
                    return mid;
                } else {
                    hi = mid - 1;
                    lastBest = hi;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return lastBest;
            }
        }
        return lastBest;
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        reAdjust();
    }

    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        this._textCachedSizes.clear();
        super.onSizeChanged(width, height, oldwidth, oldheight);
        if (width != oldwidth || height != oldheight) {
            reAdjust();
        }
    }
}
