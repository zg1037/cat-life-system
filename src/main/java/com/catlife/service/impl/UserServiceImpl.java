package com.catlife.service.impl;

import com.catlife.common.jwt.JwtUtil;
import com.catlife.common.result.Result;
import com.catlife.dto.LoginDTO;
import com.catlife.entity.User;
import com.catlife.mapper.UserMapper;
import com.catlife.service.UserService;
import com.catlife.vo.LoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result login(LoginDTO loginDTO) {

        // 1. 根据用户名查用户
        User user = userMapper.selectByUsername(loginDTO.getUsername());

        // 2. 用户不存在
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 3. 密码校验（先用明文，后面再升级加密）
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            return Result.error("密码错误");
        }

        // 4. 登录成功，生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = jwtUtil.generateToken(claims);

        // 5. 返回用户信息（不包含密码）和 token
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        loginVO.setToken(token);
        return Result.success(loginVO);
    }
}
