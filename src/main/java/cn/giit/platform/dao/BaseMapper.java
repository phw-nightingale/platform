package cn.giit.platform.dao;

import org.apache.ibatis.annotations.Param;
import cn.giit.platform.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author phw
 * @date 5-3-2018
 * @description Mapper基类，定义基本的数据库操作方法
 * @param <T>
 */
public interface BaseMapper<T extends BaseEntity> {

    Integer insert(T item);

    Integer insertSelective(T item);

    T selectByPrimaryKey(Integer id);

    T load(@Param("unique") String unique, @Param("value") String value);

    Integer deleteByPrimaryKey(Integer id);

    Integer deleteByConditions(@Param("map") Map<String, Object> conditions);

    Integer updateByPrimaryKeySelective(T item);

    Integer updateByPrimaryKey(T item);

    Integer updateByConditions(@Param("entry") Map<String, Object> entry, @Param("conditions") Map<String, Object> conditions);

    List<T> selectByConditions(@Param("map") Map<String, Object> conditions);

    Integer countByConditions(@Param("map") Map<String, Object> conditions);

    List<T> selectLike(@Param("map") Map<String, Object> conditions);

}
