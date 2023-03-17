package com.coca.assemble.minio.service;

import cn.hutool.core.io.IoUtil;
import com.coca.assemble.common.model.rpc.RpcRequest;
import com.coca.assemble.common.model.rpc.RpcResponse;
import com.coca.assemble.minio.model.OssProcessDTO;
import com.coca.assemble.minio.rpcapi.RpcOssOperateRpcApi;
import com.jvm123.minio.service.MinioFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

@Slf4j
public class MinioRpcOssService implements RpcOssOperateRpcApi {

    @Autowired
    MinioFileService fileStoreService;


    /**
     * 创建的操作处理控制
     * @param rpcRequest:内部存储对应的BucketName
     * @return
     */
    @Override
    public RpcResponse create(RpcRequest<String> rpcRequest) {
        return fileStoreService.createBucket(rpcRequest.getEnity())? RpcResponse.success(): RpcResponse.failure("创建oss bucket失败！");
    }

    /**
     * 上传服务文件处理
     * @param rpcRequest filePath和bucketName
     * @return
     */
    @Override
    public RpcResponse upload(RpcRequest<OssProcessDTO> rpcRequest) {
        try(InputStream inputStream =
                    IoUtil.toStream(new File(rpcRequest.getEnity().getFilePath()));) {
            fileStoreService.save(rpcRequest.getEnity().getBucketName(), inputStream, rpcRequest.getEnity().getFileName());
        }catch (Exception e) {
            log.error("upload the file is error",e);
            return RpcResponse.failure("upload the file is error");
        }
        return RpcResponse.success();
    }

    /**
     * 删除文件地下
     * @param rpcRequest bucketName 和 fileName
     * @return
     */
    @Override
    public RpcResponse remove(RpcRequest<OssProcessDTO> rpcRequest) {
        return fileStoreService.delete(rpcRequest.getEnity().getBucketName(),
                rpcRequest.getEnity().getFileName())?
                RpcResponse.success(): RpcResponse.failure("删除oss bucket文件失败！");
    }

    /**
     * 下载文件处理控制
     * @param rpcRequest
     * @return
     */
    @Override
    public RpcResponse download(RpcRequest<OssProcessDTO> rpcRequest) {
        try(
                InputStream inputStream = fileStoreService.
                        getStream(rpcRequest.getEnity().getBucketName(),
                                rpcRequest.getEnity().getFileName());
                OutputStream outputStream = new FileOutputStream(rpcRequest.getEnity().getFilePath());
        ) {
            fileStoreService.writeTo(rpcRequest.getEnity().getBucketName(), rpcRequest.getEnity().getFileName(), outputStream);
        } catch (IOException e) {
            log.error("download file is failure!",e);
            return RpcResponse.failure("下载oss bucket文件失败！");
        }
        return RpcResponse.success(rpcRequest.getEnity().getFilePath());
    }
}
