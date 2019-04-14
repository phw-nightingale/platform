package cn.giit.platform.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import cn.giit.platform.common.AppConst;
import cn.giit.platform.common.Page;
import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.entity.BaseEntity;
import cn.giit.platform.entity.User;
import cn.giit.platform.util.BaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    public abstract BaseMapper<T> getMapper();

    @Override
    public T insert(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return null;
        }
        User user = getCurrentUser();
        if (!BaseUtils.isNullOrEmpty(user)) {
            item.setCreateUserId(user.getId());
            item.setCreateUser(user.getNickname());
        }
        item.setCreateTime(new Date(System.currentTimeMillis()));
        if (getMapper().insertSelective(item) == 0) {
            return null;
        }
        return getMapper().selectByPrimaryKey(item.getId());
    }

    @Override
    public List<T> insert(List<T> items) {
        if (BaseUtils.isNullOrEmpty(items)) {
            return null;
        }
        User user = getCurrentUser();
        List<T> results = new ArrayList<>();
        for (T item : items) {
            if (!BaseUtils.isNullOrEmpty(user)) {
                item.setCreateUserId(user.getId());
                item.setCreateUser(user.getNickname());
            }
            item.setCreateTime(new Date(System.currentTimeMillis()));
            if (getMapper().insertSelective(item) == 0) {
                System.out.println("Insert item " + item.toString() + " failed...");
                continue;
            }
            results.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return results;
    }

    @Override
    public T deleteByPrimaryKey(Integer id) {
        if (BaseUtils.isNullOrEmpty(id)) {
            return null;
        }
        T item = getMapper().selectByPrimaryKey(id);
        if (BaseUtils.isNullOrEmpty(item)) {
            return null;
        }
        User user = getCurrentUser();
        if (!BaseUtils.isNullOrEmpty(user)) {
            item.setDeleteUserId(user.getId());
            item.setDeleteUser(user.getNickname());
        }
        item.setDeleteTime(new Date(System.currentTimeMillis()));
        if (getMapper().deleteByPrimaryKey(id) == 0) {
            return null;
        }
        return item;
    }

    @Override
    public List<T> deleteByPrimaryKey(List<Integer> ids) {
        if (BaseUtils.isNullOrEmpty(ids)) {
            return null;
        }
        User user = getCurrentUser();
        List<T> results = new ArrayList<>();
        for (Integer id : ids) {
            T item = getMapper().selectByPrimaryKey(id);
            if (!BaseUtils.isNullOrEmpty(user)) {
                item.setDeleteUserId(user.getId());
                item.setDeleteUser(user.getNickname());
            }
            item.setDeleteTime(new Date(System.currentTimeMillis()));
            if (BaseUtils.isNullOrEmpty(item)) {
                System.out.println("Item of id " + id + " is didn't exist...");
                continue;
            }
            results.add(item);
            getMapper().deleteByPrimaryKey(id);
        }
        return results;
    }

    @Override
    public List<T> deleteByConditions(T example) {
        Map<String, Object> conditions = BaseUtils.object2Map(example);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> results = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(results)) {
            return null;
        }
        User user = getCurrentUser();
        for (T item : results) {
            if (!BaseUtils.isNullOrEmpty(user)) {
                item.setDeleteUserId(user.getId());
                item.setDeleteUser(user.getNickname());
            }
            item.setDeleteTime(new Date(System.currentTimeMillis()));
        }
        if (getMapper().deleteByConditions(conditions) == 0) {
            return null;
        }
        return results;
    }

    @Override
    public T updateByPrimaryKey(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return null;
        }
        if (BaseUtils.isNullOrEmpty(item.getId())) {
            return null;
        }
        User user = getCurrentUser();
        if (!BaseUtils.isNullOrEmpty(user)) {
            item.setUpdateUserId(user.getId());
            item.setUpdateUser(user.getNickname());
        }
        item.setUpdateTime(new Date(System.currentTimeMillis()));
        if (getMapper().updateByPrimaryKeySelective(item) == 0) {
            return null;
        }
        return getMapper().selectByPrimaryKey(item.getId());
    }

    @Override
    public List<T> updateByPrimaryKey(List<T> items) {
        if (BaseUtils.isNullOrEmpty(items)) {
            return null;
        }
        User user = getCurrentUser();
        List<T> results = new ArrayList<>();
        for (T item : items) {
            if (BaseUtils.isNullOrEmpty(item.getId())) {
                System.out.println("Can't get id of item in list...");
                continue;
            }
            if (!BaseUtils.isNullOrEmpty(user)) {
                item.setUpdateUserId(user.getId());
                item.setUpdateUser(user.getNickname());
            }
            item.setUpdateTime(new Date(System.currentTimeMillis()));
            if (getMapper().updateByPrimaryKeySelective(item) == 0) {
                System.out.println("Update item of id " + item.getId() + " failed...");
                continue;
            }
            results.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return results;
    }

    @Override
    public T upgradeItemByPrimaryKey(T item) {
        User user = getCurrentUser();
        if (!BaseUtils.isNullOrEmpty(user)) {
            item.setUpdateUser(user.getNickname());
            item.setUpdateUserId(user.getId());
        }
        item.setUpdateTime(new Date(System.currentTimeMillis()));
        Integer result = getMapper().updateByPrimaryKeySelective(item);
        if (result == 0) {
            return null;
        }
        return getMapper().selectByPrimaryKey(item.getId());
    }

    @Override
    public List<T> updateByConditions(T values, T example) {
        Map<String, Object> valueMap = BaseUtils.object2Map(values);
        Map<String, Object> conditions = BaseUtils.object2Map(example);
        if (BaseUtils.isNullOrEmpty(valueMap) || BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> oldList = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(oldList)) {
            return null;
        }
        User user = getCurrentUser();
        for (T item : oldList) {
            if (!BaseUtils.isNullOrEmpty(user)) {
                item.setUpdateUserId(user.getId());
                item.setUpdateUser(user.getNickname());
            }
            item.setUpdateTime(new Date(System.currentTimeMillis()));
        }
        if (getMapper().updateByConditions(valueMap, conditions) == 0) {
            return null;
        }
        List<T> newList = new ArrayList<>();
        for (T item : oldList) {
            newList.add(getMapper().selectByPrimaryKey(item.getId()));
        }
        return newList;
    }

    @Override
    public T load(T item) {
        Map<String, Object> conditions = BaseUtils.object2Map(item);
        if (BaseUtils.isNullOrEmpty(conditions)) {
            return null;
        }
        List<T> items = getMapper().selectByConditions(conditions);
        if (BaseUtils.isNullOrEmpty(items) && items.size() != 1) {
            return null;
        }
        return items.get(0);
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        if (BaseUtils.isNullOrEmpty(id)) {
            return null;
        }
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public T selectItem(String key, String val) {
        if (BaseUtils.isNullOrEmpty(key, val)) {
            return null;
        }
        List<T> items = selectItems(key, val);
        if (BaseUtils.isNullOrEmpty(items)) {
            return null;
        }
        if (items.size() > 1) {
            log.warn("item's size has more than 1...");
            return null;
        }
        return items.get(0);
    }

    @Override
    public List<T> selectItems() {
        return getMapper().selectByConditions(new HashMap<>());
    }

    @Override
    public List<T> selectItems(T example) {
        Map<String, Object> map = null;
        if (BaseUtils.isNullOrEmpty(example)) {
            map = new HashMap<>();
        } else {
            map = BaseUtils.object2Map(example);
            if (BaseUtils.isNullOrEmpty(map)) {
                map = new HashMap<>();
            }
        }
        return getMapper().selectByConditions(map);
    }

    @Override
    public PageInfo<T> selectItems(T example, Page page) {
        int pageNum = page.getPage();
        int pageSize = page.getLimit();
        String orderBy = page.getOrderBy();
        Map<String, Object> conditionMap = BaseUtils.object2Map(example);
        if (BaseUtils.isNullOrEmpty(conditionMap)) {
            conditionMap = new HashMap<>();
        }
        if (pageNum != 0 && pageSize != 0) {
            log.info("pageNum:" + pageNum + ", pageSize: " + pageSize);
            PageHelper.startPage(pageNum, pageSize);
        }
        if (!BaseUtils.isNullOrEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<T> items = getMapper().selectByConditions(conditionMap);
        PageInfo<T> pageInfo = new PageInfo<>(items);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    @Override
    public PageInfo<T> selectItems(String key, String val, Page page) {
        int pageNum = page.getPage();
        int pageSize = page.getLimit();
        String orderBy = page.getOrderBy();
        Map<String, Object> map = new HashMap<>();
        map.put(key, val);
        if (!(pageNum == 0 || pageSize == 0)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (!BaseUtils.isNullOrEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<T> items = getMapper().selectByConditions(map);
        PageInfo<T> pageInfo = new PageInfo<>(items);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    @Override
    public List<T> selectItems(String key, String val) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, val);
        return getMapper().selectByConditions(map);
    }

    @Override
    public PageInfo<T> selectItemsLike(T example, Page page) {
        int pageNum = page.getPage();
        int pageSize = page.getLimit();
        String orderBy = page.getOrderBy();
        Map<String, Object> conditionMap = BaseUtils.object2Map(example);
        if (BaseUtils.isNullOrEmpty(conditionMap)) {
            conditionMap = new HashMap<>();
        }
        if (!BaseUtils.isNullOrEmpty(pageNum, pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        if (BaseUtils.isNullOrEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<T> items = getMapper().selectLike(conditionMap);
        PageInfo<T> pageInfo = new PageInfo<>(items);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return pageInfo;
    }

    @Override
    public String fileUpload(String uploadPath, MultipartFile file) {
        if (BaseUtils.isNullOrEmpty(file)) {
            return null;
        }
        String originalName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf("."));
        try {
            File uploadFile = new File(uploadPath, fileName);
            if (uploadFile.exists()) {
                fileUpload(uploadPath, file);
                return null;
            }
            if (!uploadFile.getParentFile().exists()) {
                if (uploadFile.getParentFile().mkdirs()) {
                    return null;
                }
            }
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadPath + File.separator + fileName;
    }

    @Override
    public String[] filesUpload(String uploadPath, List<MultipartFile> files) {
        return new String[0];
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String filePath) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (User) request.getAttribute(AppConst.KEY_CURRENT_USER);
    }
}
