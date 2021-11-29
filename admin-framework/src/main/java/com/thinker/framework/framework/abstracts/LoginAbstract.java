package com.thinker.framework.framework.abstracts;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.bo.LoginResult;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.properties.thinker.subclass.AuthTableConstant;
import com.thinker.framework.framework.support.exceptions.ThinkerException;

import java.util.List;

public abstract class LoginAbstract<T> {

    /**
     * 获取到当前执行的class对应的参数
     */
    private AuthTableConstant authTableConstant = null;

    public LoginAbstract<T> setAuthTableConstant(AuthTableConstant constant) {
        this.authTableConstant = constant;
        return this;
    }

    public AuthTableConstant getAuthTableConstant() {
        if(authTableConstant == null) {
            ThinkerAdmin.properties().getToken().getAuthTable().forEach(authTableConstant1 -> {
                if(authTableConstant1.getAction().equals(getClass().getName())) {
                    authTableConstant = authTableConstant1;
                }
            });
        }
        return authTableConstant;
    }

    /**
     * 获取到用户信息
     * @param username
     * @return
     */
    public abstract T getUser(Object username);

    /**
     * 获取到用户组别
     * @param user
     * @return
     */
    public abstract List<TextValue> getUserRoles(T user);

    /**
     * 拼接LoginResult
     * @param user
     * @return
     */
    public abstract LoginResult getLoginResult(T user);

    /**
     * 登录的接口
     * @param username
     * @param password
     * @return
     */
    public abstract LoginResult login(String username, String password) throws ThinkerException;

    /**
     * 密码的加密措施
     * @param password
     * @param user
     * @return
     */
    public abstract String createPassword(String password, T user);

    /**
     * 校验密码是否正确
     * @param password
     * @param user
     * @return
     */
    public abstract Boolean verifyPassword(String password, T user);

    /**
     * 更新用户密码
     * @param oldPassword
     * @param newPassword
     * @param id
     * @return
     */
    public abstract Boolean updatePassword(String oldPassword, String newPassword, Long id);

    /**
     * 使用输出结果，生成TOKEN
     * @param loginResult
     * @return
     */
    public abstract String createToken(LoginResult loginResult);

    /**
     * 解析TOKEN值
     * @param token
     * @param isVerify
     * @return
     */
    public abstract Dict decodeToken(String token, boolean isVerify);
}
