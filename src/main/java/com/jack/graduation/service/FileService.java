package com.jack.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.graduation.bean.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public interface FileService extends IService<FileInfo> {
    //上传hdfs方法
    boolean uploadHdfs(MultipartFile file, String fileName);

    boolean removeHdfsFile(FileInfo fileInfo);

    byte[] downloadHdfsFile(String fileUUID, Integer isEtl, String etlTime);

    HashSet<String> removeHdfsFileBatch(List<FileInfo> fileInfoList);
}
