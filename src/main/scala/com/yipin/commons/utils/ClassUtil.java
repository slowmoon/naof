package com.yipin.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;

public class ClassUtil {

    private static final Map<String,Set<String>> cached = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);



    public static Set<String> findResourcesByType(String suffix) {
        if (cached.get(suffix) != null) return cached.get(suffix);
        //如果缓存里面没有内容，则，开始查询内容。需要同步
        synchronized (cached) {
            if (cached.get(suffix) != null) return cached.get(suffix);
            HashSet<String> internal = new HashSet<>();

            try {
                Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("");

                while (resources.hasMoreElements()){
                    URL url = resources.nextElement();
                    String base = url.getPath();
                    if (url.getProtocol().equals("jar")){
                        findResourceByJar(url, suffix, internal,base);
                    }else if (url.getProtocol().equals("file")){
                        findResourceByFile(url, suffix, internal, base);
                    }
                }
                cached.put(suffix, internal);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("exception io error",e);
            }
            return cached.get(suffix);
        }
    }

    private static void findResourceByFile(URL url,String suffix,Set<String> sets,String base ){
        String file = url.getFile();
        File newFile = new File(file);
        if (newFile.isFile() && newFile.getName().endsWith(suffix)){
            sets.add(newFile.getPath());
        }else if (newFile.isDirectory()){
            File[] files = newFile.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith(suffix));
            for (File f:files){
                try {
                    if (f.isDirectory()) {
                        findResourceByFile(f.toURI().toURL(), suffix, sets,base);
                    }else {
                        sets.add(f.getPath().substring(base.length()));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void findResourceByJar(URL url, String suffix, Set<String> sets,String base){
        try {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            Enumeration<JarEntry> entries = jarURLConnection.getJarFile().entries();
            while (entries.hasMoreElements()){
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory()&&entry.getName().endsWith(suffix)){
                    sets.add(entry.getName().substring(base.length()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

        Set<String> resourcesByType = ClassUtil.findResourcesByType(".properties");
        for (String s:resourcesByType){
            System.out.println(s);
        }

    }

}
