package cn.giit.platform.controller;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.Review;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.ReviewService;
import cn.giit.platform.util.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController extends BaseController<Review> {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    protected BaseService<Review> getService() {
        return reviewService;
    }

    /**
     * 留言文章/回复留言
     *
     * @param target 留言对象类型(文章/教程/话题)等,当值为review时,表示回复留言
     * @param id     留言对象id
     * @param review review对象
     * @return 结果
     */
    @PostMapping("/api/reviews/{target}/{targetId}")
    public JsonResult addReview(@PathVariable String target, @PathVariable Integer targetId, Review review) {
        review.setTarget(target);
        review.setTargetId(targetId);
        if (BaseUtils.isNullOrEmpty(review.getContent())) {
            return JsonResult.error("内容不能为空");
        }
        return addItem(review);
    }

    @DeleteMapping("/api/reviews/{id}")
    public JsonResult closeReview(@PathVariable Integer id) {
        return deleteItemByPrimaryKey(id);
    }

    @GetMapping("/api/reviews/{key}/{val}")
    public JsonResult findReviews(@PathVariable String key, @PathVariable String val, Page page) {
        return findItems(key, val, page);
    }

    @GetMapping("/api/reviews")
    public JsonResult findReviews(Review review, Page page) {
        return findItems(review, page);
    }
}
