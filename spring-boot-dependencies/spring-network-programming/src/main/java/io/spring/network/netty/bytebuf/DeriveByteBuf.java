package io.spring.network.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 * copy
 * duplicate
 * slice
 */
public class DeriveByteBuf {

    public static void main(String[] args) {

        // slice类似String的slice切分
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", CharsetUtil.UTF_8);
        ByteBuf sliceBuf = byteBuf.slice(0, 15);
        byteBuf.setByte(0, 'J');
        System.out.println(byteBuf.getByte(0) == sliceBuf.getByte(0));// true

        // copy方法会复制一份缓冲区的真是副本，复制出的ByteBuf具有独立的数据副本
        ByteBuf copyBuf = byteBuf.copy();
        byteBuf.setByte(0, 'N');
        System.out.println(byteBuf.getByte(0) == copyBuf.getByte(0));// false

        // duplicate返回一个新的ByteBuf实例，但是readIndex & writeIndex都是与原ByteBuf共享的
        ByteBuf duplicate = byteBuf.duplicate();
        byteBuf.setByte(0, 'J');
        System.out.println(byteBuf.getByte(0) == duplicate.getByte(0));// true

        // print 'J' index of byteBuf: 0
        System.out.println(byteBuf.indexOf(0, 15, "J".getBytes()[0]));

        DefaultByteBufHolder byteBufHolder = new DefaultByteBufHolder(Unpooled.copiedBuffer("hello".getBytes()));
    }
}
