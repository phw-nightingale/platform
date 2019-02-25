package cn.giit.platform.controller;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.Topic;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController extends BaseController<Topic> {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    protected BaseService<Topic> getService() {
        return topicService;
    }

    @PostMapping("/api/topics")
    public JsonResult addTopic(Topic topic) {
        return addItem(topic);
    }

    @PutMapping("/api/topics/{id}")
    public JsonResult updateTopic(@PathVariable Integer id, Topic topic) {
        topic.setId(id);
        return updateItemByPrimaryKey(topic);
    }

    @DeleteMapping("/api/topics/{id}")
    public JsonResult closeTopic(@PathVariable Integer id) {
        return deleteItemByPrimaryKey(id);
    }

    @GetMapping("/api/topics/{key}/{val}")
    public JsonResult findTopics(@PathVariable String key, @PathVariable String val, Page page) {
        return findItems(key, val, page);
    }

    @GetMapping("/api/topics")
    public JsonResult findTopics(Topic topic, Page page) {
        return findItems(topic, page);
    }
}
