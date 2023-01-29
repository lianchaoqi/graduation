package com.jack.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.graduation.bean.AlbumGnereYear;

import java.util.List;
import java.util.Map;

public interface AlbumGnereYearService extends IService<AlbumGnereYear> {
    List<String> getAllGenre();

    List<String> getAllFilename();

    Integer delRes(String filename);

    List<AlbumGnereYear> getTop10(String startYear, String endYear, String filename);

    List<AlbumGnereYear> getTop5(String startYear, String endYear, String filename);

    List<Map<String, Long>> getPieData(String startYear, String endYear, String filename);
}
