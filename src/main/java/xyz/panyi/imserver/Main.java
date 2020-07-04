package xyz.panyi.imserver;

import xyz.panyi.imserver.model.PersonResp;

public class Main {

    /**
     *  启动服务
     * @param args
     */
    public static void main(String[] args){
        ImServer server = new ImServer(Config.IM_SERVER_PORT);
        server.runLoop();
    }

}//end class
