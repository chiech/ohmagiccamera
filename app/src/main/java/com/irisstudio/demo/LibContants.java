package com.irisstudio.demo;

import android.os.Environment;
import java.io.File;

public class LibContants {
    public static String getPackageId() {
        return "Generic";
    }

    public static File getSaveFileLocation() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Snappy Photo/.data");
    }
}
