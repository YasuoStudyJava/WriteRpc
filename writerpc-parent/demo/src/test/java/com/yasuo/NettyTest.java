package com.yasuo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

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
        //多个bytebuf组成逻辑上的一个 逻辑组装而不是物理拷贝 实现呢jvm上的零拷贝
        byteBufs.addComponents(header,body);
    }
}
