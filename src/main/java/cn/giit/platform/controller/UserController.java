package cn.giit.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.common.Page;
import cn.giit.platform.entity.User;
import cn.giit.platform.service.BaseService;
import cn.giit.platform.service.UserService;

@RestController
public class UserController extends BaseController<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected BaseService<User> getService() {
        return userService;
    }

    @GetMapping("/api/users")
    public JsonResult findUsers(User user, Page page) {
        return findItems(user, page);
    }

    @GetMapping("/api/users/{id}")
    public JsonResult findUserByPrimaryKey(@PathVariable Integer id) {
        return findItemByPrimaryKey(id);
    }

    @PostMapping("/api/sign-in")
    public JsonResult signIn(User user) {
        return userService.signIn(user);
    }

    @GetMapping("/api/sign-out")
    public JsonResult signOut() {
        return userService.signOut();
    }

    @PostMapping("/api/sign-up")
    public JsonResult signUp(User user) {
        return userService.signUp(user);
    }

    @PutMapping("/api/users/{id}")
    public JsonResult updateUser(@PathVariable Integer id, User user) {
        user.setId(id);
        return updateItemByPrimaryKey(user);
    }

    @PutMapping("/api/users/{id}/header")
    public JsonResult updateUserHeader(@PathVariable Integer id, User user) {
        user.setId(id);
        return updateItemByPrimaryKey(user);
    }
}
