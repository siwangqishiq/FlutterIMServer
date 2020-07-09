package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.OnlineUsers;

/**
 * 用户登录后统一流程处理
 */
public class UserLoginDo {
    /**
     * 用户上线
     *
     * @param user
     * @param token
     * @param ctx
     */
    public void userLogin(User user , String token , ChannelHandlerContext ctx){
        OnlineUsers.getInstance().userOnline(user.getUid() ,  ctx , user);//添加到在线用户列表
    }
}
