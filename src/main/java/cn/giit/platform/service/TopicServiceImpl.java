package cn.giit.platform.service;

import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.TopicMapper;
import cn.giit.platform.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TopicServiceImpl extends BaseServiceImpl<Topic> implements TopicService {

    private final TopicMapper topicMapper;

    @Autowired
    public TopicServiceImpl(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    public BaseMapper<Topic> getMapper() {
        return topicMapper;
    }
}
