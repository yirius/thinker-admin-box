package com.thinker.framework.token.util;

import cn.hutool.core.lang.Dict;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.token.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

    /**
     * 生成Token
     * @param userId
     * @param accessType
     * @param useSecretKey
     * @param expiredTime
     * @return
     */
    public static String sign(Long userId, int accessType, String useSecretKey, int expiredTime) {
        try{
            //创建数据并循环
            return JWT.create()
                    // 添加id
                    .withClaim(ThinkerAdmin.properties().getToken().getIdKey(), userId)
                    // 添加账户类型
                    .withClaim(ThinkerAdmin.properties().getToken().getTypeKey(), accessType)
                    // 设置过期时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime * 1000L))
                    .sign(Algorithm.HMAC256(useSecretKey));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Object getTypedClaim(Claim claim) {
        if(claim.asInt() != null){
            return claim.asInt();
        } else if(claim.asBoolean() != null){
            return claim.asBoolean();
        } else if(claim.asLong() != null){
            return claim.asLong();
        } else if(claim.asMap() != null){
            return claim.asMap();
        } else if(claim.asDate() != null){
            return claim.asDate();
        } else if(claim.asDouble() != null){
            return claim.asDouble();
        }

        return claim.asString();
    }

    /**
     * 校验TOKEN是否正确
     * @param token
     * @param secretKey
     * @return
     */
    public static boolean verify(String token, String secretKey) {
        String tokenMD5 = SecureUtil.md5(token).toUpperCase();
        try{
            if(ThinkerAdmin.thread().getObject("TOKEN_" + tokenMD5) != null) {
                return true;
            } else {
                //开始校验
                Algorithm algorithm = Algorithm.HMAC256(secretKey);
                JWTVerifier verifier = JWT.require(algorithm).build();
                Map<String, Claim> claims = verifier.verify(token).getClaims();
                // 校验之后直接存字段
                Dict tokenInfo = Dict.create();
                for (String key : claims.keySet()){
                    tokenInfo.put(key, getTypedClaim(claims.get(key)));
                }
                ThinkerAdmin.thread().setObject("TOKEN_" + SecureUtil.md5(token).toUpperCase(), tokenInfo);
                return true;
            }
        } catch (Exception err) {
            return false;
        }
    }

    /**
     * 解析TOKEN内容
     * @param token
     * @return
     */
    public static Dict decode(String token) {
        String tokenMD5 = SecureUtil.md5(token).toUpperCase();

        try {
            if(ThinkerAdmin.thread().getObject("TOKEN_" + tokenMD5) != null) {
                return (Dict) ThinkerAdmin.thread().getObject("TOKEN_" + tokenMD5);
            } else {
                //找到所有的信息
                Map<String, Claim> claims = JWT.decode(token).getClaims();

                Dict tokenInfo = Dict.create();
                for (String key : claims.keySet()){
                    tokenInfo.put(key, getTypedClaim(claims.get(key)));
                }

                ThinkerAdmin.thread().setObject("TOKEN_" + tokenMD5, tokenInfo);
                return tokenInfo;
            }
        } catch (JWTDecodeException e) {
            throw new ThinkerException("message.thinker.token.inValid", "当前TOKEN不合法", 1001);
        }
    }
}
