package xyz.panyi.imserver.service;

import io.netty.util.internal.StringUtil;
import xyz.panyi.imserver.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class OnlineUsers {

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

    private Map<Long , User> onlineUsersMap;


    private OnlineUsers(){
        onlineUsersMap = new ConcurrentHashMap<Long , User>();
    }

}
