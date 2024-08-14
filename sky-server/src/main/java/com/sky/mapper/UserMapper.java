package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    void insert(User user);

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);
}
