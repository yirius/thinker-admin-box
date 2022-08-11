package com.thinker.framework.framework.widgets;

import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.ThinkerAdmin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ThinkerRequest {

    /**
     * 获取到传递数据的实例
     * @return
     */
    public HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 获取到指定的参数，先从PARAM里获取，没有再去HEADER找
     * @param httpServletRequest
     * @param param
     * @return
     */
    public String getRequestParam(HttpServletRequest httpServletRequest, String param) {
        if(httpServletRequest != null) {
            String paramValue = httpServletRequest.getParameter(param);
            if(Validator.isEmpty(paramValue)) {
                paramValue = httpServletRequest.getHeader(param);
            }
            return paramValue;
        } else {
            return null;
        }
    }

    public String getRequestParam(String param) {
        return getRequestParam(getRequest(), param);
    }

    public String getToken() {
        return getRequestParam(getRequest(), ThinkerAdmin.properties().getToken().getTokenKey());
    }

    /**
     * 获取ip地址
     * @return
     */
    public String getIp(HttpServletRequest request) {
        if(request == null){
            request = getRequest();
        }

        String ipAddress = request.getHeader("x-forwarded-for");

        if(!Validator.isIpv4(ipAddress) && !Validator.isIpv6(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(!Validator.isIpv4(ipAddress) && !Validator.isIpv6(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(!Validator.isIpv4(ipAddress) && !Validator.isIpv6(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        ipAddress = "0:0:0:0:0:0:0:1".equals(ipAddress) ? "127.0.0.1" : ipAddress;

        // 判断是否存在代理，存在的的话只有第一个是正确的
        if(ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    public String getIp() {
        return getIp(getRequest());
    }
}
