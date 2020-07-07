package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.LoginOutReq;
import xyz.panyi.imserver.model.LoginOutResp;
import xyz.panyi.imserver.model.Msg;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.AuthService;
import xyz.panyi.imserver.service.IAuthService;
import xyz.panyi.imserver.service.OnlineUsers;
import xyz.panyi.imserver.token.SecurityHelper;

/**
 *  注销报文
 *
 */
public class LoginOutAction implements IAction {

    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg) {
        LoginOutReq req = new LoginOutReq();
        req.decode(msg.getData());


        System.out.println("uid ----> " + req.getUid());
        System.out.println("token ----> " + req.getToken());

        boolean check = SecurityHelper.vertifyToken(req.getToken() ,
                (token , uid , account , pwd)->{
                    IAuthService authService = new AuthService();
                    return authService.authUid(uid , pwd) && req.getUid() == uid;
                });
        LoginOutResp response = new LoginOutResp();
        response.setResult(check?LoginOutResp.RESULT_LOGINOUT_SUCCESS : LoginOutResp.RESULT_LOGINOUT_ERROR);

        ctx.writeAndFlush(response);
    }

}//end class
