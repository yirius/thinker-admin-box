package com.thinker.framework.framework.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ToolsUtil {

    public enum RandFormat {
        ALL, CHAR, NUMBER, NORMAL
    }

    /**
     * description 获取指定位数的随机数
     *
     * @param length 1
     * @return java.lang.String
     */
    public static String rand(int length, RandFormat randFormat) {
        String chars;
        if(randFormat.equals(RandFormat.ALL)) {
            chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-@#~";
        } else if(randFormat.equals(RandFormat.CHAR)) {
            chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        } else if(randFormat.equals(RandFormat.NUMBER)) {
            chars = "0123456789";
        } else {
            chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        }

        Random random = new Random();
        StrBuilder strBuilder = new StrBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(chars.length());
            strBuilder.append(chars.charAt(number));
        }

        return strBuilder.toStringAndReset();
    }

    public static String rand(int length) {
        return rand(length, RandFormat.NUMBER);
    }

    /**
     * 设置等待
     * @param name
     * @param waitTime
     * @return
     */
    public static boolean setTimeout(String name, Long waitTime) {
        Long canNext = (Long) ThinkerAdmin.redis().get(name, null);
        Long curTime = (new Date().getTime());
        if(canNext == null){
            // 如果不存在这个标记, 那就说明原来没进行过, 可以进行下一步
            ThinkerAdmin.redis().set(name, curTime, waitTime);
            return true;
        }else{
            // 如果日期大于记录时间seconds, 重新记录然后可以返回下一步
            if((curTime - canNext) > waitTime * 1000){
                ThinkerAdmin.redis().set(name, curTime, waitTime);
                return true;
            }else{
                return false;
            }
        }
    }

    public static boolean setTimeout(String name) {
        return setTimeout(name, 60L);
    }

    /**
     * 路径转名称
     * @param path
     * @return
     */
    public static String pathToName(String path) {
        if(path.contains("?")) {
            path = path.split("\\?")[0];
        }
        return path.replace("/", "").replace(".", "").replace(" ", "");
    }

    /**
     * 将一个str使用逗号分隔，转成list
     * @param value
     * @return
     */
    public static List<String> strToList(String value) {
        List<String> valueList = new ArrayList<>();
        if(Validator.isNotEmpty(value)) {
            if(value.startsWith("[") && value.endsWith("]")) {
                valueList = JSON.parseArray(value, String.class);
            } else {
                String[] valueArr = value.split(",");
                for (String s : valueArr) {
                    if (!valueList.contains(s)) {
                        valueList.add(s);
                    }
                }
            }
        }
        return valueList;
    }

    /**
     * 生成验证码
     * @param httpServletRequest
     * @param httpServletResponse
     */
    public static void captchaCreate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            LineCaptcha captcha = CaptchaUtil.createLineCaptcha(80, 40, 4, 30);
            captcha.setGenerator(new RandomGenerator("0123456789", 4));
            captcha.createCode();

            captcha.createCode();
            WebUtils.setSessionAttribute(httpServletRequest, "captcha", captcha);

            //图形验证码写出，可以写出到文件，也可以写出到流
            captcha.write(httpServletResponse.getOutputStream());
        } catch (Exception err) {
            throw new ThinkerException("验证码生成失败，请您点击右上角，刷新界面后重试");
        }
    }

    /**
     * 验证登录图片
     * @param captchaCode
     * @param httpServletRequest
     * @return
     */
    public static boolean captchaVerify(String captchaCode, HttpServletRequest httpServletRequest) {
        LineCaptcha lineCaptcha = (LineCaptcha) WebUtils.getSessionAttribute(httpServletRequest, "captcha");
        if(lineCaptcha != null) {
            return lineCaptcha.verify(captchaCode);
        }

        return false;
    }
}
