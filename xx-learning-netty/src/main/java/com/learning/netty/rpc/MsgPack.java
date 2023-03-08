package com.learning.netty.rpc;


import com.learning.netty.rpc.prototype.RpcContent;
import com.learning.netty.rpc.prototype.RpcHeader;

import java.io.Serializable;

public class MsgPack implements Serializable {

    private RpcHeader header;
    private RpcContent content;

    public MsgPack(RpcHeader header, RpcContent content) {
        this.header = header;
        this.content = content;
    }

    public RpcHeader getHeader() {
        return header;
    }

    public RpcContent getContent() {
        return content;
    }
}
