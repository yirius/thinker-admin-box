package com.thinker.framework.framework.properties.thinker;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ClassUtil;
import com.thinker.framework.framework.abstracts.SignAbstract;
import com.thinker.framework.framework.abstracts.defaultimpl.SignAspectDefaultImpl;
import com.thinker.framework.framework.properties.thinker.subclass.AuthTableConstant;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.Data;

import java.util.List;

@Data
public class TokenProperties {
    private boolean aop;

    private boolean singleLogin;
    private boolean renewal;
    private String tokenimpl;

    private List<AuthTableConstant> authTable;
    private List<String> signAspect;

    private String idKey;
    private String tokenKey;
    private String typeKey;

    // 使用的规则id
    private List<Integer> ruleIndex;

    /**
     * 获取到authtable
     * @param accessType
     * @return
     */
    public AuthTableConstant getAuthTableUseType(int accessType) {
        if(accessType >= getAuthTable().size() || accessType < 0) {
            throw new ThinkerException("message.thinker.exceptions.indexError", "AuthTable超出边界条件", 0, Dict.create().set("field", "AuthTable"));
        }

        // 获取到登录信息
        return getAuthTable().get(accessType);
    }

    /**
     * 获取到加密的方法
     * @param signIndex
     * @return
     */
    public SignAbstract getSignAspectUseType(int signIndex) {
        if(signIndex >= getSignAspect().size() || signIndex < 0) {
            throw new ThinkerException("message.thinker.exceptions.indexError", "SignAspect超出边界条件", 0, Dict.create().set("field", "SignAspect"));
        }

        try{
            return (SignAbstract) ClassUtil.loadClass(signAspect.get(signIndex)).newInstance();
        } catch (Exception err) {
            err.printStackTrace();

            return new SignAspectDefaultImpl();
        }
    }
}
