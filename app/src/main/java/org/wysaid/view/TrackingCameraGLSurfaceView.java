package org.wysaid.view;

import android.content.Context;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;
import javax.microedition.khronos.opengles.GL10;
import org.wysaid.nativePort.CGEFrameRenderer;

public class TrackingCameraGLSurfaceView extends CameraRecordGLSurfaceView {
    protected TrackingProc mTrackingProc;

    public interface TrackingProc {
        void processTracking(CGEFrameRenderer cGEFrameRenderer);

        void release();

        void render(CGEFrameRenderer cGEFrameRenderer);

        void resize(int i, int i2);

        boolean setup(CGEFrameRenderer cGEFrameRenderer, int i, int i2);
    }

    public TrackingCameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrackingProc getTrackingProc() {
        return this.mTrackingProc;
    }

    public boolean setTrackingProc(TrackingProc proc) {
        if (this.mTrackingProc != null) {
            this.mTrackingProc.release();
            this.mTrackingProc = null;
        }
        if (proc == null || this.mFrameRecorder == null) {
            return true;
        }
        if (proc.setup(this.mFrameRecorder, this.mRecordWidth, this.mRecordHeight)) {
            this.mTrackingProc = proc;
            return true;
        }
        Log.e("libCGE_java", "setup proc failed!");
        proc.release();
        return false;
    }

    protected void onRelease() {
        super.onRelease();
        if (this.mTrackingProc != null) {
            this.mTrackingProc.release();
            this.mTrackingProc = null;
        }
    }

    public void onDrawFrame(GL10 gl) {
        if (this.mSurfaceTexture != null && cameraInstance().isPreviewing()) {
            this.mSurfaceTexture.updateTexImage();
            this.mSurfaceTexture.getTransformMatrix(this.mTransformMatrix);
            this.mFrameRecorder.update(this.mTextureID, this.mTransformMatrix);
            if (this.mTrackingProc != null) {
                this.mTrackingProc.processTracking(this.mFrameRecorder);
                this.mTrackingProc.render(this.mFrameRecorder);
            }
            this.mFrameRecorder.runProc();
            GLES20.glBindFramebuffer(36160, 0);
            GLES20.glClear(16384);
            if (this.mIsUsingMask) {
                GLES20.glEnable(3042);
                GLES20.glBlendFunc(1, 771);
            }
            this.mFrameRecorder.render(this.mDrawViewport.f34x, this.mDrawViewport.f35y, this.mDrawViewport.width, this.mDrawViewport.height);
            GLES20.glDisable(3042);
        }
    }
}
