package cn.giit.platform.service;

import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.FileMapper;
import cn.giit.platform.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileServiceImpl extends BaseServiceImpl<File> implements FileService {

    private final FileMapper fileMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public BaseMapper<File> getMapper() {
        return fileMapper;
    }
}
