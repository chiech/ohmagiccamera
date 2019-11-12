package org.wysaid.common;

import android.opengl.GLES20;
import android.util.Log;

public class ProgramObject {
    public static final String LOG_TAG = "libCGE_java";
    private ShaderObject mFragmentShader;
    private int mProgramID;
    private ShaderObject mVertexShader;

    public static class ShaderObject {
        static final /* synthetic */ boolean $assertionsDisabled = (!ProgramObject.class.desiredAssertionStatus());
        private int mShaderID;
        private int mShaderType;

        public int shaderID() {
            return this.mShaderID;
        }

        public ShaderObject() {
            this.mShaderType = 0;
            this.mShaderID = 0;
        }

        public ShaderObject(String shaderCode, int shaderType) {
            init(shaderCode, shaderType);
        }

        public boolean init(String shaderCode, int shaderType) {
            this.mShaderType = shaderType;
            this.mShaderID = loadShader(shaderType, shaderCode);
            if (!$assertionsDisabled && this.mShaderID == 0) {
                throw new AssertionError("Shader Create Failed!");
            } else if (this.mShaderID != 0) {
                return true;
            } else {
                Log.e("libCGE_java", "glCreateShader Failed!...");
                return false;
            }
        }

        public final void release() {
            if (this.mShaderID != 0) {
                GLES20.glDeleteShader(this.mShaderID);
                this.mShaderID = 0;
            }
        }

        public static int loadShader(int type, String code) {
            int shaderID = GLES20.glCreateShader(type);
            if (shaderID == 0) {
                return shaderID;
            }
            GLES20.glShaderSource(shaderID, code);
            GLES20.glCompileShader(shaderID);
            int[] compiled = new int[]{0};
            GLES20.glGetShaderiv(shaderID, 35713, compiled, 0);
            if (compiled[0] == 1) {
                return shaderID;
            }
            Log.e("libCGE_java", GLES20.glGetShaderInfoLog(shaderID));
            GLES20.glDeleteShader(shaderID);
            return 0;
        }
    }

    public ProgramObject() {
        this.mProgramID = GLES20.glCreateProgram();
    }

    public ProgramObject(String vsh, String fsh) {
        init(vsh, fsh);
    }

    public int programID() {
        return this.mProgramID;
    }

    public final void release() {
        if (this.mProgramID != 0) {
            GLES20.glDeleteProgram(this.mProgramID);
            this.mProgramID = 0;
        }
    }

    public boolean init(String vsh, String fsh) {
        return init(vsh, fsh, this.mProgramID);
    }

    public boolean init(String vsh, String fsh, int programID) {
        if (programID == 0) {
            programID = GLES20.glCreateProgram();
            if (programID == 0) {
                Log.e("libCGE_java", "Invalid Program ID! Check if the context is bound!");
                return false;
            }
        }
        if (this.mVertexShader != null) {
            this.mVertexShader.release();
        }
        if (this.mFragmentShader != null) {
            this.mFragmentShader.release();
        }
        this.mVertexShader = new ShaderObject(vsh, 35633);
        this.mFragmentShader = new ShaderObject(fsh, 35632);
        GLES20.glAttachShader(programID, this.mVertexShader.shaderID());
        GLES20.glAttachShader(programID, this.mFragmentShader.shaderID());
        Common.checkGLError("AttachShaders...");
        GLES20.glLinkProgram(programID);
        int[] programStatus = new int[]{0};
        GLES20.glGetProgramiv(programID, 35714, programStatus, 0);
        this.mVertexShader.release();
        this.mFragmentShader.release();
        this.mVertexShader = null;
        this.mFragmentShader = null;
        if (programStatus[0] != 1) {
            Log.e("libCGE_java", GLES20.glGetProgramInfoLog(programID));
            return false;
        }
        if (!(this.mProgramID == programID || this.mProgramID == 0)) {
            GLES20.glDeleteProgram(this.mProgramID);
        }
        this.mProgramID = programID;
        return true;
    }

    public void bind() {
        GLES20.glUseProgram(this.mProgramID);
    }

    public int getUniformLoc(String name) {
        int uniform = GLES20.glGetUniformLocation(this.mProgramID, name);
        if (uniform < 0) {
            Log.e("libCGE_java", String.format("uniform name %s does not exist", new Object[]{name}));
        }
        return uniform;
    }

    public void sendUniformf(String name, float x) {
        GLES20.glUniform1f(getUniformLoc(name), x);
    }

    public void sendUniformf(String name, float x, float y) {
        GLES20.glUniform2f(getUniformLoc(name), x, y);
    }

    public void sendUniformf(String name, float x, float y, float z) {
        GLES20.glUniform3f(getUniformLoc(name), x, y, z);
    }

    public void sendUniformf(String name, float x, float y, float z, float w) {
        GLES20.glUniform4f(getUniformLoc(name), x, y, z, w);
    }

    public void sendUniformi(String name, int x) {
        GLES20.glUniform1i(getUniformLoc(name), x);
    }

    public void sendUniformi(String name, int x, int y) {
        GLES20.glUniform2i(getUniformLoc(name), x, y);
    }

    public void sendUniformi(String name, int x, int y, int z) {
        GLES20.glUniform3i(getUniformLoc(name), x, y, z);
    }

    public void sendUniformi(String name, int x, int y, int z, int w) {
        GLES20.glUniform4i(getUniformLoc(name), x, y, z, w);
    }

    public void sendUniformMat2(String name, int count, boolean transpose, float[] matrix) {
        GLES20.glUniformMatrix2fv(getUniformLoc(name), count, transpose, matrix, 0);
    }

    public void sendUniformMat3(String name, int count, boolean transpose, float[] matrix) {
        GLES20.glUniformMatrix3fv(getUniformLoc(name), count, transpose, matrix, 0);
    }

    public void sendUniformMat4(String name, int count, boolean transpose, float[] matrix) {
        GLES20.glUniformMatrix4fv(getUniformLoc(name), count, transpose, matrix, 0);
    }

    public int attributeLocation(String name) {
        return GLES20.glGetAttribLocation(this.mProgramID, name);
    }

    public void bindAttribLocation(String name, int index) {
        GLES20.glBindAttribLocation(this.mProgramID, index, name);
    }
}
