package org.wysaid.texUtils;

import android.opengl.GLES20;
import android.util.Log;
import org.wysaid.common.FrameBufferObject;
import org.wysaid.common.ProgramObject;
import org.wysaid.texUtils.TextureRenderer.Viewport;

public class TextureRendererLerpBlur extends TextureRendererDrawOrigin {
    private static final String SAMPLER_STEPS = "samplerSteps";
    private static final String fshBlur = "precision highp float;\nvarying vec2 texCoord;\nuniform sampler2D inputImageTexture;\nuniform vec2 samplerSteps;\nconst int samplerRadius = 5;\nconst float samplerRadiusFloat = 5.0;\nfloat random(vec2 seed)\n{\n  return fract(sin(dot(seed ,vec2(12.9898,78.233))) * 43758.5453);\n}\nvoid main()\n{\n  vec3 resultColor = vec3(0.0);\n  float blurPixels = 0.0;\n  float offset = random(texCoord) - 0.5;\n  \n  for(int i = -samplerRadius; i <= samplerRadius; ++i)\n  {\n    float percent = (float(i) + offset) / samplerRadiusFloat;\n    float weight = 1.0 - abs(percent);\n    vec2 coord = texCoord + samplerSteps * percent;\n    resultColor += texture2D(inputImageTexture, coord).rgb * weight;\n    blurPixels += weight;\n  }\n  gl_FragColor = vec4(resultColor / blurPixels, 1.0);\n}";
    private static final String fshBlurUpScale = "precision mediump float;\nvarying vec2 texCoords[5];\nuniform sampler2D inputImageTexture;\n\nvoid main()\n{\n  vec3 color = texture2D(inputImageTexture, texCoords[0]).rgb * 0.1;\n  color += texture2D(inputImageTexture, texCoords[1]).rgb * 0.2;\n  color += texture2D(inputImageTexture, texCoords[2]).rgb * 0.4;\n  color += texture2D(inputImageTexture, texCoords[3]).rgb * 0.2;\n  color += texture2D(inputImageTexture, texCoords[4]).rgb * 0.1;\n\n  gl_FragColor = vec4(color, 1.0);\n}";
    private static final String fshUpScale = "precision mediump float;\nvarying vec2 texCoord;\nuniform sampler2D inputImageTexture;\nvoid main()\n{\n   gl_FragColor = texture2D(inputImageTexture, texCoord);\n}";
    private static final String vshBlurCache = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   texCoord = vPosition / 2.0 + 0.5;\n}";
    private static final String vshBlurUpScale = "attribute vec2 vPosition;\nvarying vec2 texCoords[5];\nuniform vec2 samplerSteps;\n\nvoid main()\n{\n  gl_Position = vec4(vPosition, 0.0, 1.0);\n  vec2 texCoord = vPosition / 2.0 + 0.5;\n  texCoords[0] = texCoord - 2.0 * samplerSteps;\n  texCoords[1] = texCoord - 1.0 * samplerSteps;\n  texCoords[2] = texCoord;\n  texCoords[3] = texCoord + 1.0 * samplerSteps;\n  texCoords[4] = texCoord + 2.0 * samplerSteps;\n}";
    private static final String vshUpScale = "attribute vec2 vPosition;\nvarying vec2 texCoord;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   texCoord = vPosition / 2.0 + 0.5;\n}";
    private final float mBase = 2.0f;
    private FrameBufferObject mFramebuffer;
    private int mIntensity = 0;
    private final int mLevel = 16;
    private float mSampleScaling = 1.0f;
    private int mSamplerStepLoc = 0;
    private ProgramObject mScaleProgram;
    private Viewport mTexViewport;
    private int[] mTextureDownScale;

    public static TextureRendererLerpBlur create(boolean isExternalOES) {
        TextureRendererLerpBlur renderer = new TextureRendererLerpBlur();
        if (renderer.init(isExternalOES)) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public void setIntensity(int intensity) {
        if (intensity != this.mIntensity) {
            this.mIntensity = intensity;
            if (this.mIntensity > 16) {
                this.mIntensity = 16;
            }
        }
    }

    public boolean init(boolean isExternalOES) {
        return super.init(isExternalOES) && initLocal();
    }

    public void renderTexture(int texID, Viewport viewport) {
        if (this.mIntensity == 0) {
            GLES20.glBindFramebuffer(36160, 0);
            super.renderTexture(texID, viewport);
            return;
        }
        int i;
        GLES20.glActiveTexture(33984);
        this.mFramebuffer.bindTexture(this.mTextureDownScale[0]);
        this.mTexViewport.width = calcMips(512, 1);
        this.mTexViewport.height = calcMips(512, 1);
        super.renderTexture(texID, this.mTexViewport);
        this.mScaleProgram.bind();
        for (i = 1; i < this.mIntensity; i++) {
            this.mFramebuffer.bindTexture(this.mTextureDownScale[i]);
            GLES20.glBindTexture(3553, this.mTextureDownScale[i - 1]);
            GLES20.glViewport(0, 0, calcMips(512, i + 1), calcMips(512, i + 1));
            GLES20.glDrawArrays(6, 0, 4);
        }
        for (i = this.mIntensity - 1; i > 0; i--) {
            this.mFramebuffer.bindTexture(this.mTextureDownScale[i - 1]);
            GLES20.glBindTexture(3553, this.mTextureDownScale[i]);
            GLES20.glViewport(0, 0, calcMips(512, i), calcMips(512, i));
            GLES20.glDrawArrays(6, 0, 4);
        }
        GLES20.glViewport(viewport.f34x, viewport.f35y, viewport.width, viewport.height);
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glBindTexture(3553, this.mTextureDownScale[0]);
        GLES20.glDrawArrays(6, 0, 4);
    }

    public void release() {
        this.mScaleProgram.release();
        this.mFramebuffer.release();
        GLES20.glDeleteTextures(this.mTextureDownScale.length, this.mTextureDownScale, 0);
        this.mScaleProgram = null;
        this.mFramebuffer = null;
    }

    private boolean initLocal() {
        genMipmaps(16, 512, 512);
        this.mFramebuffer = new FrameBufferObject();
        this.mScaleProgram = new ProgramObject();
        this.mScaleProgram.bindAttribLocation("vPosition", 0);
        if (this.mScaleProgram.init("attribute vec2 vPosition;\nvarying vec2 texCoord;\nvoid main()\n{\n   gl_Position = vec4(vPosition, 0.0, 1.0);\n   texCoord = vPosition / 2.0 + 0.5;\n}", fshUpScale)) {
            return true;
        }
        Log.e("libCGE_java", "Lerp blur initLocal failed...");
        return false;
    }

    private void updateTexture() {
    }

    public void setTextureSize(int w, int h) {
        super.setTextureSize(w, h);
    }

    private void genMipmaps(int level, int width, int height) {
        this.mTextureDownScale = new int[level];
        GLES20.glGenTextures(level, this.mTextureDownScale, 0);
        for (int i = 0; i < level; i++) {
            GLES20.glBindTexture(3553, this.mTextureDownScale[i]);
            GLES20.glTexImage2D(3553, 0, 6408, calcMips(width, i + 1), calcMips(height, i + 1), 0, 6408, 5121, null);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, 10240, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
        }
        this.mTexViewport = new Viewport(0, 0, 512, 512);
    }

    private int calcMips(int len, int level) {
        return len / (level + 1);
    }
}
