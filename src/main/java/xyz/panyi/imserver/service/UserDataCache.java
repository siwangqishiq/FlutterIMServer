package xyz.panyi.imserver.service;

import io.netty.util.internal.StringUtil;
import xyz.panyi.imserver.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class UserDataCache {

    private static volatile UserDataCache instance;

    public static UserDataCache getInstance(){
        if(instance == null){
            synchronized (UserDataCache.class){
                if(instance == null){
                    instance = new UserDataCache();
                }
            }
        }
        return instance;
    }

    private Map<String , User> accoutsMap;
    private Map<Long, User> uidMap;

    private UserDataCache(){
        accoutsMap = new ConcurrentHashMap<String , User>();
        uidMap = new ConcurrentHashMap<Long , User>();
    }

    public User getUserByAccount(String account){
        if(StringUtil.isNullOrEmpty(account))
            return null;

        return accoutsMap.get(account);
    }

    public void addUser(User user){
        accoutsMap.put(user.getAccount() , user);
        uidMap.put(user.getUid() , user);
    }
}
