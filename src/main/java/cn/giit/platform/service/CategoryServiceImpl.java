package cn.giit.platform.service;

import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.CategoryMapper;
import cn.giit.platform.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public BaseMapper<Category> getMapper() {
        return categoryMapper;
    }
}
