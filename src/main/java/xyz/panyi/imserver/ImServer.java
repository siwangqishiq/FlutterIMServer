package xyz.panyi.imserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xyz.panyi.imserver.handler.CodecMsg;
import xyz.panyi.imserver.handler.ObjToMsgEncoder;
import xyz.panyi.imserver.handler.ServiceHandler;
import xyz.panyi.imserver.model.User;
import xyz.panyi.imserver.service.UserDataCache;

public class ImServer {
    private int port;

    public ImServer(int port){
        this.port = port;

        addTestData();
    }

    private void addTestData(){
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
    }

    public void runLoop(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();//boss线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();// worker线程组

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup , workerGroup)//
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG , 128) //每个线程的最大连接数
                    .childOption(ChannelOption.SO_KEEPALIVE , true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            final ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("msg_codec" , new CodecMsg());
                            pipeline.addLast("obj_codec" , new ObjToMsgEncoder());

                            pipeline.addLast("service_handler" , new ServiceHandler());
                        }
                    });

            System.out.println("启动服务 port : " + port + " ...");
            //绑定端口  启动服务
            ChannelFuture cf = bootstrap.bind(port).sync();
            //结束
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}//end class
