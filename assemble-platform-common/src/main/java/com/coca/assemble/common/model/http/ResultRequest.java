package com.coca.assemble.common.model.http;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ResultRequest<T> implements Serializable {

    private String id;

    private T enity;

    public ResultRequest(T enity) {
        this.enity = enity;
    }

    public ResultRequest(String id, T enity) {
        this.id = id;
        this.enity = enity;
    }

    public ResultRequest(String id) {
        this.id = id;
    }
}

