package org.wysaid.texUtils;

import android.opengl.GLES20;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class TextureRendererDrawOrigin extends TextureRenderer {
    private static final String fshDrawOrigin = "precision mediump float;\nvarying vec2 texCoord;\nuniform %s inputImageTexture;\nvoid main()\n{\n   gl_FragColor = texture2D(inputImageTexture, texCoord);\n}";

    protected TextureRendererDrawOrigin() {
        defaultInitialize();
    }

    protected TextureRendererDrawOrigin(boolean noDefaultInitialize) {
        if (!noDefaultInitialize) {
            defaultInitialize();
        }
    }

    public static TextureRendererDrawOrigin create(boolean isExternalOES) {
        TextureRendererDrawOrigin renderer = new TextureRendererDrawOrigin();
        if (renderer.init(isExternalOES)) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public boolean init(boolean isExternalOES) {
        return setProgramDefault(getVertexShaderString(), getFragmentShaderString(), isExternalOES);
    }

    public void renderTexture(int texID, Viewport viewport) {
        if (viewport != null) {
            GLES20.glViewport(viewport.f34x, viewport.f35y, viewport.width, viewport.height);
        }
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.TEXTURE_2D_BINDABLE, texID);
        GLES20.glBindBuffer(34962, this.mVertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, 0);
        this.mProgram.bind();
        GLES20.glDrawArrays(6, 0, 4);
    }

    public void setTextureSize(int w, int h) {
        this.mTextureWidth = w;
        this.mTextureHeight = h;
    }

    public String getVertexShaderString() {
        return "attribute vec2 vPosition;\nvarying vec2 texCoord;\nuniform mat4 transform;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   vec2 coord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n   texCoord = (transform * vec4(coord, 0.0, 1.0)).xy;\n}";
    }

    public String getFragmentShaderString() {
        return fshDrawOrigin;
    }
}
