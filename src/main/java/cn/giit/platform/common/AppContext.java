package cn.giit.platform.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.giit.platform.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义应用上下文
 * 持有全局Request
 *
 * @author phw
 * @date Created in 06-08-2018
 * @description
 */
public class AppContext {

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static User getCurrentUser() {
        HttpServletRequest request = getRequest();
        return (User) request.getAttribute(AppConst.KEY_CURRENT_USER);
    }

    /**
     * 获取当前用户的Request对象
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

}
