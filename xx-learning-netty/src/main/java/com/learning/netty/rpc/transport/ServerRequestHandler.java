package com.learning.netty.rpc.transport;

import com.learning.netty.rpc.MsgPack;
import com.learning.netty.rpc.ServiceFactory;
import com.learning.netty.rpc.prototype.RpcContent;
import com.learning.netty.rpc.prototype.RpcHeader;
import com.learning.netty.rpc.prototype.RpcResponse;
import com.learning.netty.rpc.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LogManager.getLogger(ServerRequestHandler.class);

    // provider:
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // ByteBuf buf = (ByteBuf) msg;
        // ByteBuf sendBuf = buf.copy();
        //
        // logger.info("server channel read, readable bytes : {}", buf.readableBytes());
        //
        // while (buf.readableBytes() >= 246){
        //
        //     // 1. 读取header数据
        //     byte[] bytes = new byte[246];
        //     // buf.readBytes(bytes);
        //     buf.getBytes(buf.readableBytes(), bytes); // get不移动指针，readableBytes保持不变。
        //     RpcHeader header = ByteUtil.toObject(bytes);
        //
        //     logger.info("server channel read, rpc header : {}", header);
        //
        //     // 2. 读取content数据
        //     if(buf.readableBytes() >= header.getDataLen()){
        //         // 先移动指针，去掉header部门
        //         buf.readBytes(246);
        //
        //         // 读取content
        //         byte[] data = new byte[(int)header.getDataLen()];
        //         buf.readBytes(data);
        //         RpcContent content = ByteUtil.toObject(data);
        //         logger.info("server channel read, rpc interface content: {}", content);
        //     }
        // }
        //
        //
        // ChannelFuture channelFuture = ctx.writeAndFlush(sendBuf);
        // channelFuture.sync();

        // 1. 当前主线程处理
        // 2. 自己开线程处理
        // 3. Netty线程处理
        // String ioThreadName = Thread.currentThread().getName();
        // ctx.executor().execute(() -> { // 当前IO线程
        ctx.executor().parent().next().execute(() -> {
            byte[] requestContentBytes = null;
            byte[] requestHeaderBytes = null;

            MsgPack msgPack = (MsgPack) msg;
            RpcHeader header = msgPack.getHeader();
            RpcContent content = msgPack.getContent();
            try {

                // String threadName = String.format("io-thread:%s, busines-thread: %s", ioThreadName, Thread.currentThread().getName());
                // RpcContent requestContent = new RpcContent(threadName);

                Object result = ServiceFactory.invoke(content);
                RpcContent requestContent = new RpcContent(new RpcResponse(result));
                requestContentBytes = ByteUtil.toByteArray(requestContent);

                RpcHeader requestHeader = RpcHeader.createHeader(RpcHeader.FLAG_SERVER, header.getRequestId(), requestContentBytes);
                requestHeaderBytes = ByteUtil.toByteArray(requestHeader);

                logger.info("server channel read, interfaceName:{}, methodName:{}, args:{}, requestHeaderBytes: {}, requestContentBytes: {}",
                        content.getName(), content.getMethodName(), content.getArgs(), requestHeaderBytes.length, requestContentBytes.length);

            } catch (Exception e) {
                RpcContent requestContent = new RpcContent(new RpcResponse(e));
                requestContentBytes = ByteUtil.toByteArray(requestContent);
                if (requestHeaderBytes == null) {
                    RpcHeader requestHeader = RpcHeader.createHeader(RpcHeader.FLAG_SERVER, header.getRequestId(), requestContentBytes);
                    requestHeaderBytes = ByteUtil.toByteArray(requestHeader);
                }
            }

            ByteBuf byteBuf = ByteUtil.createDirectBuffer(requestHeaderBytes.length + requestContentBytes.length, requestHeaderBytes, requestContentBytes);
            ctx.writeAndFlush(byteBuf);
        });
    }
}