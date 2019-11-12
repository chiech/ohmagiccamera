package org.wysaid.texUtils;

public class TextureRendererThreshold extends TextureRendererDrawOrigin {
    protected static final String THRESHOLD_VALUE = "thresholdValue";
    private static final String fshThreshold = "precision mediump float;\nvarying vec2 texCoord;\n uniform %s inputImageTexture;\n uniform float thresholdValue;\n void main()\n{\n    vec4 color = texture2D(inputImageTexture, texCoord);\n    \n    float weight = (color.r + color.g + color.b) / 3.0;\n    color.a = smoothstep(0.0, thresholdValue, weight);\n    \n    gl_FragColor = color;\n}";

    public static TextureRendererThreshold create(boolean isExternalOES) {
        TextureRendererThreshold renderer = new TextureRendererThreshold();
        if (renderer.init(isExternalOES)) {
            return renderer;
        }
        renderer.release();
        return null;
    }

    public void setThresholdValue(float thresholdValue) {
        this.mProgram.bind();
        this.mProgram.sendUniformf(THRESHOLD_VALUE, thresholdValue);
    }

    public String getFragmentShaderString() {
        return fshThreshold;
    }
}
