package xyz.panyi.imserver.service;

import xyz.panyi.imserver.model.User;

/**
 * 登录验证
 */
public class AuthService implements IAuthService{

    @Override
    public boolean auth(String account, String pwd) {
        User user = UserDataCache.getInstance().getUserByAccount(account);
        if(user == null){
            return false;
        }

        return user.getPwd().equals(pwd);
    }

    @Override
    public boolean authUid(long uid, String pwd) {
        User user = UserDataCache.getInstance().getUserByUid(uid);
        if(user == null){
            return false;
        }

        return user.getPwd().equals(pwd);
    }
}
