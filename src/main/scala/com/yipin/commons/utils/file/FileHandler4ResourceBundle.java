package com.yipin.commons.utils.file;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class FileHandler4ResourceBundle implements AbstractFileHandler {
    private ResourceBundle resourceBundle;

    public FileHandler4ResourceBundle(String name){
        resourceBundle = ResourceBundle.getBundle(name);
    }


    @Override
    public String getAsString(String key, Object... args) {
        String string = resourceBundle.getString(key);  //iso-8859-1 格式，需要转换
        try {
            string = new String(string.getBytes("ISO-8859-1"));
            return String.format(string, args);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsStringOrNull(String key, Object... args) {
        String string = resourceBundle.getString(key);  //iso-8859-1 格式，需要转换
        try {
            string = new String(string.getBytes("ISO-8859-1"));
            return String.format(string, args);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAsInt(String key, Object... args) {
        String string = resourceBundle.getString(key);
        return Integer.valueOf(string);
    }

    @Override
    public Object getAsObject(String key, Object... args) {
        return null;
    }
}
