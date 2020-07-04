package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.LoginReq;
import xyz.panyi.imserver.model.Msg;

/**
 *  登录报文
 *
 */
public class LoginAction implements IAction {

    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg) {
        LoginReq req = new LoginReq();
        req.decode(msg.getData());

        System.out.println("accout ----> " + req.getAccount());
        System.out.println("password ----> " + req.getPwd());
    }
}//end class
