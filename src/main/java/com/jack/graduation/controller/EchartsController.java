package com.jack.graduation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.graduation.bean.AlbumGnereYear;
import com.jack.graduation.common.Constants;
import com.jack.graduation.common.Result;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.service.AlbumGnereYearService;
import com.jack.graduation.service.SparkFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-17  00:43
 * @Description: TODO 可视化
 * @Version: jdk1.8
 */
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private AlbumGnereYearService albumGnereYearService;
    public static final Logger logger = LoggerFactory.getLogger(EchartsController.class);
    @Autowired
    private SparkFileService sparkFileService;

    @RequestMapping("/albumGnereYear")
    public Result albumGnereYear(
            @RequestParam(defaultValue = "Indie") String genre,
            @RequestParam(defaultValue = "941cd0118a2d457182414507a4e95924") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2021") String endYear

    ) {
        try {
            //mybatis plus查询流派等于 gnere的所有信息
            QueryWrapper<AlbumGnereYear> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("genre", genre);
            queryWrapper.eq("filename", filename);
            queryWrapper.between("album_year_of_pub", startYear, endYear);
            List<AlbumGnereYear> albumGnereYearList = albumGnereYearService.list(queryWrapper);
            //遍历封装到统一结果集里面
//            for (AlbumGnereYear albumGnereYear : albumGnereYearList) {
//                resMap.put(albumGnereYear.getAlbumYearOfPub(), albumGnereYear.getNumOfSales());
//            }
            //TODO:对里面的年份排序
            Collections.sort(albumGnereYearList);
            //TODO:反转集合 实现倒序排序
            Collections.reverse(albumGnereYearList);
            return Result.success(albumGnereYearList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(Constants.CODE_500, e.getMessage());
        }
    }

    @GetMapping("/allGenre")
    public Result getAllGenre() {
        List<String> allGenre = null;
        try {
            allGenre = albumGnereYearService.getAllGenre();
        } catch (Exception e) {
            return Result.error(Constants.CODE_500, e.getMessage());
        }
        return Result.success(allGenre);
    }

    @GetMapping("/allFilename")
    public Result getAllFilename() {
        List<String> allFilename = null;
        try {
            allFilename = albumGnereYearService.getAllFilename();
        } catch (Exception e) {
            return Result.error(Constants.CODE_500, e.getMessage());
        }
        return Result.success(allFilename);
    }

    @DeleteMapping("/delRes/{filename}")
    //结果集删除
    public Result delRes(@PathVariable("filename") String filename) {
        //hive数据集删除
        try {

            String code = sparkFileService.delRes(filename).getCode();
            System.out.println(code + "code");
            if ("200".equals(code)) {
//                QueryWrapper<AlbumGnereYear> albumGnereYearQueryWrapper = new QueryWrapper<>();
//                albumGnereYearQueryWrapper.eq("filename", filename);
//                boolean mysqlRes = albumGnereYearService.remove(albumGnereYearQueryWrapper);
                Integer integer = albumGnereYearService.delRes(filename);
                System.out.println(integer + "mysqlRes");
                if (integer != 0) {
                    return Result.success("删除成功！");
                }

            }
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, e.getMessage());
        }
        return Result.error(Constants.CODE_500, "删除数据错误！");
    }

    //所有流派信息，条形图
    @RequestMapping("/albumAllGnereYear")
    public Result albumAllGnereYear(
            @RequestParam(defaultValue = "941cd0118a2d457182414507a4e95924") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2009") String endYear

    ) {

//        Map<String, ArrayList<Long>> resMap = new HashMap<>();
        try {

            List<AlbumGnereYear> albumGnereYearList = albumGnereYearService.getTop10(startYear, endYear, filename);
            //TODO:对里面的年份排序
            Collections.sort(albumGnereYearList);
            //TODO:反转集合 实现倒序排序
            Collections.reverse(albumGnereYearList);
            //封装到map里面
//            for (AlbumGnereYear albumGnereYear : albumGnereYearList) {
//                ArrayList<Long> list = resMap.getOrDefault(albumGnereYear.getGenre(), new ArrayList<>());
//                list.add(albumGnereYear.getNumOfSales());
//                resMap.put(albumGnereYear.getGenre(), list);
//            }
            return Result.success(albumGnereYearList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(Constants.CODE_500, e.getMessage());
        }
    }

    //柱形图
    @RequestMapping("/albumAllGnereYearTop5")
    public Result albumAllGnereYearTop5(
            @RequestParam(defaultValue = "941cd0118a2d457182414507a4e95924") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2009") String endYear

    ) {

//        Map<String, ArrayList<Long>> resMap = new HashMap<>();
        try {
            List<AlbumGnereYear> albumGnereYearList = albumGnereYearService.getTop5(startYear, endYear, filename);
            //TODO:对里面的年份排序
            Collections.sort(albumGnereYearList);
            //TODO:反转集合 实现倒序排序
            Collections.reverse(albumGnereYearList);

            return Result.success(albumGnereYearList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(Constants.CODE_500, e.getMessage());
        }
    }

    //饼图
    @RequestMapping("/getPieData")
    public Result pieGraph(
            @RequestParam(defaultValue = "941cd0118a2d457182414507a4e95924") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2021") String endYear

    ) {
        try {

            List<Map<String, Long>> pieData = albumGnereYearService.getPieData(startYear, endYear, filename);
            return Result.success(pieData);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(Constants.CODE_500, e.getMessage());
        }
    }

}


