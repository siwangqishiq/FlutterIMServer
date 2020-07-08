package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.LoginOutReq;
import xyz.panyi.imserver.model.LoginOutResp;
import xyz.panyi.imserver.model.Msg;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.OnlineUsers;

/**
 *  注销报文
 *
 */
public class LoginOutAction extends CheckTokenAction<LoginOutReq> {

    @Override
    LoginOutReq createDataBean(Msg msg) {
        LoginOutReq req = new LoginOutReq(null);
        req.decode(msg.getData());
        return req;
    }

    @Override
    void vertifySuccess(ChannelHandlerContext ctx, LoginOutReq bean , User user) {
        System.out.println("uid ----> " + bean.getUid());

        if(user.getUid() == bean.getUid()){
            OnlineUsers.getInstance().userOffline(user.getUid());

            LoginOutResp resp = new LoginOutResp();
            resp.setResult(LoginOutResp.RESULT_LOGINOUT_SUCCESS);
            ctx.writeAndFlush(resp);
        }else{
            vertifyError(ctx , bean);
        }
    }

    @Override
    void vertifyError(ChannelHandlerContext ctx, LoginOutReq bean) {
        LoginOutResp response = new LoginOutResp();
        response.setResult(LoginOutResp.RESULT_LOGINOUT_ERROR);
        ctx.writeAndFlush(response);
    }

}//end class
