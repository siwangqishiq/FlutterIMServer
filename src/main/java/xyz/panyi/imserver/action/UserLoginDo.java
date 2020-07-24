package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.handler.ServiceHandler;
import xyz.panyi.imserver.model.Friend;
import xyz.panyi.imserver.model.FriendsResp;
import xyz.panyi.imserver.model.HelloRecipeMsg;
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
     * @param serviceHandler
     */
    public void userLogin(User user , String token , ServiceHandler serviceHandler){
        OnlineUsers.getInstance().userOnline(user.getUid() ,  serviceHandler.getChannelHandlerContext(), user);//添加到在线用户列表

        //返回好友列表
        responseContacts(user , serviceHandler);

        //说个Hello~
        sayHelloRecipeTest(serviceHandler);
    }


    /**
     * 返回好友列表
     * @param user
     * @param serviceHandler
     */
    public void responseContacts(User user , ServiceHandler serviceHandler){
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

        serviceHandler.sendCodec(resp);
    }

    private void sayHelloRecipeTest(ServiceHandler serviceHandler){
        HelloRecipeMsg helloMsg = new HelloRecipeMsg();
        helloMsg.setHello("Hello ! 我是一个有重传机制的消息 我一定要保证送达哦~~~~~");

        helloMsg.setCallback((codec , user)->{
            System.out.println("尝试多次 还是没有响应  算了吧");
        });

        serviceHandler.sendCodec(helloMsg);
    }

}//end class
