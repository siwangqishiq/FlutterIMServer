package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.handler.ServiceHandler;
import xyz.panyi.imserver.model.*;
import xyz.panyi.imserver.service.AuthService;
import xyz.panyi.imserver.service.IAuthService;
import xyz.panyi.imserver.service.UserDataCache;
import xyz.panyi.imserver.token.SecurityHelper;

/**
 * 需要做权限检查的action
 * @param <T>
 */
public abstract class CheckTokenAction <T extends BaseTokenBean> implements IAction{

    abstract T createDataBean(Msg msg);

    /**
     * token验证成功
     * @param ctx
     * @param bean
     */
    abstract void vertifySuccess(ChannelHandlerContext ctx , T bean , User user);

    /**
     * token验证失败
     * @param ctx
     * @param bean
     */
    abstract void vertifyError(ChannelHandlerContext ctx , T bean);

    @Override
    public void handle(ChannelHandlerContext ctx, Msg msg , ServiceHandler serviceHandler) {
        BaseTokenBean data = createDataBean(msg);

        final String token = data.getToken();
        System.out.println("token ----> " + token);

        boolean check = SecurityHelper.vertifyToken(token ,
                (token_ , uid , account , pwd)->{
                    IAuthService authService = new AuthService();
                    return authService.authUid(uid , pwd);
                });

        if(check){
            String account = SecurityHelper.getAccountFromToken(token);
            User user = UserDataCache.getInstance().getUserByAccount(account);

            vertifySuccess(ctx , (T)data , user);
        }else{
            vertifyError(ctx , (T)data);
        }
    }

}
