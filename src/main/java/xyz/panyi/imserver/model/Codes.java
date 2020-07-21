package xyz.panyi.imserver.model;

public interface Codes {
    int CODE_TEST_REQ = 10001;//测试请求
    int CODE_TEST_RESP = 10002; //测试响应

    int CODE_LOGIN_REQ = 1003;//登录请求
    int CODE_LOGIN_RESP = 1004;//登录返回

    int CODE_LOGINOUT_REQ = 1005;//注销登录 req
    int CODE_LOGINOUT_RESP = 1006;//注销登录 resp

    int CODE_AUTO_LOGIN_REQ = 1007;//自动登录req
    int CODE_AUTO_LOGIN_RESP = 1008;//自动登录resp

    int CODE_FRIEND_LIST_RESP = 3002;//好友列表resp

    int CODE_RECIPE_HELLO = 4003;//测试recipe消息

    int CODE_RESET_RESP = 8001;//退出重新登录

    int CODE_RECIPT_ACK= 9001; //远端返回已接收的ack
}
