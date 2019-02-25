package cn.giit.platform.dao;

import cn.giit.platform.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}