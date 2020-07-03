package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import xyz.panyi.imserver.model.Codes;
import xyz.panyi.imserver.model.Msg;
import xyz.panyi.imserver.model.Person;
import xyz.panyi.imserver.model.StringWrap;

/**
 *  im业务服务handler
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active " + ctx.channel().remoteAddress().toString());
        //sayHello(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive " + ctx.channel().remoteAddress().toString());
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

        switch (msg.getCode()){
            case Codes.CODE_TEST_REQ://测试请求
                handlePersonReq(msg.getData());
                break;

        }//end switch

//        String str = new String(msg.getData() , CharsetUtil.UTF_8);
//        System.out.println("data = " + str);
    }

    private void handlePersonReq(byte[] bytesData){
        Person person = new Person();
        person.decode(bytesData);

        System.out.println("person name = " + person.getName() + "   age = " + person.getAge() +" desc  =  " + person.getDesc());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

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

}//end class
