package com.thinker.framework.framework.support.filter;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.properties.ThinkerProperties;
import com.thinker.framework.framework.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Jsoup过滤 http请求，防止 Xss攻击
 *
 * @author MrBird
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest orgRequest;

    private boolean isIncludeRichText;

    XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText, ThinkerProperties thinkerProperties) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
        xssNames = thinkerProperties.getConfig().getXssNames();
    }

    List<String> xssNames = null;

    /**
     * 覆盖 getParameter方法，将参数名和参数值都做xss过滤
     * 如果需要获得原始的值，则通过 super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和 getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        //存在不过滤内容里
        if(xssNames.contains(name.toLowerCase())) {
            return super.getParameter(name);
        }
        name = JsoupUtil.clean(name);
        String value = super.getParameter(name);
        if (Validator.isNotNull(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        //存在不过滤内容里
        if(xssNames.contains(name.toLowerCase())) {
            return arr;
        }

        if (arr != null) {
            IntStream.range(0, arr.length).forEach(i -> arr[i] = JsoupUtil.clean(arr[i]));
        }
        return arr;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做 xss过滤
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = JsoupUtil.clean(name);
        String value = super.getHeader(name);
        if (Validator.isNotNull(value) && !xssNames.contains(name.toLowerCase())) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    /**
     * 获取原始的 request
     */
    private HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取原始的 request的静态方法
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

}
