package com.jack.graduation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.graduation.bean.FileInfo;
import com.jack.graduation.common.Constants;
import com.jack.graduation.common.Result;
import com.jack.graduation.service.FileService;
import com.jack.graduation.service.SparkFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-05  17:27
 * @Description: TODO 文件上传接口
 * @Version: jdk1.8
 */

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadToHdfs")
    public Result uploadToHdfs(@RequestParam MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename(); //文件名
        String type = FileUtil.extName(originalFilename);//获取文件扩展名（文件类型后缀名），扩展名不带“.”
        if (!"csv".equals(type)) {
            //throw new ServiceException(Constants.CODE_400, "文件类型必须是csv逗号分隔文件！");
            return Result.error(Constants.CODE_400, "文件类型必须是csv逗号分隔文件！");
        }
        //文件大小
        long size = file.getSize();
        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        //新的文件名
        String newOriginalFilename = uuid + StrUtil.DOT + type;
        String md5 = SecureUtil.md5(file.getInputStream());
        //下载路径
        String url = "http://localhost:9090/file/" + newOriginalFilename;
        FileInfo fileInfo = new FileInfo(null, originalFilename, md5, uuid, type, size / 1024, url, null, null, null, null);
        //信息写进数据库
        fileService.save(fileInfo);
        //存储到hdfs
        boolean res = fileService.uploadHdfs(file, newOriginalFilename);
        if (res) {
            return Result.success("文件上传成功！");
        } else {
            return Result.error(Constants.CODE_500, "服务器错误！");
        }
    }

    /**
     * 清洗后的文件，从hdfs下载
     *
     * @param newFileName 文件唯一标识
     * @param isEtl       是否清洗标识
     * @param response    响应体
     * @throws IOException exception
     */
    @GetMapping("/{newFileName}/{isEtl}/{etlTime}")
    public void downloadFile(
            @PathVariable String newFileName,
            @PathVariable Integer isEtl,
            @PathVariable String etlTime,
            HttpServletResponse response) {

        ServletOutputStream os = null;

        // 设置输出流的格式
        try {
            os = response.getOutputStream();

            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(newFileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            byte[] resBytes = fileService.downloadHdfsFile(newFileName, isEtl, etlTime);
            // 读取文件的字节流
            os.write(resBytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @DeleteMapping("/deleteFile/{id}")
    public Result deleteFile(@PathVariable Integer id) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        FileInfo fileInfo = fileService.getOne(queryWrapper);
        if (fileService.removeHdfsFile(fileInfo) && fileService.removeById(id)) {
            return Result.success("文件删除成功");
        } else {
            return Result.error(Constants.CODE_500, "hdfs文件删除失败");
        }
    }

    //批量删除数据
    @PostMapping("/delFileBatch")
    public Result delUserBatch(@RequestBody List<Integer> ids) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<FileInfo> fileInfoList = fileService.list(queryWrapper);
        HashSet<String> resSet = fileService.removeHdfsFileBatch(fileInfoList);
        if (resSet.isEmpty() && fileService.removeByIds(ids)) {
            return Result.success("批量删除文件成功");
        } else {
            return Result.error(Constants.CODE_500, resSet.toString());
        }
    }

    //根据md5查找文件是否存在
    public FileInfo getFileByMd5(String md5) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_md5", md5);
        FileInfo fileInfo = fileService.getOne(queryWrapper);
        return fileInfo;
    }

    //分页数据
    @RequestMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String fileName,
                          @RequestParam(defaultValue = "") String id,
                          @RequestParam(defaultValue = "") String uuid
    ) {
        List<FileInfo> list = fileService.list();
        IPage<FileInfo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();

        //根据username搜索
        if (!"".equals(fileName)) {
            wrapper.eq("file_name", fileName);
        }
        //根据id搜索
        if (!"".equals(id)) {
            wrapper.and(wra -> wra.eq("id", Integer.valueOf(id)));
        }
        //根据uuid搜索
        if (!"".equals(uuid)) {
            wrapper.eq("uuid", uuid);
        }
        //倒序排
        wrapper.orderByDesc("id");
        IPage<FileInfo> iPage = fileService.page(page, wrapper);
        return Result.success(iPage);
    }
}
