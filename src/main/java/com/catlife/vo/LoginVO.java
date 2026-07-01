package com.catlife.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginVO {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private LocalDateTime createTime;

    /**
     * JWT Token，登录成功后返回
     */
    private String token;

}
