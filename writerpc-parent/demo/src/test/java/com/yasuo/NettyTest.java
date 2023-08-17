package com.yasuo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @Description
 * @Author cx
 * @Date 2023/8/17 0:58
 * @Version 1.0
 */
public class NettyTest {

    @Test
    public void testCompositeByteBuf(){
        ByteBuf header = Unpooled.buffer();
        ByteBuf body = Unpooled.buffer();

        CompositeByteBuf byteBufs = Unpooled.compositeBuffer();
        //多个区域bytebuf组成逻辑上的一个 逻辑组装而不是物理拷贝 实现jvm上的零拷贝(jdk的bytebuffer实现不了)
        byteBufs.addComponents(header,body);
    }

    @Test
    public void testSlice(){
        byte[] bytes = new byte[1024];
        byte[] bytes2 = new byte[1024];
        //共享同一个存储空间 不去拷贝 将多个byte合并为逻辑上的一个 仅包装--零拷贝
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, bytes2);

        //将一个bytebuf分割成多个 共享地址 而非拷贝
        ByteBuf slice1 = byteBuf.slice(1, 5);
        ByteBuf slice2 = byteBuf.slice(6, 10);
        //System.out.println(Arrays.toString(slice1.array()));
    }

    @Test
    public void testWrapper(){
        byte[] bytes = new byte[]{1, 2, 3};
        byte[] bytes2 = new byte[]{4, 5, 6};
        //共享同一个存储空间 不去拷贝 将多个byte合并为逻辑上的一个 仅包装--零拷贝
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, bytes2);

        // 获取ByteBuffer，才能访问数据
        ByteBuffer nioBuffer = byteBuf.nioBuffer();
        // remaining获取剩余可读字节的数量 来创建一个新数组
        byte[] byteArray = new byte[nioBuffer.remaining()];
        // 将数据从 ByteBuffer 复制到数组  这里有涉及拷贝？！
        nioBuffer.get(byteArray);

        System.out.println(Arrays.toString(byteArray));
    }


}
