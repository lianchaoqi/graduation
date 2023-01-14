package com.jack.graduation.controller;

import com.jack.graduation.config.HdfsConfig;
import com.jack.graduation.utils.HdfsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-03  02:47
 * @Description: TODO
 * @Version: jdk1.8
 */
@RestController
public class HdfsFileController {
    @Autowired
    private HdfsUtil hdfsUtil;
    @Autowired
    private HdfsConfig hdfsConfig;

    @RequestMapping("/test")
    public boolean get() throws Exception {
        boolean mkdir = hdfsUtil.mkdir(hdfsConfig.getPdfDataNode() + "jack123123");
        return mkdir;
    }

    @RequestMapping("/getFileList")
    public List<Map<String, String>> getFileList() throws Exception {
        List<Map<String, String>> maps = hdfsUtil.listFile(hdfsConfig.getPdfDataNode());
        return maps;
    }

    @RequestMapping("/upLoadFile")
    public boolean loadFile() throws Exception {
        hdfsUtil.uploadFile(
                "D:/idea2020/IdeaProjects/graduation/src/main/resources/application.properties"
                , hdfsConfig.getPdfDataNode());
        boolean res = hdfsUtil.existFile(hdfsConfig.getPdfDataNode() + "application.properties");
        return res;
    }

    @RequestMapping("/downloadFile")
    public void downloadFile() throws Exception {
        hdfsUtil.downloadFile("/graduation/application.properties", "D:/idea2020/IdeaProjects/graduation/src/main/resources/tt");

    }
}
