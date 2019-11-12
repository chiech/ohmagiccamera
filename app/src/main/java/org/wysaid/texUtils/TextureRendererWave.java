package org.wysaid.texUtils;

import android.opengl.GLES20;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class TextureRendererWave extends TextureRendererDrawOrigin {
    private static final String fshWave = "precision mediump float;\nvarying vec2 texCoord;\nuniform %s inputImageTexture;\nuniform float motion;\nconst float angle = 20.0;void main()\n{\n   vec2 coord;\n   coord.x = texCoord.x + 0.01 * sin(motion + texCoord.x * angle);\n   coord.y = texCoord.y + 0.01 * sin(motion + texCoord.y * angle);\n   gl_FragColor = texture2D(inputImageTexture, coord);\n}";
    private boolean mAutoMotion = false;
    private float mMotion = 0.0f;
    private int mMotionLoc = 0;
    private float mMotionSpeed = 0.0f;

    public static TextureRendererWave create(boolean isExternalOES) {
        TextureRendererWave renderer = new TextureRendererWave();
        if (renderer.init(isExternalOES)) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public boolean init(boolean isExternalOES) {
        if (!setProgramDefault("attribute vec2 vPosition;\nvarying vec2 texCoord;\nuniform mat4 transform;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   vec2 coord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n   texCoord = (transform * vec4(coord, 0.0, 1.0)).xy;\n}", fshWave, isExternalOES)) {
            return false;
        }
        this.mProgram.bind();
        this.mMotionLoc = this.mProgram.getUniformLoc("motion");
        return true;
    }

    public void setWaveMotion(float motion) {
        this.mProgram.bind();
        GLES20.glUniform1f(this.mMotionLoc, motion);
    }

    public void setAutoMotion(float speed) {
        this.mMotionSpeed = speed;
        this.mAutoMotion = speed != 0.0f;
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
        if (this.mAutoMotion) {
            this.mMotion += this.mMotionSpeed;
            GLES20.glUniform1f(this.mMotionLoc, this.mMotion);
            if (((double) this.mMotion) > 62.83185307179586d) {
                this.mMotion = (float) (((double) this.mMotion) - 62.83185307179586d);
            }
        }
        GLES20.glDrawArrays(6, 0, 4);
    }
}
