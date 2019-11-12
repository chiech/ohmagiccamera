package org.wysaid.myUtils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {
    public static final String LOG_TAG = "libCGE_java";
    public static final File externalStorageDirectory = Environment.getExternalStorageDirectory();
    private static String mDefaultFolder = "beauty_camera";
    public static String packageFilesDirectory = null;
    public static String storagePath = null;

    public static void setDefaultFolder(String defaultFolder) {
        mDefaultFolder = defaultFolder;
    }

    public static String getPath() {
        return getPath(null);
    }

    public static String getPath(Context context) {
        if (storagePath == null) {
            storagePath = externalStorageDirectory.getAbsolutePath() + "/" + mDefaultFolder;
            File file = new File(storagePath);
            if (!(file.exists() || file.mkdirs())) {
                storagePath = getPathInPackage(context, true);
            }
        }
        return storagePath;
    }

    public static String getPathInPackage(Context context, boolean grantPermissions) {
        if (context == null || packageFilesDirectory != null) {
            return packageFilesDirectory;
        }
        String path = context.getFilesDir() + "/" + mDefaultFolder;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("libCGE_java", "在pakage目录创建CGE临时目录失败!");
                return null;
            } else if (grantPermissions) {
                if (file.setExecutable(true, false)) {
                    Log.i("libCGE_java", "Package folder is executable");
                }
                if (file.setReadable(true, false)) {
                    Log.i("libCGE_java", "Package folder is readable");
                }
                if (file.setWritable(true, false)) {
                    Log.i("libCGE_java", "Package folder is writable");
                }
            }
        }
        packageFilesDirectory = path;
        return packageFilesDirectory;
    }

    public static void saveTextContent(String text, String filename) {
        Log.i("libCGE_java", "Saving text : " + filename);
        try {
            FileOutputStream fileout = new FileOutputStream(filename);
            fileout.write(text.getBytes());
            fileout.flush();
            fileout.close();
        } catch (Exception e) {
            Log.e("libCGE_java", "Error: " + e.getMessage());
        }
    }

    public static String getTextContent(String filename) {
        Log.i("libCGE_java", "Reading text : " + filename);
        if (filename == null) {
            return null;
        }
        String content = "";
        byte[] buffer = new byte[256];
        try {
            FileInputStream filein = new FileInputStream(filename);
            while (true) {
                int len = filein.read(buffer);
                if (len <= 0) {
                    return content;
                }
                content = content + new String(buffer, 0, len);
            }
        } catch (Exception e) {
            Log.e("libCGE_java", "Error: " + e.getMessage());
            return null;
        }
    }
}
