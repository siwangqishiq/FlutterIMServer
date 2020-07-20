package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import xyz.panyi.imserver.action.AutoLoginAction;
import xyz.panyi.imserver.action.IAction;
import xyz.panyi.imserver.action.LoginAction;
import xyz.panyi.imserver.action.LoginOutAction;
import xyz.panyi.imserver.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  im业务服务handler
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Msg> {

    public static final long RECIPT_TIME_OUT = 30 * 1000;//超时重发时间

    private Map<Long, ReciptMsg> mRetryMsgsMap = new HashMap<Long,ReciptMsg>();

    private ChannelHandlerContext mChannelContext;

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
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println("read msg = " + msg);

        if(msg == null|| msg.getData() ==null )
            return;

        IAction action = null;

        switch (msg.getCode()){
            case Codes.CODE_LOGIN_REQ: //登录
                action = new LoginAction();
                break;
            case Codes.CODE_LOGINOUT_REQ: //注销
                action = new LoginOutAction();
                break;
            case Codes.CODE_AUTO_LOGIN_REQ://自动登录
                action = new AutoLoginAction();
                break;

        }//end switch

        if(action != null){
            action.handle(ctx , msg);
        }

//        String str = new String(msg.getData() , CharsetUtil.UTF_8);
//        System.out.println("data = " + str);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

//    private void handlePersonReq(ChannelHandlerContext ctx ,byte[] bytesData){
//        Person person = new Person();
//        person.decode(bytesData);
//
//        PersonResp resp = new PersonResp();
//        resp.setRespContent(person.getName()+"是谁啊?");
//        resp.setTime(System.currentTimeMillis());
//
//        System.out.println("person name = " + person.getName() +
//                "   age = " + person.getAge() +" desc  =  " + person.getDesc());
//
//        ctx.writeAndFlush(resp);
//    }

    private void sayHello(ChannelHandlerContext ctx){
        ctx.executor().submit(()->{
            for(int i = 0 ; i < 10 ;i++){
                final int index = i;
                String s = "你好世界_ " + index;

                ctx.writeAndFlush(new StringWrap(s));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //ctx.close();
        });
    }

    /**
     * 发送保证必达的消息
     * @param ctx
     * @param reciptMsg
     */
    public void sendReciptMsg(ChannelHandlerContext ctx ,final ReciptMsg reciptMsg){
        ctx.writeAndFlush(reciptMsg).addListener((future)->{
            //启动定时器
            ctx.executor().scheduleAtFixedRate(()->{
                //加入Map中
                reciptMsg.sendTimes++;
                mRetryMsgsMap.put(reciptMsg.getUuid() , reciptMsg);

                retryReciptMsg();
            },RECIPT_TIME_OUT , RECIPT_TIME_OUT , TimeUnit.MILLISECONDS);
        });
    }

    /**
     * 超时后重发消息逻辑
     */
    private void retryReciptMsg(){
        if(mChannelContext == null || mRetryMsgsMap.isEmpty())
            return;


    }

}//end class
