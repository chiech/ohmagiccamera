package org.wysaid.nativePort;

public class CGEFrameRenderer {
    protected long mNativeAddress;

    protected native void nativeBindImageFBO(long j);

    protected native long nativeCreateRenderer();

    protected native void nativeDrawCache(long j);

    protected native long nativeGetImageHandler(long j);

    protected native boolean nativeInit(long j, int i, int i2, int i3, int i4);

    protected native void nativeProcessWithFilter(long j, long j2);

    protected native int nativeQueryBufferTexture(long j);

    protected native void nativeRelease(long j);

    protected native void nativeRender(long j, int i, int i2, int i3, int i4);

    protected native void nativeRunProc(long j);

    protected native void nativeSetFilterIntensity(long j, float f);

    protected native void nativeSetFilterWithAddr(long j, long j2);

    protected native void nativeSetFilterWithConfig(long j, String str);

    protected native void nativeSetMaskFlipScale(long j, float f, float f2);

    protected native void nativeSetMaskRotation(long j, float f);

    protected native void nativeSetMaskTexture(long j, int i, float f);

    protected native void nativeSetMaskTextureRatio(long j, float f);

    protected native void nativeSetRenderFlipScale(long j, float f, float f2);

    protected native void nativeSetRenderRotation(long j, float f);

    protected native void nativeSetSrcFlipScale(long j, float f, float f2);

    protected native void nativeSetSrcRotation(long j, float f);

    protected native void nativeSrcResize(long j, int i, int i2);

    protected native void nativeUpdate(long j, int i, float[] fArr);

    static {
        NativeLibraryLoader.load();
    }

    public CGEFrameRenderer() {
        this.mNativeAddress = nativeCreateRenderer();
    }

    protected CGEFrameRenderer(int dummy) {
    }

    public boolean init(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
        if (this.mNativeAddress != 0) {
            return nativeInit(this.mNativeAddress, srcWidth, srcHeight, dstWidth, dstHeight);
        }
        return false;
    }

    public void update(int externalTexture, float[] transformMatrix) {
        if (this.mNativeAddress != 0) {
            nativeUpdate(this.mNativeAddress, externalTexture, transformMatrix);
        }
    }

    public void runProc() {
        if (this.mNativeAddress != 0) {
            nativeRunProc(this.mNativeAddress);
        }
    }

    public void render(int x, int y, int width, int height) {
        if (this.mNativeAddress != 0) {
            nativeRender(this.mNativeAddress, x, y, width, height);
        }
    }

    public void drawCache() {
        if (this.mNativeAddress != 0) {
            nativeDrawCache(this.mNativeAddress);
        }
    }

    public void setSrcRotation(float rad) {
        if (this.mNativeAddress != 0) {
            nativeSetSrcRotation(this.mNativeAddress, rad);
        }
    }

    public void setSrcFlipScale(float x, float y) {
        if (this.mNativeAddress != 0) {
            nativeSetSrcFlipScale(this.mNativeAddress, x, y);
        }
    }

    public void setRenderRotation(float rad) {
        if (this.mNativeAddress != 0) {
            nativeSetRenderRotation(this.mNativeAddress, rad);
        }
    }

    public void setRenderFlipScale(float x, float y) {
        if (this.mNativeAddress != 0) {
            nativeSetRenderFlipScale(this.mNativeAddress, x, y);
        }
    }

    public void setFilterWidthConfig(String config) {
        if (this.mNativeAddress != 0) {
            nativeSetFilterWithConfig(this.mNativeAddress, config);
        }
    }

    public void setMaskRotation(float rot) {
        if (this.mNativeAddress != 0) {
            nativeSetMaskRotation(this.mNativeAddress, rot);
        }
    }

    public void setMaskFlipScale(float x, float y) {
        if (this.mNativeAddress != 0) {
            nativeSetMaskFlipScale(this.mNativeAddress, x, y);
        }
    }

    public void setFilterIntensity(float value) {
        if (this.mNativeAddress != 0) {
            nativeSetFilterIntensity(this.mNativeAddress, value);
        }
    }

    public void srcResize(int width, int height) {
        if (this.mNativeAddress != 0) {
            nativeSrcResize(this.mNativeAddress, width, height);
        }
    }

    public void release() {
        if (this.mNativeAddress != 0) {
            nativeRelease(this.mNativeAddress);
            this.mNativeAddress = 0;
        }
    }

    public void setMaskTexture(int texID, float aspectRatio) {
        if (this.mNativeAddress != 0) {
            nativeSetMaskTexture(this.mNativeAddress, texID, aspectRatio);
        }
    }

    public void setMaskTextureRatio(float aspectRatio) {
        if (this.mNativeAddress != 0) {
            nativeSetMaskTextureRatio(this.mNativeAddress, aspectRatio);
        }
    }

    public int queryBufferTexture() {
        if (this.mNativeAddress != 0) {
            return nativeQueryBufferTexture(this.mNativeAddress);
        }
        return 0;
    }

    public long getImageHandler() {
        return nativeGetImageHandler(this.mNativeAddress);
    }

    public void bindImageFBO() {
        nativeBindImageFBO(this.mNativeAddress);
    }

    public void processWithFilter(long nativeFilter) {
        nativeProcessWithFilter(this.mNativeAddress, nativeFilter);
    }

    public void setNativeFilter(long nativeFilter) {
        nativeSetFilterWithAddr(this.mNativeAddress, nativeFilter);
    }
}
