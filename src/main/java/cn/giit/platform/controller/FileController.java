package cn.giit.platform.controller;

import cn.giit.platform.common.AppContext;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.entity.File;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController extends BaseController<File> {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    protected BaseService<File> getService() {
        return fileService;
    }

    @PostMapping("/api/files")
    public JsonResult fileUpload(MultipartFile file) {
        return fileUpload(AppContext.getRequest(), file);
    }

    @PostMapping("/api/files/header")
    public JsonResult headerUpload(MultipartFile header) {
        return fileUpload(AppContext.getRequest(), header);
    }
}
