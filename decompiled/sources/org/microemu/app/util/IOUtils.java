package org.microemu.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/* loaded from: avacs.jar:org/microemu/app/util/IOUtils.class */
public class IOUtils {
    public static String getCanonicalFileURL(File file) {
        String path = file.getAbsoluteFile().getPath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("//")) {
            if (path.startsWith("/")) {
                path = "//" + path;
            } else {
                path = "///" + path;
            }
        }
        return "file:" + path;
    }

    public static String getCanonicalFileClassLoaderURL(File file) {
        String url = getCanonicalFileURL(file);
        if (file.isDirectory() && !url.endsWith("/")) {
            url = String.valueOf(url) + "/";
        }
        return url;
    }

    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(src);
            copyToFile(fis, dst);
            closeQuietly(fis);
        } catch (Throwable th) {
            closeQuietly(fis);
            throw th;
        }
    }

    public static void copyToFile(InputStream is, File dst) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            while (true) {
                int i = is.read(buf);
                if (i != -1) {
                    fos.write(buf, 0, i);
                } else {
                    closeQuietly(fos);
                    return;
                }
            }
        } catch (Throwable th) {
            closeQuietly(fos);
            throw th;
        }
    }

    public static void closeQuietly(InputStream input) throws IOException {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(OutputStream output) throws IOException {
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Writer output) throws IOException {
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
            }
        }
    }
}
