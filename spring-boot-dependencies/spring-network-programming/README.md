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
