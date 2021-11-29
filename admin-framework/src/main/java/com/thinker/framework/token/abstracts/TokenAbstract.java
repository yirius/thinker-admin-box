package com.thinker.framework.token.abstracts;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.token.exception.NotLoginException;

public abstract class TokenAbstract {
    /**
     * 设置登录信息，同时需返回登录的TOKEN字符串
     * @param userId
     * @param accessType
     * @return
     */
    public abstract String setLoginId(Object userId, int accessType);

    /**
     * 是否已经登录
     * @return
     */
    public abstract boolean isLogin();

    /**
     * 检查是否登录，没登录扔出异常
     * @throws NotLoginException
     */
    public abstract Dict checkLogin() throws NotLoginException;

    /**
     * 注销登录
     * @return
     */
    public abstract boolean logout();

    /**
     * 获取到当前登录的ID
     * @return
     */
    public abstract Object getLoginId() throws NotLoginException;

    /**
     * 使用指定TOKEN值获取登录ID
     * @param tokenValue
     * @return
     */
    public abstract Object getLoginIdByToken(String tokenValue);

    /**
     * 获取登录的类型，从0到100
     * @return
     */
    public abstract int getLoginType() throws NotLoginException;

    /**
     * 使用指定TOKEN值获取登录的类型
     * @param tokenValue
     * @return
     */
    public abstract int getLoginTypeByToken(String tokenValue);
}
