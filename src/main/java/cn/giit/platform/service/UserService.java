package cn.giit.platform.service;

import cn.giit.platform.common.JsonResult;
import cn.giit.platform.entity.User;

public interface UserService extends BaseService<User> {

    JsonResult selectUserByToken(String token);

    JsonResult signIn(User user);

    JsonResult signOut();

    JsonResult signUp(User user);

    JsonResult wxLogin(String jsCode);
}
