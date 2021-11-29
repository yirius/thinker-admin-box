package com.thinker.framework.token.factory;

import cn.hutool.core.util.ClassLoaderUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.token.abstracts.TokenAbstract;
import com.thinker.framework.token.tokenimpl.JwtToken;

import java.util.HashMap;
import java.util.Map;

public class TokenFactory {

    /**
     * 记录TOKEN方法
     */
    public static Map<String, TokenAbstract> tokenAbstractMap = new HashMap<String, TokenAbstract>() {
        {
            put("JWT", new JwtToken());
        }
    };

    /**
     * load参数
     * @param className
     * @return
     */
    public static TokenAbstract loadToken(String className) {
        if(!tokenAbstractMap.containsKey(className.toUpperCase())) {
            try{
                tokenAbstractMap.put(className.toUpperCase(), (TokenAbstract) ClassLoaderUtil.loadClass(className).newInstance());
            } catch (Exception err) {
                err.printStackTrace();
                return tokenAbstractMap.get("JWT");
            }
        }
        return tokenAbstractMap.get(className.toUpperCase());
    }

    public static TokenAbstract loadToken() {
        return loadToken(ThinkerAdmin.properties().getToken().getTokenimpl());
    }
}
