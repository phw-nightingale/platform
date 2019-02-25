package cn.giit.platform.service;

import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.ReviewMapper;
import cn.giit.platform.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl extends BaseServiceImpl<Review> implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public BaseMapper<Review> getMapper() {
        return reviewMapper;
    }
}
