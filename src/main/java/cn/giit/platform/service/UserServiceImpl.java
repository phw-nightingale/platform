package cn.giit.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.giit.platform.common.AppConst;
import cn.giit.platform.common.AppContext;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.UserMapper;
import cn.giit.platform.entity.User;
import cn.giit.platform.util.BaseUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserMapper userMapper;

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RestTemplate restTemplate) {
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public BaseMapper<User> getMapper() {
        return userMapper;
    }

    @Override
    public JsonResult selectUserByToken(String token) {
        User exa = new User();
        exa.setToken(token);
        List<User> userList = selectItems(exa);
        if (BaseUtils.isNullOrEmpty(userList)) {
            return JsonResult.error();
        }
        return JsonResult.success("request ok", userList.get(0));
    }

    @Override
    public JsonResult signIn(User user) {
        // 使用微信登录，此方法不再需要实现
        return JsonResult.success("login success");
    }

    @Override
    public JsonResult signOut() {
        HttpServletRequest request = AppContext.getRequest();
        request.removeAttribute(AppConst.KEY_CURRENT_USER);
        return JsonResult.success("logout success");
    }

    @Override
    public JsonResult signUp(User user) {
        // 此方法不再需要实现
        return JsonResult.success("sign up success");
    }

    @Override
    public JsonResult wxLogin(String jsCode) {
        HttpHeaders headers = new HttpHeaders();
        HttpMethod GET = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = new StringBuilder().append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=").append(AppConst.APPID)
                .append("&secret=").append(AppConst.APPSEC)
                .append("&js_code=").append(jsCode)
                .append("&grant_type=authorization_code")
                .toString();
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.exchange(url, GET, entity, String.class);
        return JsonResult.success("request ok", response.getBody());
    }
}
