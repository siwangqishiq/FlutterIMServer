package xyz.panyi.imserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import xyz.panyi.imserver.handler.CodecMsg;
import xyz.panyi.imserver.model.Msg;

public class TestClient {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); //
            b.group(workerGroup); //
            b.channel(NioSocketChannel.class); //
            b.option(ChannelOption.SO_KEEPALIVE, true); //
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new CodecMsg());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 1998).sync(); //

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    /**
     *
     *
     */
    public static class ClientHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channel active");


            String str = "Hello 你好世界 hahaha";

            //ByteBuf dataBuf = Unpooled.copiedBuffer(str.getBytes(CharsetUtil.UTF_8));
            int code = 101;

            Msg helloMsg = Msg.genMsg(code , str.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(helloMsg);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {

        }
    }
}
