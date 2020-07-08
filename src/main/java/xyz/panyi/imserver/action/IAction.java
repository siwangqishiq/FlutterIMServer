package xyz.panyi.imserver.action;

import io.netty.channel.ChannelHandlerContext;
import xyz.panyi.imserver.model.Msg;

public interface IAction {

    /**
     *
     * @param ctx
     * @param msg
     */
    void handle(ChannelHandlerContext ctx , Msg msg);
}
