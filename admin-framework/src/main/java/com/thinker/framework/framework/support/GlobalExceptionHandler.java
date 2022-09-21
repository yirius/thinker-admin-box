package com.thinker.framework.framework.support;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RestControllerAdvice
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 所有异常的抛出
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ThinkerResponse handleException(Exception e){
        if(e.getLocalizedMessage() == null || !(e.getLocalizedMessage().contains("Broken pipe") || e.getLocalizedMessage().contains("Connection reset by peer"))) {
            log.error("系统内部异常，异常信息", e);
        }
        return new ThinkerResponse().local("message.thinker.exceptions.systemError").msg("系统内部异常:"+e.getLocalizedMessage()).data(Dict.create().set("err", e.getLocalizedMessage())).fail();
    }

    /**
     * Thinker异常的抛出
     * @param e
     * @return
     */
    @ExceptionHandler(value = ThinkerException.class)
    public ThinkerResponse handleThinkerException(ThinkerException e) {
        String msg = e.getMessage();
        if(msg == null) {
            msg = e.toString();
        }

        // 记录校验参数
        log.warn("msg:"+msg+"|code:"+e.getCode());

        return new ThinkerResponse().msg(msg).local(e.getLocal()).data(e.getData()).fail().code(e.getCode());
    }

    /**
     * 传递实体参数，加入数据库等的时候
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ThinkerResponse handleValidException(BindException e){
        StrBuilder message = new StrBuilder();

        //找到并排序
        List<FieldError> fieldErrors = new ArrayList<>(e.getBindingResult().getFieldErrors());
        fieldErrors.sort(Comparator.comparingInt(o -> o.getField().hashCode()));

        for (FieldError error : fieldErrors) {
            message.append(error.getDefaultMessage()).append(",");
        }

        String msg = message.toStringAndReset();
        return new ThinkerResponse().msg(msg.substring(0, msg.length() - 1)).fail();
    }

    // 堆栈信息打印方法如下
    public static String getThrowableStackInfo(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String msg = buf.toString();
        try {
            buf.close();
        } catch (Exception t) {
            return e.getMessage();
        }
        return msg;
    }
}
