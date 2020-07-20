package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DemoHandler extends ChannelInboundHandlerAdapter implements ChannelOutboundHandler {
    public static class SendMessage {
        public int id;
        public int times;
        // 每次要发送的数据
        public Object data;
    }


    // 这里面缓存已发送的消息
    private Map<Integer, SendMessage> map = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Map<Integer, SendMessage> newMap = new HashMap<>();
        // 每隔三秒重发一次消息
        ctx.executor().scheduleAtFixedRate(() -> {
            if (map.size() > 0) {
                map.forEach((k, v) -> {
                    ctx.writeAndFlush(v.data);
                    v.times = v.times + 1;
                    if (v.times < 3) {
                        // 保留未发送三次的。
                        newMap.put(v.id, v);
                    }
                });
            }
            map = newMap;
        }, 3, 3, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 从msg中获取客户端应答的消息id，收到客户端的响应。
        int id = 0;
        map.remove(id);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 这里是拦截发送的消息
        SendMessage seMessage = new SendMessage();
        seMessage.data = msg;
        // 记录一下id,这个id由你的业务决定，只要每个消息唯一即可
        seMessage.id = 1;
        seMessage.times = 1;
        //把发送的消息缓存起来，等待客户端的响应
        map.put(seMessage.id, seMessage);
        ctx.writeAndFlush(seMessage);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub

    }



    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub

    }
}
