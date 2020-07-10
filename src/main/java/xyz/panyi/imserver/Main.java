package xyz.panyi.imserver;

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
        user1.setSex(User.SEX_MALE);
        user1.setDisplayName("潘易");
        user1.setPwd("123456");
        user1.setAvator("https://p1.pstatp.com/large/39f900000550ee07d57b");
        user1.setDesc("错的不是我 是这个世界");
        UserDataCache.getInstance().addUser(user1);

        User user2 = new User();
        user2.setAccount("gongtengxinyi");
        user2.setUid(2);
        user2.setDisplayName("工藤新一");
        user2.setSex(User.SEX_MALE);
        user2.setPwd("123456");
        user2.setAvator("https://pbs.twimg.com/profile_images/1253753602637656064/R7gPnwQR_400x400.jpg");
        user2.setDesc("真相永远只有一个");
        UserDataCache.getInstance().addUser(user2);

        User user3 = new User();
        user3.setAccount("test");
        user3.setUid(3);
        user3.setDisplayName("毛利兰");
        user3.setPwd("123456");
        user3.setAvator("https://pbs.twimg.com/profile_images/888462069892136960/VG1pBK-d_400x400.jpg");
        user3.setSex(User.SEX_FEMALE);
        user3.setDesc("洗衣机~~~~~~~~~~~~");
        UserDataCache.getInstance().addUser(user3);

        User user4 = new User();
        user4.setAccount("test1");
        user4.setUid(4);
        user4.setDisplayName("木哈哈");
        user4.setSex(User.SEX_FEMALE);
        user4.setPwd("123456");
        user4.setAvator("http://pic1.win4000.com/wallpaper/2020-07-06/5f02e087d5cc0.jpg");
        UserDataCache.getInstance().addUser(user3);


        User user5 = new User();
        user5.setAccount("test2");
        user5.setUid(5);
        user5.setDisplayName("木哈哈2");
        user5.setSex(User.SEX_FEMALE);
        user5.setPwd("123456");
        user5.setAvator("https://konachan.net/sample/afaef3e794b6f135b81c227be7146f3b/Konachan.com%20-%20310476%20sample.jpg");
        UserDataCache.getInstance().addUser(user5);


    }

}//end class
