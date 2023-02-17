package com.jack.graduation.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.graduation.bean.AlbumGnereYear;
import com.jack.graduation.bean.User;
import com.jack.graduation.common.Constants;
import com.jack.graduation.common.Result;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.service.AlbumGnereYearService;
import com.jack.graduation.service.SparkFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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
            @RequestParam(defaultValue = "Rock") String genre,
            @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2021") String endYear

    ) {
        try {
            //TODO:mybatis plus查询流派等于 gnere的所有信息
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

    //TODO:所有流派信息，条形图
    @RequestMapping("/albumAllGnereYear")
    public Result albumAllGnereYear(
            @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2009") String endYear

    ) {

        try {

            List<AlbumGnereYear> albumGnereYearList = albumGnereYearService.getTop10(startYear, endYear, filename);
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

    //柱形图
    @RequestMapping("/albumAllGnereYearTop5")
    public Result albumAllGnereYearTop5(
            @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
            @RequestParam(defaultValue = "2000") String startYear,
            @RequestParam(defaultValue = "2009") String endYear

    ) {
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

    //TODO:饼图
    @RequestMapping("/getPieData")
    public Result pieGraph(
            @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
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

    @RequestMapping("/getAnalysisAllDataPage")
    //TODO:分页数据
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
                          @RequestParam(defaultValue = "2023-02-16") String dt,
                          @RequestParam(defaultValue = "") String id
    ) {
        List<AlbumGnereYear> list = albumGnereYearService.list();
        IPage<AlbumGnereYear> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AlbumGnereYear> wrapper = new QueryWrapper<>();

        //TODO:根据username搜索
        if (!"".equals(filename)) {
            wrapper.eq("filename", filename);
        }
        //TODO:根据id搜索
        if (!"".equals(id)) {
            wrapper.and(wra -> wra.eq("id", Integer.valueOf(id)));
        }
        //TODO:根据dt搜索
        if (!"".equals(dt)) {
            wrapper.and(wra -> wra.eq("dt", dt));
        }
        // wrapper.like("username", username);
        // wrapper.and(a -> a.like("username", uname));
        //TODO:正序排
        wrapper.orderByAsc("id");
        IPage<AlbumGnereYear> iPage = albumGnereYearService.page(page, wrapper);
        return Result.success(iPage);
    }

    //TODO:导出结果集方法
    @GetMapping("/exportRes")
    public void export(HttpServletResponse response,
                       @RequestParam(defaultValue = "513716dc162f42d9814d459ab1a99729") String filename,
                       @RequestParam(defaultValue = "2023-02-16") String dt
    ) throws Exception {
        //TODO: 从数据库查询出所有的数据
        QueryWrapper<AlbumGnereYear> wrapper = new QueryWrapper<>();
        wrapper.eq("filename", filename);
        wrapper.and(wra -> wra.eq("dt", dt));
        List<AlbumGnereYear> list = albumGnereYearService.list(wrapper);
        //TODO: 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("genre", "流派");
        writer.addHeaderAlias("albumYearOfPub", "专辑发售年份");
        writer.addHeaderAlias("numOfTracks", "专辑销售歌曲数");
        writer.addHeaderAlias("numOfSales", "专辑销售歌曲量");
        writer.addHeaderAlias("dt", "文件分析日期");
        writer.addHeaderAlias("filename", "文件名");
        //TODO: 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
        //TODO: 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("结果集信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}


