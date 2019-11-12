package org.wysaid.common;

import android.opengl.GLES20;
import android.util.Log;
import java.nio.FloatBuffer;

public class TextureDrawer {
    public static final int DRAW_FUNCTION = 6;
    private static final String fsh = "precision mediump float;\nvarying vec2 texCoord;\nuniform sampler2D inputImageTexture;\nvoid main()\n{\n   gl_FragColor = texture2D(inputImageTexture, texCoord);\n}";
    public static final float[] vertices = new float[]{-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    private static final String vsh = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nuniform mat2 rotation;\nuniform vec2 flipScale;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   texCoord = flipScale * (vPosition / 2.0 * rotation) + 0.5;\n}";
    private int mFlipScaleLoc;
    private ProgramObject mProgram;
    private int mRotLoc;
    private int mVertBuffer;

    private TextureDrawer() {
    }

    protected boolean init() {
        this.mProgram = new ProgramObject();
        if (this.mProgram.init(vsh, fsh)) {
            this.mProgram.bind();
            this.mRotLoc = this.mProgram.getUniformLoc("rotation");
            this.mFlipScaleLoc = this.mProgram.getUniformLoc("flipScale");
            int[] vertBuffer = new int[1];
            GLES20.glGenBuffers(1, vertBuffer, 0);
            this.mVertBuffer = vertBuffer[0];
            GLES20.glBindBuffer(34962, this.mVertBuffer);
            FloatBuffer buffer = FloatBuffer.allocate(vertices.length);
            buffer.put(vertices).position(0);
            GLES20.glBufferData(34962, 32, buffer, 35044);
            setRotation(0.0f);
            setFlipScale(1.0f, 1.0f);
            return true;
        }
        this.mProgram.release();
        this.mProgram = null;
        return false;
    }

    public static TextureDrawer create() {
        TextureDrawer drawer = new TextureDrawer();
        if (drawer.init()) {
            return drawer;
        }
        Log.e("libCGE_java", "TextureDrawer create failed!");
        drawer.release();
        return null;
    }

    public void release() {
        this.mProgram.release();
        GLES20.glDeleteBuffers(1, new int[]{this.mVertBuffer}, 0);
        this.mProgram = null;
        this.mVertBuffer = 0;
    }

    public void drawTexture(int texID) {
        drawTexture(texID, 3553);
    }

    public void drawTexture(int texID, int type) {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(type, texID);
        GLES20.glBindBuffer(34962, this.mVertBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, 0);
        this.mProgram.bind();
        GLES20.glDrawArrays(6, 0, 4);
    }

    public void bindVertexBuffer() {
        GLES20.glBindBuffer(34962, this.mVertBuffer);
    }

    public void setRotation(float rad) {
        _rotate(this.mRotLoc, rad);
    }

    public void setFlipScale(float x, float y) {
        this.mProgram.bind();
        GLES20.glUniform2f(this.mFlipScaleLoc, x, y);
    }

    private void _rotate(int location, float rad) {
        float cosRad = (float) Math.cos((double) rad);
        float sinRad = (float) Math.sin((double) rad);
        float[] rotation = new float[]{cosRad, sinRad, -sinRad, cosRad};
        this.mProgram.bind();
        GLES20.glUniformMatrix2fv(location, 1, false, rotation, 0);
    }
}
