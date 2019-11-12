package org.wysaid.texUtils;

import android.opengl.GLES20;
import android.util.Log;
import android.view.View;

import java.nio.FloatBuffer;
import org.wysaid.common.ProgramObject;

public abstract class TextureRenderer {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DRAW_FUNCTION = 6;
    protected static final String FLIPSCALE_NAME = "flipScale";
    public static final String LOG_TAG = "libCGE_java";
    protected static final String POSITION_NAME = "vPosition";
    protected static final String REQUIRE_STRING_EXTERNAL_OES = "#extension GL_OES_EGL_image_external : require\n";
    protected static final String ROTATION_NAME = "rotation";
    protected static final String SAMPLER2D_VAR = "sampler2D";
    protected static final String SAMPLER2D_VAR_EXTERNAL_OES = "samplerExternalOES";
    protected static final String TRANSFORM_NAME = "transform";
    public static final float[] vertices = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    protected static final String vshDrawDefault = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nuniform mat4 transform;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   vec2 coord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n   texCoord = (transform * vec4(coord, 0.0, 1.0)).xy;\n}";
    protected int TEXTURE_2D_BINDABLE;
    protected int mFlipScaleLoc;
    protected ProgramObject mProgram;
    protected int mRotationLoc;
    protected int mTextureHeight;
    protected int mTextureWidth;
    protected int mTransformLoc;
    protected int mVertexBuffer;

    public static class Viewport {
        public int height;
        public int width;
        /* renamed from: x */
        public int f34x;
        /* renamed from: y */
        public int f35y;

        public Viewport()
        {}


        public Viewport(int _x, int _y, int _width, int _height) {
            this.f34x = _x;
            this.f35y = _y;
            this.width = _width;
            this.height = _height;
        }
    }

    public abstract String getFragmentShaderString();

    public abstract String getVertexShaderString();

    public abstract boolean init(boolean z);

    public abstract void renderTexture(int i, Viewport viewport);

    public abstract void setTextureSize(int i, int i2);

    static {
        boolean z;
        if (TextureRenderer.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    public void release() {
        if (this.mVertexBuffer != 0) {
            GLES20.glDeleteBuffers(1, new int[]{this.mVertexBuffer}, 0);
            this.mVertexBuffer = 0;
        }
        if (this.mProgram != null) {
            this.mProgram.release();
            this.mProgram = null;
        }
    }

    public void setRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        float[] rot = new float[]{cosRad, sinRad, -sinRad, cosRad};
        if ($assertionsDisabled || this.mProgram != null) {
            this.mProgram.bind();
            GLES20.glUniformMatrix2fv(this.mRotationLoc, 1, false, rot, 0);
            return;
        }
        throw new AssertionError("setRotation must not be called before init!");
    }

    public void setFlipscale(float x, float y) {
        this.mProgram.bind();
        GLES20.glUniform2f(this.mFlipScaleLoc, x, y);
    }

    public void setTransform(float[] matrix) {
        this.mProgram.bind();
        GLES20.glUniformMatrix4fv(this.mTransformLoc, 1, false, matrix, 0);
    }

    protected boolean setProgramDefault(String vsh, String fsh, boolean isExternalOES) {
        this.TEXTURE_2D_BINDABLE = isExternalOES ? 36197 : 3553;
        this.mProgram = new ProgramObject();
        this.mProgram.bindAttribLocation(POSITION_NAME, 0);
        StringBuilder append = new StringBuilder().append(isExternalOES ? REQUIRE_STRING_EXTERNAL_OES : "");
        Object[] objArr = new Object[1];
        objArr[0] = isExternalOES ? SAMPLER2D_VAR_EXTERNAL_OES : SAMPLER2D_VAR;
        if (!this.mProgram.init(vsh, append.append(String.format(fsh, objArr)).toString())) {
            return false;
        }
        this.mRotationLoc = this.mProgram.getUniformLoc(ROTATION_NAME);
        this.mFlipScaleLoc = this.mProgram.getUniformLoc(FLIPSCALE_NAME);
        this.mTransformLoc = this.mProgram.getUniformLoc(TRANSFORM_NAME);
        setRotation(0.0f);
        setFlipscale(1.0f, 1.0f);
        setTransform(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        return true;
    }

    protected void defaultInitialize() {
        int[] vertexBuffer = new int[1];
        GLES20.glGenBuffers(1, vertexBuffer, 0);
        this.mVertexBuffer = vertexBuffer[0];
        if (this.mVertexBuffer == 0) {
            Log.e("libCGE_java", "Invalid VertexBuffer!");
        }
        GLES20.glBindBuffer(34962, this.mVertexBuffer);
        FloatBuffer buffer = FloatBuffer.allocate(vertices.length);
        buffer.put(vertices).position(0);
        GLES20.glBufferData(34962, 32, buffer, 35044);
    }
}
