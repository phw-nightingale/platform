package cn.giit.platform.controller;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.Category;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/categories")
    public JsonResult getCategories(Category item, Page page) {
        return findItems(item, page);
    }

    @GetMapping("/api/categories/{key}/{val}")
    public JsonResult getCategories(@PathVariable String key, @PathVariable String val, Page page) {
        return findItems(key, val, page);
    }


}
