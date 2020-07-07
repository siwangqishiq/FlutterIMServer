package xyz.panyi.imserver.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.StringUtil;
import xyz.panyi.imserver.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class OnlineUsers {

    public static class UserChannelWrap{
        public UserChannelWrap(User user, ChannelHandlerContext ctx) {
            this.user = user;
            this.ctx = ctx;
        }

        public User user;
        public ChannelHandlerContext ctx;
    }//end inner class

    private static volatile OnlineUsers instance;

    public static OnlineUsers getInstance(){
        if(instance == null){
            synchronized (OnlineUsers.class){
                if(instance == null){
                    instance = new OnlineUsers();
                }
            }
        }
        return instance;
    }

    private Map<Long , UserChannelWrap> onlineUsersMap;


    private OnlineUsers(){
        onlineUsersMap = new ConcurrentHashMap<Long , UserChannelWrap>();
    }

    /**
     *
     * @param uid
     * @param ctx
     * @param user
     */
    public void userOnline(long uid, ChannelHandlerContext ctx , User user){
        UserChannelWrap oldWrap = onlineUsersMap.get(uid);
        if(oldWrap != null){
            oldWrap.ctx.close();
        }

        UserChannelWrap wrap = new UserChannelWrap(user , ctx);
        onlineUsersMap.put(uid , wrap);
    }

    /**
     *
     * @param uid
     */
    public void userOffline(long uid){
        onlineUsersMap.remove(uid);
    }

}
