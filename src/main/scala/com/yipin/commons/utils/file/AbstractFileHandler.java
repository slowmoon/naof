package com.yipin.commons.utils.file;

public interface AbstractFileHandler {


    String getAsString(String key, Object...args);

    String getAsStringOrNull(String key, Object...args);

    int getAsInt(String key, Object...args);

    Object getAsObject(String key, Object...args);
}


