package com.jack.graduation.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.bean
 * @Author: jack
 * @CreateTime: 2023-01-04  18:46
 * @Description: TODO
 * @Version: jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String password;
    private String token;
}
