package com.thinker.framework.framework.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        httpServletResponse.
        if(e.getLocalizedMessage() == null || !(e.getLocalizedMessage().contains("Broken pipe") || e.getLocalizedMessage().contains("Connection reset by peer"))) {
            log.info("err", e);
        }
        return null;
    }
}
