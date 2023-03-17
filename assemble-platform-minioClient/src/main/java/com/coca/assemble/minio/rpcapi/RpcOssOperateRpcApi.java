package com.coca.assemble.minio.rpcapi;

import com.coca.assemble.common.model.rpc.RpcRequest;
import com.coca.assemble.common.model.rpc.RpcResponse;
import com.coca.assemble.minio.model.OssProcessDTO;

public interface RpcOssOperateRpcApi {
    /**
     * bucket
     * @param rpcRequest:内部存储对应的BucketName
     * @return
     */
    RpcResponse create(RpcRequest<String> rpcRequest);


    /**
     * 存储文件
     * @param rpcRequest filePath和bucketName
     * @return
     */
    RpcResponse upload(RpcRequest<OssProcessDTO> rpcRequest);

    /**
     * 删除文件
     * @param rpcRequest bucketName 和 fileName
     * @return
     */
    RpcResponse remove(RpcRequest<OssProcessDTO> rpcRequest);


    /**
     * 下载文件
     * @param rpcRequest bucketName 和 fileName
     * @return
     */
    RpcResponse download(RpcRequest<OssProcessDTO> rpcRequest);
}
