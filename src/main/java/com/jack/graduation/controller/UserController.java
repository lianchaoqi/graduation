package com.jack.graduation.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.graduation.bean.User;
import com.jack.graduation.bean.UserDTO;
import com.jack.graduation.common.Constants;
import com.jack.graduation.common.Result;
import com.jack.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.controller
 * @Author: jack
 * @CreateTime: 2023-01-01  19:07
 * @Description: TODO
 * @Version: jdk1.8
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/byId")
    public User getPage() {
        return userService.getById(1);
    }

    /**
     * @param userDTO 登录bean
     * @return 是否登录成功
     * 用户登录方法
     */
    @PostMapping("/userLogin")
    public Result userLogin(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        //判断是用户名和密码否合法
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "输入参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        /**
         * 登录成功，返回结果集
         * 结果集里面封装了用户信息和token（验证）信息
         */
        return Result.success(dto);
    }

    @RequestMapping("/all")
    public List<User> getAll() {

        return userService.list();
    }

    @DeleteMapping("/deleteUser/{id}")
    public Result delUser(@PathVariable("id") Integer id) {

        return Result.success(userService.removeById(id));
    }

    //批量删除数据
    @PostMapping("/delUserBatch")
    public Result delUserBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }


    @PostMapping(value = "/saveUser")
    public Result saveUser(@RequestBody User user) {
        long ts = System.currentTimeMillis();
        user.setUpdateTime(new Date(ts));
        return Result.success(userService.saveOrUpdate(user));
    }

    /**
     * @param user 注册用户方法
     * @return 是否注册成功
     */
    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        //判断是用户名和密码否合法
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "输入参数错误");
        }
        boolean res = userService.register(user);
        return Result.success(res);
    }


    //分页数据
    @RequestMapping("/page")
    public Result getPage(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String username,
                          @RequestParam(defaultValue = "") String id,
                          @RequestParam(defaultValue = "") String eamil,
                          @RequestParam(defaultValue = "") String address
    ) {
        List<User> list = userService.list();
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        //根据username搜索
        if (!"".equals(username)) {
            wrapper.eq("username", username);
        }
        //根据id搜索
        if (!"".equals(id)) {

            wrapper.and(wra -> wra.eq("id", Integer.valueOf(id)));
        }
//        wrapper.like("username", username);
//        wrapper.and(a -> a.like("username", uname));
        //倒序排
        wrapper.orderByDesc("id");
        IPage<User> iPage = userService.page(page, wrapper);

        return Result.success(iPage);
    }

    //导出用户方法
    @GetMapping("/exportUser")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
        //ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "修改时间");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
}
