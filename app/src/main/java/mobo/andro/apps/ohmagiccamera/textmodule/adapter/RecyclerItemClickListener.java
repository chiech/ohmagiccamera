package mobo.andro.apps.ohmagiccamera.textmodule.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements OnItemTouchListener {
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    /* renamed from: mobo.andro.apps.camera.textmodule.adapter.RecyclerItemClickListener$1 */
    class C09021 extends SimpleOnGestureListener {
        C09021() {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.mListener = listener;
        this.mGestureDetector = new GestureDetector(context, new C09021());
    }

    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (!(childView == null || this.mListener == null || !this.mGestureDetector.onTouchEvent(e))) {
            this.mListener.onItemClick(childView, view.getChildPosition(childView));
        }
        return false;
    }

    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
