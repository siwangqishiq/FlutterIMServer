package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.handler.ServiceHandler;
import xyz.panyi.imserver.model.LoginReq;
import xyz.panyi.imserver.model.LoginResp;
import xyz.panyi.imserver.model.Msg;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.AuthService;
import xyz.panyi.imserver.service.IAuthService;
import xyz.panyi.imserver.service.OnlineUsers;
import xyz.panyi.imserver.service.UserDataCache;
import xyz.panyi.imserver.token.SecurityHelper;

/**
 *  登录报文
 *
 */
public class LoginAction implements IAction {

    private UserLoginDo userLoginDo = new UserLoginDo();

    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg , ServiceHandler serviceHandler) {
        LoginReq req = new LoginReq();
        req.decode(msg.getData());

        System.out.println("accout ----> " + req.getAccount());
        System.out.println("password ----> " + req.getPwd());

        final String account = req.getAccount();
        final String pwd = req.getPwd();

        IAuthService authService = new AuthService();

        LoginResp resp = new LoginResp();

        if(authService.auth(account , pwd)){//登录成功
            resp.setResultCode(LoginResp.RESULT_CODE_SUCCESS);

            User user = UserDataCache.getInstance().getUserByAccount(account);

            String token = SecurityHelper.createToken(user.getUid() , account , pwd);
            resp.setToken(token);

            if(user != null){
                resp.setAccount(user.getAccount());
                resp.setUid(user.getUid());
                resp.setAvator(user.getAvator());
                resp.setDisplayName(user.getDisplayName());

                resp.setSex(user.getSex());
                resp.setDesc(user.getDesc());
                resp.setAge(user.getAge());

                ctx.writeAndFlush(resp);

                userLoginDo.userLogin(user , token , serviceHandler);
            }
        }else{
            resp.setResultCode(LoginResp.RESULT_CODE_ERROR);
            resp.setToken(null);
            ctx.writeAndFlush(resp);
        }
    }

}//end class
