package com.yipin.commons.utils.file;
import com.yipin.commons.utils.ClassUtil;
import com.yipin.commons.utils.exception.BaseException;

import java.util.*;

public class BaseUtils {


    private static BaseUtils INSTANCE;
    private Map<String, String> cached = new HashMap<>();
    private Set<AbstractFileHandler> handlers = new HashSet<>();

    private static final String KEY_INDEX = ".properties";


    public static String getResult(BaseException e){
        return getResult(e.getBusinessKey(), e.getArgs());
    }


    public static synchronized String getResult(String key, Object...args){
        if (INSTANCE.cached.get(key)==null){
            String result=null;
            Iterator<AbstractFileHandler> iterator = INSTANCE.handlers.iterator();
            do {
                if (!iterator.hasNext()){
                    throw new RuntimeException("未能从本地加载资源["+key+"]属性！");
                }
                result = iterator.next().getAsString(key, args);
            }while (result==null);

            INSTANCE.cached.put(key, result);
        }
        return INSTANCE.cached.get(key);
    }


    static {
        INSTANCE = new BaseUtils();
        Set<String> resourcesByType = ClassUtil.findResourcesByType(KEY_INDEX);
        if (!resourcesByType.isEmpty()){
            for (String resource:resourcesByType){
                FileHandler4ResourceBundle fileHandler4ResourceBundle = new FileHandler4ResourceBundle(resource.replace(KEY_INDEX, ""));
                INSTANCE.handlers.add(fileHandler4ResourceBundle);
            }
        }
    }




}
