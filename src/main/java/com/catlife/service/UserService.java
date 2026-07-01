package com.catlife.service;

import com.catlife.common.result.Result;
import com.catlife.dto.LoginDTO;

public interface UserService {

    Result login(LoginDTO loginDTO);
}