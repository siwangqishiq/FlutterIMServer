package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.*;

/**
 * 自动登录 接口请求
 */
public class AutoLoginAction extends CheckTokenAction<AutoLoginReq> {

    private UserLoginDo userLoginDo = new UserLoginDo();

    @Override
    AutoLoginReq createDataBean(Msg msg) {
        AutoLoginReq req = new AutoLoginReq();
        req.decode(msg.getData());
        return req;
    }

    @Override
    void vertifySuccess(ChannelHandlerContext ctx, AutoLoginReq bean, User user) {
        userLoginDo.userLogin(user , bean.getToken() , ctx);

        final AutoLoginResp resp = new AutoLoginResp();
        resp.setResult(AutoLoginResp.RESULT_SUCCESS);

        ctx.writeAndFlush(resp);
    }

    @Override
    void vertifyError(ChannelHandlerContext ctx, AutoLoginReq bean) {
        ResetResp resp = new ResetResp(ResetResp.RESET_CODE_ERRORTOKEN , "自动登录失败");
        ctx.writeAndFlush(resp);
    }

}//end class
