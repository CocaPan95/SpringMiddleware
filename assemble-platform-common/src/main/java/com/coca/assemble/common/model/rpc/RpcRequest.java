package com.coca.assemble.common.model.rpc;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class RpcRequest<T> implements Serializable {

    private String id;

    private T enity;

    public RpcRequest(T enity) {
        this.enity = enity;
    }

    public RpcRequest(String id, T enity) {
        this.id = id;
        this.enity = enity;
    }

    public RpcRequest(String id) {
        this.id = id;
    }
}

