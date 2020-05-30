package com.group4.utils;

import com.group4.TheNoteApplication;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

public class FileTool {
    public static URL getFxml(String name) {
        return TheNoteApplication.class.getResource("fxml/"+name);
    }

    public static String getCss(String name) {
        return TheNoteApplication.class.getResource("css/" + name).toExternalForm();
    }

    public static String getWorkPath() {
        return System.getProperty("user.dir");
    }

    public static String getDataPath() {
        return Path.of(getWorkPath(), "data").toString();
    }

    public static String getCatalogPath(String catalog) {
        return Path.of(getDataPath(), catalog).toString();
    }

    public static String getNotePath(String catalog, String note) {
        return Path.of(getCatalogPath(catalog), note).toString();
    }

    public static Boolean delete(String path) {
        File f = new File(path);
        if (!f.isFile()) {
            boolean err;
            String[] list = f.list();
            if (list != null) {
                for (String sub : list) {
                    err = delete(sub);
                    if (err) {
                        return true;
                    }
                }
            }
        }
        return f.delete();
    }
    public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
}
