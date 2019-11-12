package mobo.andro.apps.ohmagiccamera.crop;

import android.graphics.Rect;

class VerticalHandleHelper extends HandleHelper {
    private Edge mEdge;

    VerticalHandleHelper(Edge edge) {
        super(null, edge);
        this.mEdge = edge;
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = Edge.LEFT.getCoordinate();
        float top = Edge.TOP.getCoordinate();
        float right = Edge.RIGHT.getCoordinate();
        float bottom = Edge.BOTTOM.getCoordinate();
        float halfDifference = (AspectRatioUtil.calculateHeight(left, right, targetAspectRatio) - (bottom - top)) / 2.0f;
        bottom += halfDifference;
        Edge.TOP.setCoordinate(top - halfDifference);
        Edge.BOTTOM.setCoordinate(bottom);
        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge.TOP, imageRect, targetAspectRatio)) {
            Edge.BOTTOM.offset(-Edge.TOP.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
        if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge.BOTTOM, imageRect, targetAspectRatio)) {
            Edge.TOP.offset(-Edge.BOTTOM.snapToRect(imageRect));
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }
    }
}
