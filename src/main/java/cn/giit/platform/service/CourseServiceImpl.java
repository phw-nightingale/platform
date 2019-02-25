package cn.giit.platform.service;

import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.CourseMapper;
import cn.giit.platform.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceImpl<Course> implements CourseService {

    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public BaseMapper<Course> getMapper() {
        return courseMapper;
    }
}
