package cn.giit.platform.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.giit.platform.common.AppConst;
import cn.giit.platform.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author phw
 * @date Created in 04-08-2018
 * @description
 */
@Slf4j
@Component
public class BaseUtils {

    /**
     * 判断一个对象是否为空
     *
     * @param object 要判断的对象
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String str = (String) object;
            return str.equals("") || str.length() == 0;
        }
        if (object instanceof List<?>) {
            List<?> list = (List<?>) object;
            return list.size() == 0;
        }
        if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;
            return map.size() == 0;
        }
        return false;
    }

    /**
     * 判断多个对象是否为空，任意一个为空即返回true
     *
     * @param objects 对象数组
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Object... objects) {
        for (Object o : objects) {
            if (isNullOrEmpty(o)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 将对象以及父类的属性转化为Map键值对
     *
     * @param item 对象
     * @return map集合
     */
    public static Map<String, Object> object2Map(Object item) {
        if (isNullOrEmpty(item)) {
            return null;
        }
        try {
            Map<String, Object> map = new HashMap<>();
            Field[] fields = item.getClass().getDeclaredFields();
            Field[] superFields = item.getClass().getSuperclass().getDeclaredFields();
            getFieldsAndValues(item, map, fields);
            getFieldsAndValues(item, map, superFields);
            return map;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断一个字符串是否为数字
     *
     * @param str str
     * @return true or false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 替换字符串
     *
     * @param str str
     * @return 替换后的字符串
     */
    public static String replaceInteger(String str) {
        return str.replaceAll("^[0-9]*$", "{id}");
    }

    /**
     * 下划线转驼峰
     *
     * @param line         字符串
     * @param isFirstUpper 首字符是否大写
     * @return 转化后的字符串
     */
    public static String replaceUnderline(String line, Boolean isFirstUpper) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(isFirstUpper && matcher.start() == 0 ? Character.toUpperCase(word.charAt(0)) : Character.toLowerCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     *
     * @param method
     * @param url
     * @return
     */
    public static String httpRequest(HttpMethod method, String url) {
        RestTemplate rest = new RestTemplate();

        return null;
    }

    /**
     * 获取当前用户的request对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        User currentUser = (User) request.getAttribute(AppConst.KEY_CURRENT_USER);
        if (BaseUtils.isNullOrEmpty(currentUser)) {
            throw new RuntimeException("Error:Cannot get current user");
        }
        return currentUser;
    }

    /**
     *
     * 方法用途: 对所有传入参数按照字段名的Unicode码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap   要排序的Map对象
     * @param urlEncode   是否需要URLENCODE
     * @param keyToLower    是否需要将Key转换为全小写
     *            true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower)
    {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try
        {
            List<Map.Entry<String, String>> infoIds = new ArrayList<>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, Comparator.comparing(o -> (o.getKey())));
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds)
            {
                if (StringUtils.isEmpty(item.getKey()))
                {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode)
                    {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower)
                    {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else
                    {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (!buff.isEmpty())
            {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e)
        {
            return null;
        }
        return buff;
    }

    private static void getFieldsAndValues(Object item, Map<String, Object> map, Field[] fields) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Field field : fields) {
            String fieldName = field.getName();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            String methodName = "get" + fieldName;
            Object value = item.getClass().getMethod(methodName).invoke(item);
            if (value != null) {
                map.put(camel2Underline(field.getName()), value);
            }
        }
    }

}
