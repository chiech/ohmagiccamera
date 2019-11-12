package org.wysaid.common;

import android.annotation.SuppressLint;
import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint({"InlinedApi"})
public class SharedContext {
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final String LOG_TAG = "libCGE_java";
    private static int mBitsA = 8;
    private static int mBitsB = 8;
    private static int mBitsG = 8;
    private static int mBitsR = 8;
    private EGLConfig mConfig;
    private EGLContext mContext;
    private EGLDisplay mDisplay;
    private EGL10 mEgl;
    private GL10 mGl;
    private EGLSurface mSurface;

    public static void setContextColorBits(int r, int g, int b, int a) {
        mBitsR = r;
        mBitsG = g;
        mBitsB = b;
        mBitsA = a;
    }

    public static SharedContext create() {
        return create(EGL10.EGL_NO_CONTEXT, 64, 64, 1, null);
    }

    public static SharedContext create(int width, int height) {
        return create(EGL10.EGL_NO_CONTEXT, width, height, 1, null);
    }

    public static SharedContext create(EGLContext context, int width, int height) {
        return create(context, width, height, 1, null);
    }

    public static SharedContext create(EGLContext context, int width, int height, int contextType, Object obj) {
        SharedContext sharedContext = new SharedContext();
        if (sharedContext.initEGL(context, width, height, contextType, obj)) {
            return sharedContext;
        }
        sharedContext.release();
        return null;
    }

    public EGLContext getContext() {
        return this.mContext;
    }

    public EGLDisplay getDisplay() {
        return this.mDisplay;
    }

    public EGLSurface getSurface() {
        return this.mSurface;
    }

    public EGL10 getEGL() {
        return this.mEgl;
    }

    public GL10 getGL() {
        return this.mGl;
    }

    SharedContext() {
    }

    public void release() {
        Log.i("libCGE_java", "#### CGESharedGLContext Destroying context... ####");
        if (this.mDisplay != EGL10.EGL_NO_DISPLAY) {
            this.mEgl.eglMakeCurrent(this.mDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.mEgl.eglDestroyContext(this.mDisplay, this.mContext);
            this.mEgl.eglDestroySurface(this.mDisplay, this.mSurface);
            this.mEgl.eglTerminate(this.mDisplay);
        }
        this.mDisplay = EGL10.EGL_NO_DISPLAY;
        this.mSurface = EGL10.EGL_NO_SURFACE;
        this.mContext = EGL10.EGL_NO_CONTEXT;
    }

    public void makeCurrent() {
        if (!this.mEgl.eglMakeCurrent(this.mDisplay, this.mSurface, this.mSurface, this.mContext)) {
            Log.e("libCGE_java", "eglMakeCurrent failed:" + this.mEgl.eglGetError());
        }
    }

    public boolean swapBuffers() {
        return this.mEgl.eglSwapBuffers(this.mDisplay, this.mSurface);
    }

    private boolean initEGL(EGLContext context, int width, int height, int contextType, Object obj) {
        int[] contextAttribList = new int[]{12440, 2, 12344};
        int[] configSpec = new int[]{12339, contextType, 12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344};
        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfig = new int[1];
        int[] version = new int[2];
        int[] surfaceAttribList = new int[]{12375, width, 12374, height, 12344};
        this.mEgl = (EGL10) EGLContext.getEGL();
        EGLDisplay eglGetDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.mDisplay = eglGetDisplay;
        if (eglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            Log.e("libCGE_java", String.format("eglGetDisplay() returned error 0x%x", new Object[]{Integer.valueOf(this.mEgl.eglGetError())}));
            return false;
        } else if (this.mEgl.eglInitialize(this.mDisplay, version)) {
            Log.i("libCGE_java", String.format("eglInitialize - major: %d, minor: %d", new Object[]{Integer.valueOf(version[0]), Integer.valueOf(version[1])}));
            if (this.mEgl.eglChooseConfig(this.mDisplay, configSpec, configs, 1, numConfig)) {
                String str = "libCGE_java";
                String str2 = "Config num: %d, has sharedContext: %s";
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(numConfig[0]);
                objArr[1] = context == EGL10.EGL_NO_CONTEXT ? "NO" : "YES";
                Log.i(str, String.format(str2, objArr));
                this.mConfig = configs[0];
                this.mContext = this.mEgl.eglCreateContext(this.mDisplay, this.mConfig, context, contextAttribList);
                if (this.mContext == EGL10.EGL_NO_CONTEXT) {
                    Log.e("libCGE_java", "eglCreateContext Failed!");
                    return false;
                }
                switch (contextType) {
                    case 1:
                    case EGL_RECORDABLE_ANDROID /*12610*/:
                        this.mSurface = this.mEgl.eglCreatePbufferSurface(this.mDisplay, this.mConfig, surfaceAttribList);
                        break;
                    case 2:
                        this.mSurface = this.mEgl.eglCreatePixmapSurface(this.mDisplay, this.mConfig, obj, surfaceAttribList);
                        break;
                    case 4:
                        this.mSurface = this.mEgl.eglCreateWindowSurface(this.mDisplay, this.mConfig, obj, surfaceAttribList);
                        break;
                }
                if (this.mSurface == EGL10.EGL_NO_SURFACE) {
                    Log.e("libCGE_java", "eglCreatePbufferSurface Failed!");
                    return false;
                } else if (this.mEgl.eglMakeCurrent(this.mDisplay, this.mSurface, this.mSurface, this.mContext)) {
                    int[] clientVersion = new int[1];
                    this.mEgl.eglQueryContext(this.mDisplay, this.mContext, 12440, clientVersion);
                    Log.i("libCGE_java", "EGLContext created, client version " + clientVersion[0]);
                    this.mGl = (GL10) this.mContext.getGL();
                    return true;
                } else {
                    Log.e("libCGE_java", "eglMakeCurrent failed:" + this.mEgl.eglGetError());
                    return false;
                }
            }
            Log.e("libCGE_java", String.format("eglChooseConfig() returned error 0x%x", new Object[]{Integer.valueOf(this.mEgl.eglGetError())}));
            return false;
        } else {
            Log.e("libCGE_java", String.format("eglInitialize() returned error 0x%x", new Object[]{Integer.valueOf(this.mEgl.eglGetError())}));
            return false;
        }
    }
}
