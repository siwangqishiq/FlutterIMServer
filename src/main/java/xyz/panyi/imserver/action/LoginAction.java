package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
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


    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg) {
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

                userLogin(user , token , ctx);
            }
        }else{
            resp.setResultCode(LoginResp.RESULT_CODE_ERROR);
            resp.setToken(null);
        }

        System.out.println("resp = " + resp);

        ctx.writeAndFlush(resp);
    }


    /**
     * 用户上线
     *
     * @param user
     * @param token
     * @param ctx
     */
    private void userLogin(User user , String token , ChannelHandlerContext ctx){
        OnlineUsers.getInstance().userOnline(user.getUid() ,  ctx , user);//添加到在线用户列表
    }


}//end class
