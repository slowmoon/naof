package com.yipin.prod;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class ResourceTest {


    public static void main(String[] args) {

        InputStream resourceAsStream = ResourceTest.class.getClassLoader().getResourceAsStream("META-INF/bc_codeMap.properties");

        ResourceBundle bundle = ResourceBundle.getBundle("META-INF/bc_codeMap");
        String string = bundle.getString("com.yipin.application.007");
        System.out.println(new String(string.getBytes(StandardCharsets.ISO_8859_1)));



      /*  Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String property = properties.getProperty("com.yipin.application.007");
        try {
            System.out.println(new String(property.getBytes("ISO-8859-1")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/


    }


}
