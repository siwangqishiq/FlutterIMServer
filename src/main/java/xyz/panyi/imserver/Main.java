package xyz.panyi.imserver;

import com.google.gson.Gson;
import xyz.panyi.imserver.model.PersonResp;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.UserDataCache;

public class Main {

    /**
     *  启动服务
     * @param args
     */
    public static void main(String[] args){
        addTestData();
        ImServer server = new ImServer(Config.IM_SERVER_PORT);
        server.runLoop();
    }


    private static void addTestData(){
        User user1 = new User();
        user1.setAccount("siwangqishiq");
        user1.setUid(1);
        user1.setDisplayName("潘易");
        user1.setPwd("1989391013");
        user1.setAvator("https://p1.pstatp.com/large/39f900000550ee07d57b");

        UserDataCache.getInstance().addUser(user1);

        User user2 = new User();
        user2.setAccount("gongtengxinyi");
        user2.setUid(2);
        user2.setDisplayName("工藤新一");
        user2.setPwd("891013");
        user2.setAvator("https://pbs.twimg.com/profile_images/1253753602637656064/R7gPnwQR_400x400.jpg");
        UserDataCache.getInstance().addUser(user2);

        User user3 = new User();
        user3.setAccount("test");
        user3.setUid(3);
        user3.setDisplayName("毛利兰兰");
        user3.setPwd("891013");
        user3.setAvator("https://pbs.twimg.com/profile_images/888462069892136960/VG1pBK-d_400x400.jpg");
        UserDataCache.getInstance().addUser(user3);


        Gson gson = new Gson();
        String json = gson.toJson(user1);
    }

}//end class
