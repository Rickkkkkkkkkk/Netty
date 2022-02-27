package util;

/**
 * @author zzb_r
 */
public class JsonMsg {

    private int id;

    private String content;

    public String convertToJson() {
        return JsonUtil.pojoToJson(this);
    }

    public static JsonMsg parseFromJson(String json) {
        return JsonUtil.jsonToPojo(json, JsonMsg.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
