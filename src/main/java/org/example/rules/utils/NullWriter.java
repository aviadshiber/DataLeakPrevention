package org.example.rules.utils;

import java.io.IOException;
import java.io.Writer;

public class NullWriter extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //writes nothing
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
