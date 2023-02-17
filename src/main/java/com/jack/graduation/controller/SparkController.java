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
 * @Description: a
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
        //TODO:获取当前时间
        Date date = new Date(System.currentTimeMillis());
        //TODO:时间格式转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String etlTime = dateFormat.format(date);
        //TODO:调用spark程序清洗
        boolean res = false;
        try {
            res = sparkFileService.fileEtl(hdfsConfig.getHdfsPath(), hdfsConfig.getHdfsCleanPath(), one, etlTime);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        if (res) {
            one.setEtlTime(date);
            one.setIsEtl(1);
            //TODO:mysql数据修改
            boolean resMysql = fileService.updateById(one);
            if (resMysql) {
                return Result.success("清洗成功！");
            } else {
                return Result.error(Constants.CODE_500, "系统异常！");
            }
        }
        return Result.error(Constants.CODE_500, "spark异常，清洗系统错误！");
    }

    //TODO:宽表生成
//    @GetMapping("/dwsAlbum/{dt}/{filename}")
    @GetMapping("/dwsAlbum/{id}")
    public Result dwsAlbum(@PathVariable("id") int id) {
        //TODO:获取文件分区信息
        FileInfo fileInfo = fileService.getById(id);
        String filename = fileInfo.getUuid();
        //TODO:时间格式转换
        SimpleDateFormat dateFormatEtlTime = new SimpleDateFormat("yyyy-MM-dd");
        String dt = dateFormatEtlTime.format(fileInfo.getEtlTime());
        System.out.println("con:"+dt);
        System.out.println("con:"+filename);
        try {
            //TODO:hive宽表生成
            boolean dwdToDwsRes = sparkFileService.albumDwdToDws(dt, filename);
            if (dwdToDwsRes) {
                //TODO:获取当前时间
                Date date = new Date(System.currentTimeMillis());
                //TODO:时间格式转换
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createTime = dateFormat.format(date);
                //TODO:宽表album写入mysql
                Result res = sparkFileService.dwsAlbum(dt, filename, createTime);
                if (res != null) {
                    return Result.success("true");
                }
            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        return Result.error(Constants.CODE_500,"宽表生成错误");
    }

    //TODO:流派年份维度
    @GetMapping("/adsAlbumGnereYear/{id}")
    public Result adsAlbumGnereYear(
            @PathVariable("id") String id) {
        FileInfo fileInfo = null;
        Result result = null;
        //TODO:获取当前时间
        Date analysisDate = new Date(System.currentTimeMillis());
        try {
            //TODO:获取文件分区信息
            fileInfo = fileService.getById(id);
            String filename = fileInfo.getUuid();
            //TODO:时间格式转换
            SimpleDateFormat dateFormatEtlTime = new SimpleDateFormat("yyyy-MM-dd");
            String dt = dateFormatEtlTime.format(fileInfo.getEtlTime());
            result = sparkFileService.adsAlbumGnereYear(dt, filename);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        if ("200".equals(result.getCode())) {
            fileInfo.setAnalysisTime(analysisDate);
            fileInfo.setIsAnalysis(1);
            if (fileService.updateById(fileInfo)) {
                return Result.success("分析成功！");
            } else {
                return Result.error(Constants.CODE_500, "分析失败！系统错误");
            }
        }
        return Result.error(Constants.CODE_500, "spark异常，分析系统错误！");
    }
    //TODO:国家流派年份维度
    @GetMapping("/adsAlbumCountryGnereYear/{dt}/{filename}")
    public Result adsAlbumCountryGnereYear(
            @PathVariable("dt") String dt,
            @PathVariable("filename") String filename) {
        return sparkFileService.adsAlbumCountryGnereYear(dt, filename);
    }
}
