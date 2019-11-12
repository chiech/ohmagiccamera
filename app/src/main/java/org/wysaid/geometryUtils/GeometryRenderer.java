package org.wysaid.geometryUtils;

import android.opengl.GLES20;
import org.wysaid.common.ProgramObject;

public class GeometryRenderer {
    protected static final String CANVAS_SIZE = "canvasSize";
    protected static final String COLOR_NAME = "color";
    protected static final String POSITION_NAME = "vPosition";
    private static final String fshDrawOrigin = "precision mediump float;\nuniform vec4 color;\nvoid main()\n{\n   gl_FragColor = color;\n}";
    protected static final String vshDrawDefault = "attribute vec2 vPosition;\nuniform vec2 canvasSize;\nvoid main()\n{\n   gl_Position = vec4((vPosition / canvasSize) * 2.0 - 1.0, 0.0, 1.0);\n}";
    protected float mCanvasHeight;
    protected float mCanvasWidth;
    protected ProgramObject mProgram;
    protected int mVertexBuffer;

    GeometryRenderer() {
    }

    protected boolean init() {
        this.mProgram = new ProgramObject();
        this.mProgram.bindAttribLocation(POSITION_NAME, 0);
        if (this.mProgram.init(vshDrawDefault, fshDrawOrigin)) {
            setColor(1.0f, 1.0f, 1.0f, 1.0f);
            setCanvasSize(1.0f, 1.0f);
            return true;
        }
        release();
        return false;
    }

    public void release() {
        if (this.mProgram != null) {
            this.mProgram.release();
            this.mProgram = null;
        }
        if (this.mVertexBuffer != 0) {
            GLES20.glDeleteBuffers(1, new int[]{this.mVertexBuffer}, 0);
            this.mVertexBuffer = 0;
        }
    }

    public static GeometryRenderer create() {
        GeometryRenderer renderer = new GeometryRenderer();
        if (renderer.init()) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public void setColor(float r, float g, float b, float a) {
        this.mProgram.bind();
        this.mProgram.sendUniformf(COLOR_NAME, r, g, b, a);
    }

    public int getVertexBuffer() {
        return this.mVertexBuffer;
    }

    public void setVertexBuffer(int buffer) {
        this.mVertexBuffer = buffer;
    }

    public void setCanvasSize(float w, float h) {
        this.mCanvasWidth = w;
        this.mCanvasHeight = h;
        this.mProgram.bind();
        this.mProgram.sendUniformf(CANVAS_SIZE, w, h);
    }

    public ProgramObject getProgram() {
        return this.mProgram;
    }

    public void bindBufferAttrib() {
        GLES20.glBindBuffer(34962, this.mVertexBuffer);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, 5126, false, 0, 0);
    }

    public void render(int mode, int first, int count) {
        bindBufferAttrib();
        this.mProgram.bind();
        GLES20.glDrawArrays(mode, first, count);
    }
}
