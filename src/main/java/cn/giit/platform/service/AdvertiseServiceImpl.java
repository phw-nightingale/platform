package cn.giit.platform.service;

import cn.giit.platform.dao.AdvertiseMapper;
import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.entity.Advertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvertiseServiceImpl extends BaseServiceImpl<Advertise> implements AdvertiseService {

    private final AdvertiseMapper advertiseMapper;

    @Autowired
    public AdvertiseServiceImpl(AdvertiseMapper advertiseMapper) {
        this.advertiseMapper = advertiseMapper;
    }

    @Override
    public BaseMapper<Advertise> getMapper() {
        return advertiseMapper;
    }

}
