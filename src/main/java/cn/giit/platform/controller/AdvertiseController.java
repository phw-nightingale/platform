package cn.giit.platform.controller;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.Advertise;
import cn.giit.platform.service.AdvertiseService;
import cn.giit.platform.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdvertiseController extends BaseController<Advertise> {

    private final AdvertiseService advertiseService;

    @Autowired
    public AdvertiseController(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    @Override
    protected BaseService<Advertise> getService() {
        return advertiseService;
    }

    @PostMapping("/api/advertises")
    public JsonResult addAdvertise(Advertise advertise) {
        return addItem(advertise);
    }

    @PutMapping("/api/advertises/{id}")
    public JsonResult updateAdvertise(@PathVariable Integer id, Advertise advertise) {
        advertise.setId(id);
        return updateItemByPrimaryKey(advertise);
    }

    @DeleteMapping("/api/advertises/{id}")
    public JsonResult closeAdvertise(@PathVariable Integer id) {
        return deleteItemByPrimaryKey(id);
    }

    @GetMapping("/api/advertises/{key}/{val}")
    public JsonResult findAdvertise(@PathVariable String key, @PathVariable String val, Page page) {
        return findItems(key, val, page);
    }

    @GetMapping("/api/advertises")
    public JsonResult findAdvertise(Advertise advertise, Page page) {
        return findItems(advertise, page);
    }
}
