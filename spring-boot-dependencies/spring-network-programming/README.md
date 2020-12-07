## Netty

> OIO：最开始表示为旧的输入/输出（Old I/O），后又可以理解为阻塞输入/输出（Block I/O）。
>
> NIO：最开始表示为新的输入/输出（New I/O），后又可以理解为非阻塞输入/输出（Non-Block I/O）。

### 核心组件

#### 组件概览

![](https://image.leejay.top/FoaBOgwzVOuNzRywEDPxFkExNsLp)

#### Channel

基于Socket的进一步封装，降低了Socket的复杂度。包含众多的实现：`NioSocketChannel`、`NioServerSocketChannel`等。

#### EventLoop

Netty的核心抽象，用于处理连接的生命周期中所发生的事件。

![](https://image.leejay.top/FugXYl_baoDHRycBFmCY50w4ufq3)

> 1. 一个EventLoopGroup包含一个或多个EventLoop。
> 2. 一个EventLoop在其生命周期中只和一个Thread绑定。该EventLoop处理的I/O都在该Thread上被处理。
> 3. 一个Channel在其生命周期中只会被注册到一个EventLoop中。
> 4. 一个EventLoop可以被分配个一个或多个Channel。

#### ChannelFuture

因为Netty的操作都是异步的，基于`Future`的`ChannelFuture`的接口，添加`ChannelFutureListener`来实现某个操作完成时被通知。

> 同属于一个channel的操作都会被保证以它们`被调用的顺序`来执行。

#### ChannelHandler

用于处理`所有进出站的数据`的事件处理器。实现类包括`ChannelInboundHandler`、`ChannelOutboundHandler`。

```java
public interface ChannelHandler {}

public interface ChannelInboundHandler 
    	extends ChannelHandler {}

public class ChannelInboundHandlerAdapter 
    	extends ChannelHandlerAdapter implements ChannelInboundHandler {}

public abstract class SimpleChannelInboundHandler<I> 
    	extends ChannelInboundHandlerAdapter {}
```

#### ChannelPipeline

用于存储`ChannelHandler链`的容器。在应用程序初始化时（BootStrap引导）通过`ChannelInitializer`将自定义的`ChannelHandler`注册到`ChannelPipeline`中。

```java
public interface ChannelPipeline
        extends ChannelInboundInvoker, ChannelOutboundInvoker, 
				Iterable<Entry<String, ChannelHandler>> {}
```

> 接口的继承表明：ChannelPipeline可以处理`入站和出站`的ChannelHandler链。

```java
public class EchoClient {
    Bootstrap bootstrap = new Bootstrap()
        .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                // 将自定义的ChannelHandler加入到ChannelPipeline的最后
                ch.pipeline().addLast(
                    new EchoClientHandlerFirst(), 
                    new EchoClientHandler(), 
                    new EchoClientHandlerLast());
            }
        });
}

@Slf4j
@ChannelHandler.Sharable
public class EchoClientHandlerFirst extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("EchoClientHandlerFirst received: {}", msg.toString(CharsetUtil.UTF_8));
        // 使用Unpooled.copiedBuffer()处理ByteBuf的release问题
        // 将msg传递到下个ChannelHandler
        ctx.fireChannelRead(Unpooled.copiedBuffer(msg));
    }
}

@Slf4j
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        // client接收到信息时被调用
        log.info("EchoClientHandler received: {}", msg.toString(CharsetUtil.UTF_8));
        ctx.fireChannelRead(Unpooled.copiedBuffer(msg));
    }
}

@Slf4j
@ChannelHandler.Sharable
public class EchoClientHandlerLast extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("EchoClientHandlerLast received: {}", msg.toString(CharsetUtil.UTF_8));
    }
}
```

> 通过`handler方法`将ChannelPipeline绑定到bootstrap中，ChannelPipeline中的ChannelHandler的顺序由`添加时的顺序`决定。
>
> 即为：EchoClientHandlerFirst -> EchoClientHandler -> EchoClientHandlerLast。

![](https://image.leejay.top/Fuyanerz_JSEi_Msd-OOYXYxkjN-)

> 入站和出站是相对的，以上图为例：Server端入站即是Client端的出站，Server端的出站即是Client端的入站。
>
> 虽然ChannelInboundHandler和ChannelOutboundHandler都扩展自ChannelHandler，但是Netty能够保证`数据旨在具有相同的两个类型的ChannelHandler中传递。`

#### ChannelHandlerContext

作为参数传递由上一个ChannelHandler传递到下一个ChannelHandler中。包含

```java
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // channel是活跃时被调用
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty Rock", CharsetUtil.UTF_8));
        // 从下一个ChannelHandler开始流动
        ctx.fireChannelRead(Unpooled.copiedBuffer(msg));
        // 从第一个ChannelHandler开始流动
        // ctx.pipeline().fireChannelRead(Unpooled.copiedBuffer(msg));
    }
}
```

> 可以通过`ChannelHandlerContext`写入消息，消息会从`下一个ChannelHandler`开始流动。
>
> 如果是将消息写入`Channel`，那么会从`第一个ChannelHandler`开始流动。

#### Bootstrap

服务端和客户端采用了不同的引导，主要的区别在于：

1. 客户端Bootstrp连接到`远程的地址和端口`，而服务端则绑定到`本地的端口`。
2. 客户端只需要一个`EventLoopGroup`，而服务端需要两个，其中一个只包含`ServerChannel`，代表服务自身的已绑定到某个本地端口的正在监听的socket，另一个包含所有已创建的、用于传入客户端连接的channel。

```java
Bootstrap bootstrap = new Bootstrap();// client
ServerBootstrap bootstrap = new ServerBootstrap();// server
```

----

### Channel

#### Channel's API

![](https://image.leejay.top/FtELlIBc5Eva4RcRK9P30RL-QDES)

> Channel是`线程安全`的，在多线程下发送消息，其顺序由发送的顺序决定。

#### Channel的内置传输

- NIO

提供基于JDK的NIO进行封装的API，所有I/O操作都是异步的，其核心就是`Selector`选择器（和某个线程绑定），会在：`新的Channel已被接受且就绪`、`Channel连接已经完成`、`Channel有已就绪的可供读取的数据`、`Channel可用于写数据`时发出通知。

![](https://image.leejay.top/FvMwqVoSwPIQ8_wJTxUQfwoe5OZj)

- Epoll

基于Linux的Epoll特性（一个高度可扩展的I/O事件通知特性），相比原生的JDK-NIO有更强的性能，但执行流程与NIO相同。

- OIO

原生JDK中的BIO是阻塞实现，Netty利用了`SO_TIMEOUT`标识，当阻塞一定时长时，抛出`SocketTimeOut`异常并捕获该异常，并继续循环，在EventLoop下次运行时将再次尝试。

![](https://image.leejay.top/FnOwK-sLN9h3FvLyL7vI6ERsGWaW)

- Local

用于同一个JVM中运行的客户端和服务器程序之间的异步通信，它并没有绑定物理网络地址，不接受真正的网络流量。

- Embedded

将一组`ChannelHandler`作为帮助类嵌入到其他的`ChannelHandler`内部，而不需要去修改代码。

##### 总结

|          | Channel                  | EventLoopGroup      | 支持协议         | 使用需求                   |
| -------- | ------------------------ | ------------------- | ---------------- | -------------------------- |
| NIO      | NioServerSocketChannel   | NioEventLoopGroupNi | TCP/UDP/SCTP/UDT | 代码库中没有非阻塞调用     |
| Epoll    | EpollServerSocketChannel | EpollEventLoopGroup | TCP/UDP          | 相比NIO，在Linux上推荐使用 |
| OIO      | OioServerSocketChannel   | OioEventLoopGroup   | TCP/UDP/SCTP/UDT | 阻塞代码库（JDBC等）       |
| Local    | LocalServerChannel       | LocalEventLoopGroup | -                | 同一个JVM内部的通信        |
| Embedded | EmbeddedEventLoop        | EmbeddedChannel     | -                | 测试ChannelHandler的实现   |

---

### ByteBuf

**`ByteBuf`**是基于JDK原生的`ByteBuffer`封装而来的字节容器。通过`ByteBuf`抽象类和`ByteBufHolder`接口进行暴露。具有`自定义缓冲区类型扩展`、`基于零拷贝`、`按需增长`、`读写使用不同的索引`、`支持池化`等优点。

#### ByteBuf类

ByteBuf维护了两个不同的索引：读索引 & 写索引（这与ByteBuffer类不同，它只维护了一个索引），初始情况两者都为0，且不应超过capacity。ByteBuf可以指定一个初始容量，但最大不超过`Integer.MAX_VALUE`，内置的API中`以Read、Write开头的方法会推动索引变化，而get、set开头的则不会`。

##### ByteBuf的使用模式

- 堆缓冲区

将数据存储在JVM的堆空间中，又被称为`支撑数组`。其能在没有池化的情况下提供快速的分配与释放，适合有遗留数据需要处理的情况。

```java
public void dump() {
    ByteBuf byteBuf = ...;
    if (byteBuf.hasArray()) { // 判断是否有支撑数组
        byte[] array = byteBuf.array(); // 若有就获取该数组的引用
        int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();  // 计算第一个字节的偏移量
        int length = byteBuf.readableBytes(); // 计算可读字节的长度
    }
}
```

- 直接缓冲区

自JDK1.4后允许JVM调用本地方法(native)来分配堆外内存(又称为直接内存)。主要是为了避免`I/O操作前后将缓冲区内容复制到一个中间缓冲区`。相比于`堆内缓冲区`，`直接缓冲区`的分配和释放都是昂贵的。

```java
public void direct() {
    ByteBuf directBuffer = Unpooled.directBuffer(16);
    if (!directBuffer.hasArray()) { // 判断是否有支撑数组
        // 还未读的字节大小
        int length = directBuffer.readableBytes();
        byte[] array = new byte[length];
        // 获取剩下的未读数据
        directBuffer.getBytes(directBuffer.readerIndex(), array);
    }
}
```

- 复合缓冲区

是多个`ByteBuf`的一个聚合视图，是JDK原生所没有的功能。此类型可以同时包含堆内缓冲区和直接缓冲区，如果只有一个ByteBuf的实例，那么`hasArray()`会返回该实例的`hasArray()`的值，否则返回false。

```java
public void composite() {
    CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
    // 添加堆内和堆外两种模式的数据
    compositeByteBuf.addComponents(
        Unpooled.directBuffer(16), Unpooled.copiedBuffer("Hello".getBytes()));
}
```

#### ByteBuf字节操作

![](https://image.leejay.top/FlQi0WTAq7pvHL8xIgMUQIYrPqcf)

> 1. 初始状态下：`readIndex = writeIndex = 0`，若`readIndex | writeIndex > capacity-1`或`readIndex > WriteIndex`，则会抛出`indexOutOfBoundException`。
> 2. 可丢弃字节，可以理解为已读字节，`[0, readIndex]`部分的字节已被全部读取，`(readIndex, writeIndex]`部分的字节可以被读取，`(writeIndex，capacity)`部分的字节尚未被写入。
> 3. 调用`discardReadBytes()`方法后会丢弃`已读字节`并回收他们，此时`readIndex`会被移动到缓冲区的开始位置。
> 4. `get/set`开头的方法不会修改index的位置，而`read/write`则会修改。

##### 派生缓冲区

```java
public void test() {
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
}
```

> 非特殊需要，`slice()`方法能满足就用该方法，避免`复制`带来的内存开销。

#### ByteBufHolder接口

`ByteBuf`的容器，为了满足除了基本的`ByteBuf`数据负载外，还要满足类似`HTTP`响应返回的各种属性值，`ByteBufHolder`包含`一个ByteBuf对象`，可以按需实现不同的需求。

```java
DefaultByteBufHolder byteBufHolder = new DefaultByteBufHolder(
    													Unpooled.copiedBuffer("hello".getBytes()));
```

#### ByteBuf分配

##### ByteBufAllocator

Netty基于ByteBufAllocator接口实现了ByteBuf的`池化`，它可以分配任意类型的ByteBuf实例。

```java
public void alloc() {
    // Netty默认的分配
    ByteBufAllocator allocator = new PooledByteBufAllocator();
    allocator.buffer(); //堆或直接缓冲区
    allocator.heapBuffer(); // 堆内缓冲区
    allocator.directBuffer(); // 直接缓冲区
    allocator.compositeBuffer(); // 复合缓冲区
}
```

> Netty默认使用`PooledByteBufAllocator`类作为分配的规则。但也提供了`UnpooledByteBufAllocator`。前者`池化`了ByteBuf实例以提高性能并最大程度减少内存碎片，后者则`不池化`ByteBuf实例，每次调用都返回一个新的实例。

我们还可以通过`Channel`对象或绑定到Channel的`ChannelHandlerContext`来获取`ByteBufAllocator`的引用。

```java
public interface ChannelHandlerContext extends AttributeMap, 
											   ChannelInboundInvoker, ChannelOutboundInvoker {
  	ByteBufAllocator alloc();
}
```

##### Unpooled缓冲区

```java
public void unPool() {
    Unpooled.copiedBuffer("Hello".getBytes());
    Unpooled.directBuffer();
    Unpooled.buffer();
    Unpooled.wrappedBuffer("Hello".getBytes());
}
```

> copiedBuffer()与wrappedBuffer()的区别在于：前者会生成一个`新的完全独立`的ByteBuf，而后者与传入的byte[]仍是共享的(类似duplicate()。且它们底层是创建`堆内`缓冲区。

##### ByteBufUtil

用于操作ByteBuf的静态方法，和池化无关，可单独使用。常用的有`equals（比较两个ByteBuf是否相等）`、`hexDump（返回ByteBuf的十六进制内容）`等。

##### 引用计数法

在Netty的池化中，存有一个重要的概念：`引用计数法`，只要该实例的引用大于0，就不会被释放，如果引用降低到0，那该实例就会被释放。一般由最后访问的该实例的那一方负责释放。

```java
public interface ReferenceCounted {
    int refCnt();
    ReferenceCounted retain();
    ReferenceCounted retain(int increment);
    ReferenceCounted touch();
    ReferenceCounted touch(Object hint);
    boolean release();
    boolean release(int decrement);
}
```

#### ByteBuf总结

![](https://image.leejay.top/Fs8QHe15SgVPrHy60QimS6HBGYTt)

---

### ChannelHandler & ChannelPipeline

