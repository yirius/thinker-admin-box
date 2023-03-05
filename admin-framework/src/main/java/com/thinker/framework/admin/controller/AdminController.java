package com.thinker.framework.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.serviceimpl.TkRulesImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.aspect.logs.ThinkerTableLogAspect;
import com.thinker.framework.framework.config.ThinkerConfig;
import com.thinker.framework.framework.entity.bo.LoginResult;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.framework.langs.ILangInfo;
import com.thinker.framework.framework.service.UploadService;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.CacheUtil;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.token.aspect.CheckLoginAspect;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping(value = "/thinker/admin")
public class AdminController {

    @RequestMapping(value = "/version")
    public String version() {
        return ThinkerAdmin.version;
    }

    public static final LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 40, 4, 30);
    private static final RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);

    /**
     * 语言设置
     * @return
     */
    @RequestMapping(value = "/langs")
    public ThinkerResponse langs() {
        Dict dict = (Dict) ThinkerAdmin.redis().get("SYS_LANG");
        if(dict == null) {
            // 系统内部设置的LANGS
            List<TkRules> tkRulesList = SpringContext.getBean(TkRulesImpl.class).query().select("title,title_en,name").list();
            Dict cnLang = Dict.create();
            Dict enLang = Dict.create();
            tkRulesList.forEach(tkRules -> {
                cnLang.set(tkRules.getName(), tkRules.getTitle());
                enLang.set(tkRules.getName(), tkRules.getTitleEn());
            });
            dict = Dict.create().set("zh-cn", Dict.create().set("menu", cnLang)).set("en", Dict.create().set("menu", enLang));

            ThinkerAdmin.redis().set("SYS_LANG", dict);
        }

        Dict enLang = (Dict) dict.get("en");
        Dict zhCnLang = (Dict) dict.get("zh-cn");

        SpringContext.getApplicationContext().getBeansOfType(ILangInfo.class).forEach((s, iLangInfo) -> {
            if(iLangInfo.getZhCn() != null) {
                zhCnLang.putAll(iLangInfo.getZhCn());
            }
            if(iLangInfo.getEn() != null) {
                enLang.putAll(iLangInfo.getEn());
            }
        });

        return new ThinkerResponse().data(dict).success();
    }

    /**
     * 验证码
     * @param httpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/captcha")
    public void captcha(HttpServletResponse httpServletResponse) throws IOException {
        if(lineCaptcha.getGenerator() != randomGenerator) {
            lineCaptcha.setGenerator(randomGenerator);
        }
        lineCaptcha.createCode();
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write(httpServletResponse.getOutputStream());
    }

    /**
     * 直接登录，根据参数获取
     * @param username
     * @param password
     * @return
     */
    @ThinkerTableLogAspect(value = "登录日志")
    @RequestMapping(value = "/login")
    public ThinkerResponse login(String username, String password, String vercode) {
        // 如果密码错误次数过多，则显示验证码
        int loginErr = (int) ThinkerAdmin.redis().get("LOGIN_"+username+"_ERR", 0);
        if(loginErr == 1 && Validator.isEmpty(vercode)) {
            return new ThinkerResponse().local("message.thinker.token.emptyVercode").msg("验证码不能为空").fail().code(505);
        }

        // 如果提交了验证码，就验证
        if(Validator.isNotEmpty(vercode) && !lineCaptcha.verify(vercode)) {
            return new ThinkerResponse().local("message.thinker.token.vercodeIncorrect").msg("验证码输入不正确").fail().code(506);
        }

        try {
            // 找到对应登录方法
            LoginAbstract<?> loginAbstract = LoginFactory.loadLogin();
            // 判断登录
            LoginResult loginResult = loginAbstract.login(username, password);
            // 检查是否存在token，不存在则生成
            if(Validator.isEmpty(loginResult.getToken())) {
                loginResult.setToken(loginAbstract.createToken(loginResult));
            }

            // 删除记录，防止下次登录无法直接
            ThinkerAdmin.redis().rm("LOGIN_"+username+"_ERR");

            // 返回结果
            return ThinkerAdmin.response().data(loginResult).success();
        } catch (Exception err) {
            // 3分钟内登录，需要再次确认验证码
            ThinkerAdmin.redis().set("LOGIN_"+username+"_ERR", 1, 180L);
            // 返回结果
            return ThinkerAdmin.response().msg(err.getMessage()).fail();
        }
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public ThinkerResponse logout() {
        return ThinkerAdmin.response().msg(TokenFactory.loadToken().logout() ? "Logout Success" : "Logout Fail").success();
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/userinfo")
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ThinkerResponse userinfo() {
        // 找到对应登录方法
        LoginAbstract loginAbstract = LoginFactory.loadLogin();

        return ThinkerAdmin.response().data(loginAbstract.getLoginResult(
                loginAbstract.getUser(TokenFactory.loadToken().getLoginId())
        )).success();
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/editPassword")
    @SuppressWarnings({"rawtypes"})
    public ThinkerResponse editPassword(String oldPassword, String newPassword) {
        // 找到对应登录方法
        LoginAbstract loginAbstract = LoginFactory.loadLogin();

        return ThinkerAdmin.response().data(
                loginAbstract.updatePassword(
                        oldPassword, newPassword,
                        Long.parseLong(String.valueOf(TokenFactory.loadToken().getLoginId()))
                )
        ).success();
    }

    /**
     * 获取到菜单列表
     * @return
     */
    @RequestMapping(value = "/menus")
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ThinkerResponse menus() {
        Dict tokenInfo = TokenFactory.loadToken().checkLogin();
        Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
        int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

        return ThinkerAdmin.response().data(
                CacheUtil.getUserMenu(userId, accessType)
        ).success();
    }

    /**
     * 上传组件
     * @param request
     * @param isImage
     * @return
     */
    @RequestMapping(value = "/upload")
    public ThinkerResponse upload(HttpServletRequest request, Boolean isImage, Boolean isThumb) {
        if(isImage == null) {
            isImage = false;
        }
        if(isThumb == null) {
            isThumb = false;
        }
        return new ThinkerResponse().data(SpringContext.getBean(UploadService.class).upload(request, isImage, isThumb)).success();
    }

    @CheckLoginAspect
    @RequestMapping(value = "/downloadLogs", produces = {"application/octet-stream;charset=UTF-8","application/zip;charset=UTF-8"})
    public byte[] downloadLogs(String path, HttpServletResponse httpServletResponse) {
        if(Validator.isNotEmpty(path)) {
            String logPath = ThinkerAdmin.file().getDirPath("logs/") + path.split("\\.")[0] + File.separator + path;

            if(FileUtil.exist(logPath)) {
                httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + path);

                return FileUtil.readBytes(logPath);
            }
        }

        return null;
    }
}
