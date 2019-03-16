package cn.giit.platform.service;

import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.BaseEntity;
import cn.giit.platform.entity.User;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    /**
     * 插入一条记录
     *
     * @param item 记录
     * @return 记录
     */
    T insert(T item);

    /**
     * 插入记录集合
     *
     * @param items 记录集合
     * @return 记录集合
     */
    List<T> insert(List<T> items);

    /**
     * 根据主键删除一条记录
     *
     * @param id 主键
     * @return 被删除的记录
     */
    T deleteByPrimaryKey(Integer id);

    /**
     * 根据主键集合删除记录
     *
     * @param ids 主键集合
     * @return 被删除的记录集合
     */
    List<T> deleteByPrimaryKey(List<Integer> ids);

    /**
     * 根据删除记录
     *
     * @param example 条件
     * @return 被删除的记录集合
     */
    List<T> deleteByConditions(T example);

    /**
     * 根据主键以及非空属性更新
     *
     * @param item 条件
     * @return 更新后的记录
     */
    T updateByPrimaryKey(T item);

    /**
     * 根据主键以及非空属性更新列表
     * 不适合大批量操作
     *
     * @param items 条件列表
     * @return 更新后的记录列表
     */
    List<T> updateByPrimaryKey(List<T> items);

    /**
     * 条件更新
     *
     * @param values  目标值
     * @param example 条件
     * @return 更新后的记录列表
     */
    List<T> updateByConditions(T values, T example);

    /**
     * 根据主键更新非空属性
     *
     * @param item 要更新的对象
     * @return 更新后的记录
     */
    T upgradeItemByPrimaryKey(T item);

    /**
     * 根据条件加载一个对象
     * 若符合该条件的对象有一个或多个
     * 那么就返回null
     *
     * @param example 条件
     * @return 单个记录
     */
    T load(T example);

    /**
     * 根据ID加载对象
     *
     * @param id 唯一标识符
     * @return 对象
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 根据条件选择单个结果
     *
     * @param key 关键字
     * @param val 值
     * @return 结果
     */
    T selectItem(String key, String val);

    /**
     * 查询所有记录
     *
     * @return 对相集合
     */
    List<T> selectItems();

    /**
     * 通用查询
     *
     * @param example 条件
     * @return 对象集合
     */
    List<T> selectItems(T example);

    /**
     * 通用查询，包含分页，条件和排序
     *
     * @param example 条件
     * @param page    分页信息
     * @return 分页数据
     */
    PageInfo<T> selectItems(T example, Page page);

    /**
     * 单条件查询
     *
     * @param key  关键字
     * @param val  值
     * @param page 分页信息
     * @return 分页数据
     */
    PageInfo<T> selectItems(String key, String val, Page page);

    /**
     * 单条件查询
     *
     * @param key 关键字
     * @param val 值
     * @return 结果
     */
    List<T> selectItems(String key, String val);

    /**
     * 通用模糊查询，包含分页，条件和排序
     *
     * @param example 条件
     * @param page    分页信息
     * @return 分页数据
     */
    PageInfo<T> selectItemsLike(T example, Page page);

    /**
     * 单文件上传
     *
     * @param uploadPath 上传路径
     * @param file       上传文件
     * @return 文件url
     */
    String fileUpload(String uploadPath, MultipartFile file);

    /**
     * 多文件上传
     *
     * @param uploadPath 上传路径
     * @param files      文件列表
     * @return 文件url列表
     */
    String[] filesUpload(String uploadPath, List<MultipartFile> files);

    /**
     * 根据文件地址下载文件
     *
     * @param filePath 文件地址
     * @return 文件二进制流
     */
    ResponseEntity<byte[]> fileDownload(String filePath);

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    User getCurrentUser();

}
