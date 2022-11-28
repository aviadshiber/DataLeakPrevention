package org.example.rules.utils;

import lombok.Cleanup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class WebHelper {
    public static void downloadFile(String urlStr, File file) throws IOException {
        URL url = new URL(urlStr);
        @Cleanup BufferedInputStream bis = new BufferedInputStream(url.openStream());
        @Cleanup FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
    }
}
