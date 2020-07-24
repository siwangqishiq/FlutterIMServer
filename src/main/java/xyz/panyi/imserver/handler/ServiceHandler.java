package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.panyi.imserver.action.AutoLoginAction;
import xyz.panyi.imserver.action.IAction;
import xyz.panyi.imserver.action.LoginAction;
import xyz.panyi.imserver.action.LoginOutAction;
import xyz.panyi.imserver.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * im业务服务handler
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Msg> {

    public static final long RECIPT_TIME_OUT = 30 * 1000;//超时重发时间
    public static final int MAX_RETRY_TIMES = 3;

    private Map<Long, Codec> mRetryMsgsMap = new HashMap<Long, Codec>();

    private ChannelHandlerContext mChannelContext;

    public ChannelHandlerContext getChannelHandlerContext(){
        return mChannelContext;
    }

    public User mUser; //标示远端用户身份  未登录时为null


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        mChannelContext = ctx;
        System.out.println("channel active " + ctx.channel().remoteAddress().toString());
        //sayHello(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive " + ctx.channel().remoteAddress().toString());
        ctx.close();
    }

    /**
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("read msg = " + msg +"  uuid = " + msg.getUuid());

        if (msg == null || msg.getData() == null)
            return;

        IAction action = null;

        switch (msg.getCode()) {
            case Codes.CODE_LOGIN_REQ: //登录
                action = new LoginAction();
                break;
            case Codes.CODE_LOGINOUT_REQ: //注销
                action = new LoginOutAction();
                break;
            case Codes.CODE_AUTO_LOGIN_REQ://自动登录
                action = new AutoLoginAction();
                break;
            case Codes.CODE_RECIPT_ACK:
                handleReciptAck(msg);
                break;
            default:
                break;
        }//end switch

        if (action != null) {
            action.handle(ctx, msg, this);
        }
//        String str = new String(msg.getData() , CharsetUtil.UTF_8);
//        System.out.println("data = " + str);
    }

    /**
     * recipt消息的返回 处理
     * 从缓存中移除重发消息
     */
    public void handleReciptAck(Msg msg) {
        RecipeAck reciptAck = new RecipeAck();
        reciptAck.decode(msg.getData());

        long uuid = reciptAck.getSendUuid();
        System.out.println("removd uuid = " + uuid);
        mRetryMsgsMap.remove(uuid);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //ctx.close();
    }

    public void sendCodec(final Codec codec) {
        final ChannelFuture future = mChannelContext.writeAndFlush(codec);

        if(codec.needSendRetry()){ //发送的消息要求具有超时重发机制  启动定时重发任务
            future.addListener((f) -> {
                mRetryMsgsMap.put(codec.uuid , codec);
                //启动定时器
                mChannelContext.executor().schedule(() -> {
                    retryReciptMsg(codec.uuid);
                }, RECIPT_TIME_OUT, TimeUnit.MILLISECONDS);
            });
        }
    }

    /**
     *
     *  超时后重发消息逻辑
     */
    private void retryReciptMsg(long msgUuid) {
        if (mChannelContext == null || mRetryMsgsMap.get(msgUuid) == null)
            return;

        final Codec codecMsg = mRetryMsgsMap.get(msgUuid);
        codecMsg.sendTimes++;
        if (codecMsg.sendTimes > MAX_RETRY_TIMES) {//超过最大重试次数
            System.out.println("多次重试后 移除消息 uuid = " + codecMsg.uuid);
            mRetryMsgsMap.remove(msgUuid);

            if (codecMsg.getCallback() != null) {
                codecMsg.getCallback().onSendError(codecMsg , mUser);
            }
        } else {
            System.out.println("重发消息 uuid = " + codecMsg.uuid);
            sendCodec(codecMsg);
        }
    }

}//end class
