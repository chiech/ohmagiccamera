package org.wysaid.texUtils;

public class TextureRendererEmboss extends TextureRendererDrawOrigin {
    protected static final String SAMPLER_STEPS = "samplerSteps";
    private static final String fshEmboss = "precision mediump float;\nuniform %s inputImageTexture;\nvarying vec2 texCoord;\nuniform vec2 samplerSteps;\nconst float stride = 2.0;\nconst vec2 norm = vec2(0.72, 0.72);\nvoid main() {\n  vec4 src = texture2D(inputImageTexture, texCoord);\n  vec3 tmp = texture2D(inputImageTexture, texCoord + samplerSteps * stride * norm).rgb - src.rgb + 0.5;\n  float f = (tmp.r + tmp.g + tmp.b) / 3.0;\n  gl_FragColor = vec4(f, f, f, src.a);\n}";

    public static TextureRendererEmboss create(boolean isExternalOES) {
        TextureRendererEmboss renderer = new TextureRendererEmboss();
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
        this.mProgram.sendUniformf(SAMPLER_STEPS, 0.0015625f, 0.0015625f);
        return true;
    }

    public void setTextureSize(int w, int h) {
        super.setTextureSize(w, h);
        this.mProgram.bind();
        this.mProgram.sendUniformf(SAMPLER_STEPS, 1.0f / ((float) w), 1.0f / ((float) h));
    }

    public String getFragmentShaderString() {
        return fshEmboss;
    }
}
