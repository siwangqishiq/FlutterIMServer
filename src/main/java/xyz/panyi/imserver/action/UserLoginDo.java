package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.Friend;
import xyz.panyi.imserver.model.FriendsResp;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.OnlineUsers;
import xyz.panyi.imserver.service.UserDataCache;

import java.util.ArrayList;
import java.util.List;

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

        responseContacts(user , ctx);
    }

    /**
     * 返回好友列表
     * @param user
     * @param ctx
     */
    public void responseContacts(User user , ChannelHandlerContext ctx){
        List<Long> friendUids = user.getFriends();

        List<Friend> contacts = new ArrayList<Friend>();
        for(Long uid : friendUids){
            User u = UserDataCache.getInstance().getUserByUid(uid);
            if(u != null){
                contacts.add(Friend.buildFriendFromUser(u));
            }
        }//end for i

        FriendsResp resp = new FriendsResp();
        resp.setResult(FriendsResp.RESULT_SUCCESS);

        //System.out.println("contact size = " + contacts.size());

        resp.setFriendList(contacts);

        ctx.writeAndFlush(resp);
    }
}
