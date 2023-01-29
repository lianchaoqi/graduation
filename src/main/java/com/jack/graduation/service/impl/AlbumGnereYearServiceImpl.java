package com.jack.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.graduation.bean.AlbumGnereYear;
import com.jack.graduation.mapper.AlbumGnereYearMapper;
import com.jack.graduation.service.AlbumGnereYearService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.service.impl
 * @Author: jack
 * @CreateTime: 2023-01-21  12:06
 * @Description: TODO
 * @Version: jdk1.8
 */
@Service
public class AlbumGnereYearServiceImpl extends ServiceImpl<AlbumGnereYearMapper, AlbumGnereYear> implements AlbumGnereYearService {
    @Resource
    private AlbumGnereYearMapper albumGnereYearMapper;

    @Override
    public List<String> getAllGenre() {
        return albumGnereYearMapper.getGenreStrings();
    }

    @Override
    public List<String> getAllFilename() {
        return albumGnereYearMapper.getFilenameStrings();
    }

    @Override
    public Integer delRes(String filename) {
        return albumGnereYearMapper.delRes(filename);
    }

    @Override
    public List<AlbumGnereYear> getTop10(String startYear, String endYear, String filename) {
        return albumGnereYearMapper.getTop10(startYear, endYear, filename);
    }

    @Override
    public List<AlbumGnereYear> getTop5(String startYear, String endYear, String filename) {
        return albumGnereYearMapper.getTop5(startYear, endYear, filename);
    }

    @Override
    public List<Map<String, Long>> getPieData(String startYear, String endYear, String filename) {
        return albumGnereYearMapper.getPieData(startYear, endYear, filename);
    }
}
