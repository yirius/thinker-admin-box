package com.thinker.framework.token.tokenimpl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.properties.thinker.subclass.AuthTableConstant;
import com.thinker.framework.token.abstracts.TokenAbstract;
import com.thinker.framework.token.exception.NotLoginException;
import com.thinker.framework.token.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtToken extends TokenAbstract {
    private static final String EXPIRED_PREV = "JWT_TOKEN_EXPIRED";
    private static final String SINGLE_IP_PREV = "JWT_TOKEN_IP";

    /**
     * 设置登录信息，同时需返回登录的TOKEN字符串
     *
     * @param userId
     * @param accessType
     * @return
     */
    @Override
    public String setLoginId(Object userId, int accessType) {
        // 获取到登录信息
        AuthTableConstant authTableConstant = ThinkerAdmin.properties().getToken().getAuthTableUseType(accessType);

        // 生成TOKEN参数
        String token = JwtUtil.sign(Convert.toLong(userId), accessType, authTableConstant.getSecret(), authTableConstant.getExpire());
        String tokenMd5 = SecureUtil.md5(token).toUpperCase(),
               tokenContent = accessType+"|"+ ThinkerAdmin.request().getIp();

        // 设置过期时间，并且设置过期时长
        ThinkerAdmin.redis().set(EXPIRED_PREV + "_" + tokenMd5, userId+"|"+tokenContent, (long) authTableConstant.getExpire());

        if(ThinkerAdmin.properties().getToken().isSingleLogin()) {
            // 设置账号登录状态，记录登录IP
            ThinkerAdmin.redis().hashSet(SINGLE_IP_PREV, userId +"|"+accessType, ThinkerAdmin.request().getIp());
        } else {
            // 记录当前登录的所有账号的IP
            ThinkerAdmin.redis().hashSet(SINGLE_IP_PREV+"_"+userId+"_"+accessType, ThinkerAdmin.request().getIp(), DateUtil.now());
        }

        return token;
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    @Override
    public boolean isLogin() {
        String tokenValue = ThinkerAdmin.request().getToken();
        if(!Validator.isEmpty(tokenValue)) {
            // 找到登录信息
            Dict tokenInfo = JwtUtil.decode(tokenValue);
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            // 加密MD5，方便读取
            String tokenMd5 = SecureUtil.md5(tokenValue).toUpperCase();
            // 是否单点登录
            if(ThinkerAdmin.properties().getToken().isSingleLogin()) {
                String currentIp = (String) ThinkerAdmin.redis().hashGet(SINGLE_IP_PREV, userId +"|"+accessType);
                if(Validator.isEmpty(currentIp)) {
                    ThinkerAdmin.redis().hashSet(SINGLE_IP_PREV, userId +"|"+accessType, ThinkerAdmin.request().getIp());
                } else {
                    // IP不一致，则不能登录
                    if(!currentIp.equals(ThinkerAdmin.request().getIp())) {
                        return false;
                    }
                }
            }
            // 如果存在信息，说明有操作，更新了
            if(ThinkerAdmin.redis().has(EXPIRED_PREV+"_"+tokenMd5)) {
                return true;
            }
            // 进行校验
            if(JwtUtil.verify(tokenValue, ThinkerAdmin.properties().getToken().getAuthTableUseType(accessType).getSecret())) {
                if(ThinkerAdmin.properties().getToken().isRenewal()) {
                    // 是否需要续期
                    ThinkerAdmin.redis().expire(
                            EXPIRED_PREV+"_"+tokenMd5,
                            (long) ThinkerAdmin.properties().getToken().getAuthTableUseType(accessType).getExpire()
                    );
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否登录，没登录扔出异常
     *
     * @throws NotLoginException
     */
    @Override
    public Dict checkLogin() throws NotLoginException {
        String tokenValue = ThinkerAdmin.request().getToken();
        if(!Validator.isEmpty(tokenValue)) {
            // 找到登录信息
            Dict tokenInfo = JwtUtil.decode(tokenValue);
            int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());
            Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
            AuthTableConstant authTableConstant = ThinkerAdmin.properties().getToken().getAuthTableUseType(accessType);
            // 加密MD5，方便读取
            String tokenMd5 = SecureUtil.md5(tokenValue).toUpperCase();
            // 是否单点登录
            if(ThinkerAdmin.properties().getToken().isSingleLogin()) {
                String currentIp = (String) ThinkerAdmin.redis().hashGet(SINGLE_IP_PREV, userId +"|"+accessType);
                if(Validator.isEmpty(currentIp)) {
                    ThinkerAdmin.redis().hashSet(SINGLE_IP_PREV, userId +"|"+accessType, ThinkerAdmin.request().getIp());
                } else {
                    // IP不一致，则不能登录
                    if(!currentIp.equals(ThinkerAdmin.request().getIp())) {
                        throw new NotLoginException("message.thinker.token.tokenMulti", "当前账户同时登录过多", 1001);
                    }
                }
            }
            // 如果不存在信息，说明不然是过期了，不然就是没记录
            if(!ThinkerAdmin.redis().has(EXPIRED_PREV+"_"+tokenMd5)) {
                // 进行校验
                if(!JwtUtil.verify(tokenValue, authTableConstant.getSecret())) {
                    throw new NotLoginException("message.thinker.token.inValid", "当前TOKEN不合法", 1001);
                }
                ThinkerAdmin.redis().set(
                        EXPIRED_PREV+"_"+tokenMd5,
                        userId+"|"+accessType+"|"+ ThinkerAdmin.request().getIp(),
                        (long) authTableConstant.getExpire()
                );
            }
            if(ThinkerAdmin.properties().getToken().isRenewal()) {
                ThinkerAdmin.redis().expire(EXPIRED_PREV+"_"+tokenMd5,(long) authTableConstant.getExpire());
            }
            // 返回信息
            return tokenInfo;
        }

        throw new NotLoginException("message.thinker.token.emptyToken", "TOKEN为空，无法校验", 1001);
    }

    /**
     * 注销登录
     *
     * @return
     */
    @Override
    public boolean logout() {
        try{
            // 设置过期时间，并且设置过期时长
            ThinkerAdmin.redis().rm(EXPIRED_PREV + "_" + SecureUtil.md5(ThinkerAdmin.request().getToken()).toUpperCase());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return true;
    }

    /**
     * 获取到当前登录的ID
     *
     * @return
     */
    @Override
    public Object getLoginId() throws NotLoginException {
        return checkLogin().getLong(ThinkerAdmin.properties().getToken().getIdKey());
    }

    /**
     * 使用指定TOKEN值获取登录ID
     *
     * @param tokenValue
     * @return
     */
    @Override
    public Object getLoginIdByToken(String tokenValue) {
        if(!Validator.isEmpty(tokenValue)) {
            // 找到登录信息
            Dict tokenInfo = JwtUtil.decode(tokenValue);
            return tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
        }

        return null;
    }

    /**
     * 获取登录的类型，从0到100
     *
     * @return
     */
    @Override
    public int getLoginType() throws NotLoginException{
        return checkLogin().getInt(ThinkerAdmin.properties().getToken().getTypeKey());
    }

    /**
     * 使用指定TOKEN值获取登录的类型
     *
     * @param tokenValue
     * @return
     */
    @Override
    public int getLoginTypeByToken(String tokenValue) {
        if(!Validator.isEmpty(tokenValue)) {
            // 找到登录信息
            Dict tokenInfo = JwtUtil.decode(tokenValue);
            return tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTokenKey());
        }

        return -1;
    }
}
