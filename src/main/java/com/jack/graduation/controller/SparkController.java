package com.jack.graduation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.graduation.bean.FileInfo;
import com.jack.graduation.common.Constants;
import com.jack.graduation.common.Result;
import com.jack.graduation.config.HdfsConfig;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.service.FileService;
import com.jack.graduation.service.SparkFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-09  18:55
 * @Description: TODO
 * @Version: jdk1.8
 */
@RestController
@RequestMapping("/sparkCon")
public class SparkController {
    @Autowired
    private FileService fileService;
    @Autowired
    private SparkFileService sparkFileService;
    @Autowired
    private HdfsConfig hdfsConfig;

    @GetMapping("/etlFile/{id}")
    public Result etlFile(@PathVariable Integer id) {
        QueryWrapper<FileInfo> fileInfoQueryWrapper = new QueryWrapper<>();
        fileInfoQueryWrapper.eq("id", id);
        FileInfo one = fileService.getOne(fileInfoQueryWrapper);
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        //时间格式转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String etlTime = dateFormat.format(date);
        //调用spark 程序清洗
        boolean res = false;
        try {
            res = sparkFileService.fileEtl(hdfsConfig.getHdfsPath(), hdfsConfig.getHdfsCleanPath(), one, etlTime);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        if (res) {
            one.setEtlTime(date);
            one.setIsEtl(1);
            //mysql数据修改
            boolean resMysql = fileService.updateById(one);
            if (resMysql) {
                return Result.success("清洗成功！");
            } else {
                return Result.error(Constants.CODE_500, "系统异常！");
            }
        }
        return Result.error(Constants.CODE_500, "spark异常，清洗系统错误！");
    }

    @GetMapping("/dwsAlbum/{dt}/{filename}")
    public Result analysisFile(@PathVariable String dt, @PathVariable String filename) {
        boolean dwdToDwsRes = sparkFileService.albumDwdToDws(dt, filename);
        try {
            if (dwdToDwsRes) {
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                //时间格式转换
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createTime = dateFormat.format(date);
                //宽表album
                Result res = sparkFileService.dwsAlbum(dt, filename, createTime);
                if (res != null) {
                    return Result.success("true");
                }
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        return Result.success("false");
    }

    //流派年份维度
    @GetMapping("/adsAlbumGnereYear/{dt}/{filename}")
    public Result adsAlbumGnereYear(
            @PathVariable("dt") String dt,
            @PathVariable("filename") String filename) {
        return sparkFileService.adsAlbumGnereYear(dt, filename);
    }

    //国家流派年份维度
    @GetMapping("/adsAlbumCountryGnereYear/{dt}/{filename}")
    public Result adsAlbumCountryGnereYear(
            @PathVariable("dt") String dt,
            @PathVariable("filename") String filename) {
        return sparkFileService.adsAlbumCountryGnereYear(dt, filename);
    }
}
