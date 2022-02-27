import util.JsonMsg;

public class JsonMsgDemo {

    public static JsonMsg buildJsonMsg() {
        JsonMsg jsonMsg = new JsonMsg();
        jsonMsg.setId(1000);
        jsonMsg.setContent("疯狂创客圈：高性能学习社群！");
        return jsonMsg;
    }

    public static void main(String[] args) {
        JsonMsg jsonMsg = buildJsonMsg();
        String s = jsonMsg.convertToJson();
        System.out.println("json" + s);

        JsonMsg jsonMsg1 = JsonMsg.parseFromJson(s);
        System.out.println("id: " + jsonMsg1.getId());
        System.out.println("content: " + jsonMsg1.getContent());
    }

}
