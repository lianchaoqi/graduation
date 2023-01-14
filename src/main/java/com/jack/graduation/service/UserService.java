package com.jack.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.graduation.bean.User;
import com.jack.graduation.bean.UserDTO;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.service
 * @Author: jack
 * @CreateTime: 2022-12-31  21:36
 * @Description: TODO
 * @Version: jdk1.8
 */

@Service
public interface UserService extends IService<User> {
    UserDTO login(UserDTO userDTO);
    boolean register(User user);
}
