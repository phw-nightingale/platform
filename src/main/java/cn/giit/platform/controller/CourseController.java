package cn.giit.platform.controller;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.Course;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.CourseService;
import cn.giit.platform.util.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController extends BaseController<Course> {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected BaseService<Course> getService() {
        return courseService;
    }

    @PostMapping("/api/courses")
    public JsonResult addCreate(Course course) {
        return addItem(course);
    }

    @PutMapping("/api/courses/{id}/content")
    public JsonResult updateContent(@PathVariable Integer id, Course course) {
        course.setId(id);
        if (BaseUtils.isNullOrEmpty(course.getContent())) {
            return updateItemByPrimaryKey(course);
        } else {
            return JsonResult.error("内容不能为空");
        }
    }

    @DeleteMapping("/api/courses/{id}")
    public JsonResult closeCourse(@PathVariable Integer id) {
        return deleteItemByPrimaryKey(id);
    }

    @GetMapping("/api/courses/{id}")
    public JsonResult courseDetail(@PathVariable Integer id) {
        return findItemByPrimaryKey(id);
    }

    @GetMapping("/api/courses/{key}/{val}")
    public JsonResult findCourses(@PathVariable String key, @PathVariable String val, Page page) {
        return findItems(key, val, page);
    }

    @GetMapping("/api/courses")
    public JsonResult findCourses(Course course, Page page) {
        return findItems(course, page);
    }
}
