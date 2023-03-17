package com.coca.assemble.minio.controller;

import com.coca.assemble.common.model.http.ResultResponse;
import com.coca.assemble.minio.service.MinioHttpOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/oss")
@Api(value="MINO-OSS服务",tags = {"MINO服务组件"},description = "主要作为OSS服务组件的-MINIO服务")
public class MinioStorageController {
    @Autowired
    MinioHttpOssService fileStoreService;

    /**
     * bucket
     * @param bucketName
     * @return
     */
    @GetMapping("/create")
    @ApiOperation(value="创建Bucket服务",notes = "创建Bucket服务")
    public ResultResponse create(@RequestParam("bucketName") String bucketName){
        try {
            return fileStoreService.create(bucketName);
        }catch (Exception e){
            log.error("create bucket is failure!",e);
            return ResultResponse.failure("create bucket is failure!");
        }
    }


    /**
     * 存储文件
     * @param file
     * @param bucketName
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value="上传文件",notes = "上传文件到指定Bucket服务")
    @ApiImplicitParam(name = "file", value = "上传的文件", dataType = "java.io.File", required = true)
    public ResultResponse upload(@RequestParam("file") MultipartFile file, @RequestParam("bucketName") String bucketName){
        try {
            return fileStoreService.upload(file,bucketName);
        } catch (Exception e) {
            log.error("upload the file is error",e);
            return ResultResponse.failure("upload the file is error");
        }
    }

    /**
     * 删除文件
     * @param bucketName
     * @param bucketName
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation(value="删除文件",notes = "删除文件指定Bucket服务")
    public ResultResponse delete(@RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName){
        try {
            return fileStoreService.delete(bucketName,fileName);
        }catch (Exception e){
            log.error("delete bucket is failure!",e);
            return ResultResponse.failure("delete bucketis failure!");
        }
    }
    /**
     * 下载文件
     * @param bucketName
     * @param bucketName
     * @return
     */
    @GetMapping("/download")
    @ApiOperation(value="下载文件",notes = "下载文件指定Bucket服务")
    public void download(HttpServletResponse httpServletResponse, @RequestParam("bucketName") String bucketName, @RequestParam("fileName") String fileName){
        try {
            fileStoreService.download(httpServletResponse,bucketName,fileName);
        } catch (Exception e) {
            log.error("download file is failure!",e);
        }
    }
}
