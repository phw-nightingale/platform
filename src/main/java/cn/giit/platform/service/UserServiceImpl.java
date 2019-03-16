package cn.giit.platform.service;

import cn.giit.platform.common.AppConst;
import cn.giit.platform.common.AppContext;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.dao.BaseMapper;
import cn.giit.platform.dao.UserMapper;
import cn.giit.platform.entity.User;
import cn.giit.platform.util.BaseUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserMapper userMapper;

    private final RestTemplate restTemplate;

    private final Gson gson;

    @Value("${wx.app.appid}")
    private String APPID;

    @Value("${wx.app.appsecret}")
    private String APPSECRET;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RestTemplate restTemplate, Gson gson) {
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;
        this.gson = gson;
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
        //使用微信的session_key登录
        User curr;
        if (user.getWxToken() != null) {
            String sessionKey = user.getWxToken();
            curr = selectItem(AppConst.KEY_WXTOKEN, sessionKey);
            if (BaseUtils.isNullOrEmpty(curr)) {
                return JsonResult.error(401, "token invalid");
            } else {
                return JsonResult.success("login ok", curr);
            }
        } else {
            //使用常规方式登录
            String phone = user.getPhone();
            String pass = user.getPassword();
            String jsCode = user.getStr1();
            if (BaseUtils.isNullOrEmpty(phone, pass)) {
                return JsonResult.error("用户名或密码不全");
            }
            if (BaseUtils.isNullOrEmpty(jsCode)) {
                return JsonResult.error("参数不全");
            }
            curr = selectItem(AppConst.KEY_PHONE, phone);
            if (BaseUtils.isNullOrEmpty(curr)) {
                return JsonResult.error("手机号不正确");
            }
            if (!curr.getPassword().equals(pass)) {
                return JsonResult.error("密码不正确");
            }

            //登录微信服务器
            HttpHeaders headers = new HttpHeaders();
            HttpMethod GET = HttpMethod.GET;
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = new StringBuilder().append("https://api.weixin.qq.com/sns/jscode2session")
                    .append("?appid=").append(APPID)
                    .append("&secret=").append(APPSECRET)
                    .append("&js_code=").append(jsCode)
                    .append("&grant_type=authorization_code")
                    .toString();
            ResponseEntity<String> response = restTemplate.exchange(url, GET, entity, String.class);
            JsonObject jsObj = new JsonParser().parse(response.getBody()).getAsJsonObject();
            String sessionKey = jsObj.get(AppConst.KEY_SESSION_KEY).getAsString();
            if (StringUtils.isEmpty(sessionKey)) {
                return JsonResult.error("微信登录失败");
            }
            //登录成功
            curr.setWxToken(sessionKey);
            userMapper.updateByPrimaryKey(curr);
        }
        return JsonResult.success("login ok", curr);
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
        /*
        HttpHeaders headers = new HttpHeaders();
        HttpMethod GET = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = new StringBuilder().append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=").append(APPID)
                .append("&secret=").append(APPSECRET)
                .append("&js_code=").append(jsCode)
                .append("&grant_type=authorization_code")
                .toString();
        ResponseEntity<String> response = restTemplate.exchange(url, GET, entity, String.class);
        return JsonResult.success("login ok", response.getBody());
        */
        return null;
    }
}
