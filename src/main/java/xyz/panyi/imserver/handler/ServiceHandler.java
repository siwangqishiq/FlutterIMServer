package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import xyz.panyi.imserver.action.AutoLoginAction;
import xyz.panyi.imserver.action.IAction;
import xyz.panyi.imserver.action.LoginAction;
import xyz.panyi.imserver.action.LoginOutAction;
import xyz.panyi.imserver.model.*;
import xyz.panyi.imserver.util.LruCache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * im业务服务handler
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Msg> {

    public static final long RECIPT_TIME_OUT = 30 * 1000;//超时重发时间

    private Map<Long, RecipeMsg> mRetryMsgsMap = new HashMap<Long, RecipeMsg>();

    private ChannelHandlerContext mChannelContext;

    public ChannelHandlerContext getChannelHandlerContext(){
        return mChannelContext;
    }

    public User mUser; //标示远端用户身份  未登录时为null

    private LruCache<Long,RecipeMsg> mHasReceivedMsgMap = new LruCache(128);

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
        System.out.println("read msg = " + msg);

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
                handleRecipt(msg);
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
    public void handleRecipt(Msg msg) {
        RecipeAck reciptAck = new RecipeAck();
        reciptAck.decode(msg.getData());

        long uuid = reciptAck.getSendUuid();
        mRetryMsgsMap.remove(uuid);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 发送保证必达的消息
     *
     * @param reciptMsg
     */
    public void sendRecipeMsg(final RecipeMsg reciptMsg) {
        mChannelContext.writeAndFlush(reciptMsg).addListener((future) -> {
            mRetryMsgsMap.put(reciptMsg.getUuid() , reciptMsg);
            //启动定时器
            mChannelContext.executor().schedule(() -> {
                retryReciptMsg(reciptMsg.getUuid());
            }, RECIPT_TIME_OUT, TimeUnit.MILLISECONDS);
        });
    }

    /**
     *
     *  超时后重发消息逻辑
     */
    private void retryReciptMsg(long msgUuid) {
        if (mChannelContext == null || mRetryMsgsMap.get(msgUuid) == null)
            return;

        final RecipeMsg msg = mRetryMsgsMap.get(msgUuid);

        msg.sendTimes++;
        if (msg.sendTimes > RecipeMsg.MAX_RETRY_TIMES) {//超过最大重试次数
            System.out.println("多次重试后 移除消息 uuid = " + msg.getUuid());
            mRetryMsgsMap.remove(msgUuid);

            if (msg.getCallback() != null) {
                msg.getCallback().onSendError(mUser);
            }
        } else {
            System.out.println("重发消息 uuid = " + msg.getUuid());
            sendRecipeMsg(msg);
        }
    }

}//end class
