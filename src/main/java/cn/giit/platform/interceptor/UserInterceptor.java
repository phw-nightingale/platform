package cn.giit.platform.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import cn.giit.platform.common.AppConst;
import cn.giit.platform.common.JsonResult;
import cn.giit.platform.entity.User;
import cn.giit.platform.service.UserService;
import cn.giit.platform.util.BaseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Autowired
    public UserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader(AppConst.KEY_AUTHORIZATION);
        if (BaseUtils.isNullOrEmpty(token)) {
            response.sendRedirect("/401");
            return false;
        }
        try {
            // 判断token是否过期
            JsonResult res = userService.selectUserByToken(token);
            if (res.getCode() != AppConst.RESULT_SUCCESS) {
                response.sendRedirect("/403");
            }
            User user = (User) res.getData();
            long expires = Long.valueOf(user.getStr1());
            if (new Date().getTime() > expires) {
                response.sendRedirect("/401");
                return false;
            }


        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect("/401");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

}
