package com.jack.graduation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.graduation.bean.User;
import com.jack.graduation.bean.UserDTO;
import com.jack.graduation.common.Constants;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.mapper.UserMapper;
import com.jack.graduation.service.UserService;
import com.jack.graduation.utils.TokenUtil;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.service.impl
 * @Author: jack
 * @CreateTime: 2023-01-01  21:40
 * @Description: TODO
 * @Version: jdk1.8
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userDTO.getUsername());
        userQueryWrapper.eq("password", userDTO.getPassword());
        User one = null;

        // 处理异常情况
        try {
            one = getOne(userQueryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (one != null) {
            //true=>表示忽略大小写
            BeanUtil.copyProperties(one, userDTO, true);
            //返回一个token验证
            String token = TokenUtil.getToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User one = null;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (one != null) {
            throw new ServiceException(Constants.CODE_600, "用户名已存在");
        } else {
            return save(user);
        }
    }
}
