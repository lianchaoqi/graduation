package com.jack.graduation.controller;

import com.jack.graduation.common.Result;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.Random;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-17  15:57
 * @Description: TODO
 * @Version: jdk1.8
 */

@RequestMapping("/status")
@RestController
public class HomeController {
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    /**
     * cpu使用情况
     *
     * @return int 当前CPU使用
     */
    @GetMapping("/cpu")
    public Result cpuLoad() {
        double cpuLoad = osmxb.getSystemCpuLoad();
        int i = (int) (cpuLoad * 100);
        return Result.success(i);
    }

    /**
     * 内存使用情况
     *
     * @return int当前内存使用
     */
    @GetMapping("/memory")
    public Result memoryLoad() {
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        double value = freePhysicalMemorySize / totalvirtualMemory;
        int i = (int) ((1 - value) * 100);
        return Result.success(i);
    }

}
