package xyz.panyi.imserver.service;

public interface IAuthService {
    boolean auth(String account , String pwd);

    boolean authUid(long uid , String pwd);
}
