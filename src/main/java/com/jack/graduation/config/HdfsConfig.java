package com.jack.graduation.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.config
 * @Author: jack
 * @CreateTime: 2023-01-03  01:38
 * @Description: TODO:hdfs配置类
 * @Version: jdk1.8
 */
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HdfsConfig {
    // hdfs nameNode连接URL
    @Value("${nameNode.url}")
    private String nameNodeUrl;

    // 操作用户
    @Value("${hdfs.userName}")
    private String hdfsUserName;

    // 操作存储节点路径
    @Value("${hdfs.dataNode}/")
    private String pdfDataNode;

    //hdfs存储路径
    @Value("${nameNode.hdfsPath}")
    private String hdfsPath;

    //hdfs清洗存储路径
    @Value("${nameNode.hdfsCleanPath}")
    private String hdfsCleanPath;

}
