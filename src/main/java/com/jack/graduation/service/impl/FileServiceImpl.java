package com.jack.graduation.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.graduation.bean.FileInfo;
import com.jack.graduation.common.Constants;
import com.jack.graduation.config.HdfsConfig;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.mapper.FileMapper;
import com.jack.graduation.service.FileService;
import com.jack.graduation.service.SparkFileService;
import com.jack.graduation.utils.HdfsUtil;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.service.impl
 * @Author: jack
 * @CreateTime: 2023-01-05  18:48
 * @Description: TODO
 * @Version: jdk1.8
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileInfo> implements FileService {


    @Autowired
    private HdfsUtil hdfsUtil;
    @Autowired
    private HdfsConfig hdfsConfig;
    @Autowired
    private SparkFileService sparkFileService;

    /**
     * @param file     前端传过来的文件
     * @param fileName 文件名
     * @return
     */
    @Override
    public boolean uploadHdfs(MultipartFile file, String fileName) {
        boolean res = false;
        try {
            hdfsUtil.createFile(hdfsConfig.getHdfsPath() + fileName, file, fileName);
            res = hdfsUtil.existFile(hdfsConfig.getHdfsPath() + fileName);
            if (res) {
                return true;
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "hdfs io error!");
        }
        return res;
    }

    @Override
    public boolean removeHdfsFile(FileInfo fileInfo) {
        boolean res = false;
        String filename = fileInfo.getUuid() + StrUtil.DOT + fileInfo.getFileType();

        try {
            //未清洗文件路径IsEtl==0
            if (fileInfo.getIsEtl() == 0) {
                res = hdfsUtil.deleteFile(hdfsConfig.getHdfsPath() + filename);
            } else {
//                res = hdfsUtil.deleteFile(hdfsConfig.getHdfsCleanPath() + filename);
                res = sparkFileService.deletePartition(fileInfo);
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        return res;
    }

    @Override
    public byte[] downloadHdfsFile(String newFileName, Integer isEtl, String etlTime) {

        FileSystem fs = null;
        FSDataInputStream fis = null;
        byte[] resBytes;
        //hdfs文件父级
        String hdfsPath = hdfsConfig.getHdfsCleanPath() + "dt=" + etlTime + "/filename=" + FileUtil.getPrefix(newFileName) + "/";
        String hdfsFileName = hdfsUtil.getSingleFileName(hdfsPath);
        try {
            //文件名
            fs = hdfsUtil.getFileSystem();
            if (isEtl == 0) {
                //创建输入流
                fis = fs.open(new Path(hdfsConfig.getHdfsPath() + newFileName));
                resBytes = IOUtils.readFullyToByteArray(fis);
            } else {
                //创建输入流
//                fis = fs.open(new Path(hdfsConfig.getHdfsCleanPath() + newFileName));
                fis = fs.open(new Path(hdfsPath + hdfsFileName));
                //返回byte数组
                resBytes = IOUtils.readFullyToByteArray(fis);
            }
        } catch (Exception e) {
//            throw new ServiceException(Constants.CODE_500, "hdfs文件下载失败！");
            throw new ServiceException(Constants.CODE_500, e.getMessage());
            //e.printStackTrace();
        } finally {
            IOUtils.closeStream(fis);
            if (fs != null) {
                try {
                    //关流
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return resBytes;
    }

    @Override
    public HashSet<String> removeHdfsFileBatch(List<FileInfo> fileInfoList) {
        boolean res = false;
        HashSet<String> resSet = new HashSet<>();
        for (FileInfo fileInfo : fileInfoList) {
            String filename = fileInfo.getUuid() + StrUtil.DOT + fileInfo.getFileType();
            try {
                //未清洗文件路径IsEtl==0
                if (fileInfo.getIsEtl() == 0) {
                    res = hdfsUtil.deleteFile(hdfsConfig.getHdfsPath() + filename);
                    if (!res) {
                        resSet.add(fileInfo.getFileName() + "删除失败！");
                    }
                } else {
//                    res = hdfsUtil.deleteFile(hdfsConfig.getHdfsCleanPath() + filename);
                    res = sparkFileService.deletePartition(fileInfo);
                    if (!res) {
                        resSet.add(fileInfo.getFileName() + "删除失败！");
                    }
                }
            } catch (Exception e) {
                throw new ServiceException(Constants.CODE_500, resSet.toString());
            }
        }
        return resSet;
    }

}
