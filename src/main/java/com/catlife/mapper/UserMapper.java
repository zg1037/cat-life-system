package com.catlife.mapper;

import com.catlife.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectByUsername(String username);

}