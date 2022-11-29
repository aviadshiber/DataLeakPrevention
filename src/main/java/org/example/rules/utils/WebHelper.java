package org.example.rules.utils;

import lombok.Cleanup;
import lombok.val;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;

public class WebHelper {
    public static void downloadFile(String urlStr, File file) throws IOException {

        URL url = new URL(urlStr);
        @Cleanup val in = Channels.newChannel(url.openStream());
        @Cleanup val fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(in,0,Long.MAX_VALUE);
    }
}
