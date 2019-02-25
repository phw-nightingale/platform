package cn.giit.platform.controller;

import cn.giit.platform.entity.Category;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController extends BaseController<Category> {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected BaseService<Category> getService() {
        return categoryService;
    }
}
