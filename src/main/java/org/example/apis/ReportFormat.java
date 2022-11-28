package org.example.apis;

@FunctionalInterface
public interface ReportFormat {
    String format(String timestamp,Rule context,String data,Object ... args);
}
