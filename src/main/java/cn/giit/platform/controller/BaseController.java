package cn.giit.platform.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import cn.giit.platform.common.AppConst;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.BaseEntity;
import cn.giit.platform.entity.User;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.util.BaseUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @author phw
 * @date 4-22-2018
 * @description Controller基类
 */
public abstract class BaseController<T extends BaseEntity> {

    protected abstract BaseService<T> getService();

    /**
     * 输出时间转换
     *
     * @param binder binder
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    protected JsonResult findItems(T item, Page page) {
        PageInfo<T> pageInfo = getService().selectItems(item, page);
        return JsonResult.success("Success", pageInfo);
    }

    protected JsonResult findItems(String key, String val, Page page) {
        PageInfo<T> pageInfo = getService().selectItems(key, val, page);
        return JsonResult.success("Success", pageInfo);
    }

    protected JsonResult findItemByPrimaryKey(Integer id) {
        T item = getService().selectByPrimaryKey(id);
        if (BaseUtils.isNullOrEmpty(item)) {
            JsonResult.error("No such record");
        }
        return JsonResult.success("Success", item);
    }

    /**
     * 根据主键更新非空的记录
     *
     * @param item 包含id的记录
     * @return 更新后的记录
     */
    protected JsonResult updateItemByPrimaryKey(T item) {
        item = getService().updateByPrimaryKey(item);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Upgrade error");
        }
        return JsonResult.success("Upgrade successful", item);
    }

    protected JsonResult upgradeItemByPrimaryKey(T item) {
        if (BaseUtils.isNullOrEmpty(item, item.getId())) {
            return JsonResult.error("Get params error");
        }
        item = getService().upgradeItemByPrimaryKey(item);
        return JsonResult.success("Upgrade successful", item);
    }


    protected JsonResult filesUpload(ServletRequest req) {
        MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) req;
        String[] filesPath = getService().filesUpload(req.getServletContext().getRealPath("/upload"), multiReq.getFiles("file"));
        if (BaseUtils.isNullOrEmpty((Object[]) filesPath)) {
            return JsonResult.error("Files upload error");
        }
        Map<String, Object> dataMap = new HashMap<>();
        for (int i = 0; i < filesPath.length; i++) {
            dataMap.put("file" + i, filesPath[i]);
        }
        return JsonResult.success("Success", dataMap);
    }

    protected JsonResult fileUpload(ServletRequest req, MultipartFile file) {
        if (BaseUtils.isNullOrEmpty(file)) {
            return JsonResult.error("File upload error");
        }
        String filePath = getService().fileUpload(req.getServletContext().getRealPath("/upload"), file);
        if (BaseUtils.isNullOrEmpty(filePath)) {
            return JsonResult.error("File upload error");
        }
        return JsonResult.success("File upload successful", filePath);
    }

    protected JsonResult addItem(T item) {
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Get item error");
        }
        item = getService().insert(item);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Save failed");
        }
        return JsonResult.success("Insert item successful", item);
    }


    protected JsonResult deleteItemByPrimaryKey(Integer id) {
        T item = getService().deleteByPrimaryKey(id);
        if (BaseUtils.isNullOrEmpty(item)) {
            return JsonResult.error("Didn't remove any item");
        }
        return JsonResult.success("Remove successful", item);
    }


    protected User getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (User) request.getAttribute(AppConst.KEY_CURRENT_USER);
    }

}
