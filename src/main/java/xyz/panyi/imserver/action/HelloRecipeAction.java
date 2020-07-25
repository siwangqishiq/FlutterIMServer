package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.handler.ServiceHandler;
import xyz.panyi.imserver.model.HelloRecipeMsg;
import xyz.panyi.imserver.model.Msg;

/**
 * 处理测试消息
 */
public class HelloRecipeAction implements IAction {

    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg , ServiceHandler serviceHandler) {
        HelloRecipeMsg helloRecipeMsg = new HelloRecipeMsg();
        helloRecipeMsg.decode(msg.getData());

        System.out.println("Hello Receipe Action : " + helloRecipeMsg.getHello());
        System.out.println("Hello Receipe Action uuid : " + helloRecipeMsg.uuid);

    }

}//end class
