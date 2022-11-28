package org.example.rules.utils;

import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

public class ClassLoaderHelper {

    /**
     * load the impl class of list, with a prefix, as protocol interface.
     * @param list of impl class names
     * @param packagePrefix prefix of package
     * @param protocol to protocol as interface
     * @return List of Protocol with impl loaded
     * @param <IN> impl input
     * @param <OUT> protocol output
     */
    public static <IN,OUT> List<OUT> loadClasses(List<IN> list, String packagePrefix, Class<OUT> protocol){
        return list.stream().map(clazz -> {
            val fqdn = String.format("%s.%s",packagePrefix,clazz);
            return loadClassAs(fqdn, protocol);
        }).collect(Collectors.toList());
    }
    private static <T> T loadClassAs(final String className, final Class<T> type){
        try{
            return type.cast(Class.forName(className).newInstance());
        } catch(InstantiationException
                | IllegalAccessException
                | ClassNotFoundException e){
            throw new IllegalStateException(e);
        }
    }
}
