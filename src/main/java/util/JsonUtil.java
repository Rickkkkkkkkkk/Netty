package util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

/**
 * @author zzb_r
 */
public class JsonUtil {

    static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.disableHtmlEscaping();
    }


    public static String pojoToJson(Object obj) {
        return gsonBuilder.create().toJson(obj);
    }

    public static <T> T jsonToPojo(String json, Class<T> clz) {
        return JSONObject.parseObject(json, clz);
    }
}
