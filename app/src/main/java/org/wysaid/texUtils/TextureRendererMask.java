package org.wysaid.texUtils;

import android.opengl.GLES20;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class TextureRendererMask extends TextureRendererDrawOrigin {
    static final /* synthetic */ boolean $assertionsDisabled = (!TextureRendererMask.class.desiredAssertionStatus());
    protected static final String MASK_FLIPSCALE_NAME = "maskFlipScale";
    protected static final String MASK_ROTATION_NAME = "maskRotation";
    protected static final String MASK_TEXTURE_NAME = "maskTexture";
    private static final String fshMask = "precision mediump float;\nvarying vec2 texCoord;\nvarying vec2 maskCoord;\nuniform %s inputImageTexture;\nuniform sampler2D maskTexture;\nvoid main()\n{\n   gl_FragColor = texture2D(inputImageTexture, texCoord);\n   vec4 maskColor = texture2D(maskTexture, maskCoord);\n   gl_FragColor *= maskColor;\n}";
    private static final String vshMask = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nvarying vec2 maskCoord;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nuniform mat2 maskRotation;\nuniform vec2 maskFlipScale;\nuniform mat4 transform;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   vec2 coord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n   texCoord = (transform * vec4(coord, 0.0, 1.0)).xy;\n   maskCoord = maskFlipScale * (vPosition / 2.0 * maskRotation) + 0.5;\n}";
    protected int mMaskFlipscaleLoc;
    protected int mMaskRotLoc;
    protected int mMaskTexture;

    public static TextureRendererMask create(boolean isExternalOES) {
        TextureRendererMask renderer = new TextureRendererMask();
        if (renderer.init(isExternalOES)) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public boolean init(boolean isExternalOES) {
        if (!setProgramDefault(getVertexShaderString(), getFragmentShaderString(), isExternalOES)) {
            return false;
        }
        this.mProgram.bind();
        this.mMaskRotLoc = this.mProgram.getUniformLoc(MASK_ROTATION_NAME);
        this.mMaskFlipscaleLoc = this.mProgram.getUniformLoc(MASK_FLIPSCALE_NAME);
        this.mProgram.sendUniformi(MASK_TEXTURE_NAME, 1);
        setMaskRotation(0.0f);
        setMaskFlipscale(1.0f, 1.0f);
        return true;
    }

    public void setMaskRotation(float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        float[] rot = new float[]{cosRad, sinRad, -sinRad, cosRad};
        if ($assertionsDisabled || this.mProgram != null) {
            this.mProgram.bind();
            GLES20.glUniformMatrix2fv(this.mMaskRotLoc, 1, false, rot, 0);
            return;
        }
        throw new AssertionError("setRotation must not be called before init!");
    }

    public void setMaskFlipscale(float x, float y) {
        this.mProgram.bind();
        GLES20.glUniform2f(this.mMaskFlipscaleLoc, x, y);
    }

    public void setMaskTexture(int texID) {
        if (texID != this.mMaskTexture) {
            GLES20.glDeleteTextures(1, new int[]{this.mMaskTexture}, 0);
            this.mMaskTexture = texID;
        }
    }

    public void renderTexture(int texID, Viewport viewport) {
        if (viewport != null) {
            GLES20.glViewport(viewport.f34x, viewport.f35y, viewport.width, viewport.height);
        }
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.TEXTURE_2D_BINDABLE, texID);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, this.mMaskTexture);
        GLES20.glBindBuffer(34962, this.mVertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, 0);
        this.mProgram.bind();
        GLES20.glDrawArrays(6, 0, 4);
    }

    public String getVertexShaderString() {
        return vshMask;
    }

    public String getFragmentShaderString() {
        return fshMask;
    }

    public void release() {
        super.release();
        GLES20.glDeleteTextures(1, new int[]{this.mMaskTexture}, 0);
    }
}
