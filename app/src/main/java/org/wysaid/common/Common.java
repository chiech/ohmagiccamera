package org.wysaid.common;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.nio.FloatBuffer;

public class Common {
    public static final boolean DEBUG = true;
    public static final float[] FULLSCREEN_VERTICES = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    public static final String LOG_TAG = "libCGE_java";

    public static void checkGLError(String tag) {
        int err = GLES20.glGetError();
        for (int loopCnt = 0; loopCnt < 32 && err != 0; loopCnt++) {
            String msg;
            switch (err) {
                case 1280:
                    msg = "invalid enum";
                    break;
                case 1281:
                    msg = "invalid value";
                    break;
                case 1282:
                    msg = "invalid operation";
                    break;
                case 1285:
                    msg = "out of memory";
                    break;
                case 1286:
                    msg = "invalid framebuffer operation";
                    break;
                default:
                    msg = "unknown error";
                    break;
            }
            Log.e("libCGE_java", String.format("After tag \"%s\" glGetError %s(0x%x) ", new Object[]{tag, msg, Integer.valueOf(err)}));
            err = GLES20.glGetError();
        }
    }

    private static void _texParamHelper(int type, int filter, int wrap) {
        GLES20.glTexParameterf(type, 10241, (float) filter);
        GLES20.glTexParameterf(type, 10240, (float) filter);
        GLES20.glTexParameteri(type, 10242, wrap);
        GLES20.glTexParameteri(type, 10243, wrap);
    }

    public static int genBlankTextureID(int width, int height) {
        return genBlankTextureID(width, height, 9729, 33071);
    }

    public static int genBlankTextureID(int width, int height, int filter, int wrap) {
        int[] texID = new int[1];
        GLES20.glGenTextures(1, texID, 0);
        GLES20.glBindTexture(3553, texID[0]);
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, null);
        _texParamHelper(3553, filter, wrap);
        return texID[0];
    }

    public static int genNormalTextureID(Bitmap bmp) {
        return genNormalTextureID(bmp, 9729, 33071);
    }

    public static int genNormalTextureID(Bitmap bmp, int filter, int wrap) {
        int[] texID = new int[1];
        GLES20.glGenTextures(1, texID, 0);
        GLES20.glBindTexture(3553, texID[0]);
        GLUtils.texImage2D(3553, 0, bmp, 0);
        _texParamHelper(3553, filter, wrap);
        return texID[0];
    }

    public static int genSurfaceTextureID() {
        int[] texID = new int[1];
        GLES20.glGenTextures(1, texID, 0);
        GLES20.glBindTexture(36197, texID[0]);
        _texParamHelper(36197, 9729, 33071);
        return texID[0];
    }

    public static void deleteTextureID(int texID) {
        GLES20.glDeleteTextures(1, new int[]{texID}, 0);
    }

    public static int genFullscreenVertexArrayBuffer() {
        int[] vertexBuffer = new int[1];
        GLES20.glGenBuffers(1, vertexBuffer, 0);
        if (vertexBuffer[0] == 0) {
            Log.e("libCGE_java", "Invalid VertexBuffer! You must call this within an OpenGL thread!");
            return 0;
        }
        GLES20.glBindBuffer(34962, vertexBuffer[0]);
        FloatBuffer buffer = FloatBuffer.allocate(FULLSCREEN_VERTICES.length);
        buffer.put(FULLSCREEN_VERTICES).position(0);
        GLES20.glBufferData(34962, 32, buffer, 35044);
        return vertexBuffer[0];
    }
}
