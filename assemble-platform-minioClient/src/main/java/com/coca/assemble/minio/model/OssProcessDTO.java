package com.coca.assemble.minio.model;

import lombok.Data;

import java.io.Serializable;

@Data
@SuppressWarnings("serial")
public class OssProcessDTO implements Serializable {

    private String filePath;

    private String bucketName;

    private String fileName;
}
