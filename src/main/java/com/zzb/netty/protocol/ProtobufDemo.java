package com.zzb.netty.protocol;

import com.alibaba.fastjson.JSON;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtobufDemo {

    public static void main(String[] args) throws IOException {
        // 方式一
//        MsgProtos.Msg msg = buildMsg();
//        byte[] bytes = msg.toByteArray();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byteArrayOutputStream.write(bytes);
//        byte[] bytes1 = byteArrayOutputStream.toByteArray();
//        MsgProtos.Msg msg1 = MsgProtos.Msg.parseFrom(bytes1);
//        System.out.println("id :" + msg1.getId());
//        System.out.println("content：" + msg1.getContent());


        // 方式二
        MsgProtos.Msg msg = buildMsg();

        // 序列化到二进制码流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        msg.writeDelimitedTo(outputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // 从二进制玛字节流反序列化成Protobuf对象
        MsgProtos.Msg msg1 = MsgProtos.Msg.parseDelimitedFrom(byteArrayInputStream);
        System.out.println("id: " + msg1.getId());
        System.out.println("content: " + msg1.getContent());
    }

    public static MsgProtos.Msg buildMsg() {
        MsgProtos.Msg.Builder msgBuilder = MsgProtos.Msg.newBuilder();
        msgBuilder.setId(1);
        msgBuilder.setContent("疯狂创业圈！");
        return msgBuilder.build();
    }
}
