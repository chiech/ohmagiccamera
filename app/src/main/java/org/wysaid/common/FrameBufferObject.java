package org.wysaid.common;

import android.opengl.GLES20;
import android.util.Log;

public class FrameBufferObject {
    private int mFramebufferID;

    public FrameBufferObject() {
        int[] buf = new int[1];
        GLES20.glGenFramebuffers(1, buf, 0);
        this.mFramebufferID = buf[0];
    }

    public void release() {
        GLES20.glDeleteFramebuffers(1, new int[]{this.mFramebufferID}, 0);
    }

    public void bind() {
        GLES20.glBindFramebuffer(36160, this.mFramebufferID);
    }

    public void bindTexture(int texID) {
        bind();
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, texID, 0);
        if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
            Log.e("libCGE_java", "CGE::FrameBuffer::bindTexture2D - Frame buffer is not valid!");
        }
    }
}
