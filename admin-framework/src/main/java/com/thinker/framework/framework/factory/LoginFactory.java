package com.thinker.framework.framework.factory;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ClassLoaderUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.properties.thinker.subclass.AuthTableConstant;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.factory.TokenFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFactory {

    // 获取到当前登录的状态
    public static int loadAccessType(String accessTypeStr){
        if(Validator.isNotEmpty(accessTypeStr)){
            return Integer.parseInt(accessTypeStr);
        }
        // 如果不存在access-type参数，看看有没有登录信息
        if(TokenFactory.loadToken().isLogin()) {
            return TokenFactory.loadToken().getLoginType();
        }
        // 如果都不存在，直接去掉
        return 0;
    }

    /**
     * 无参数获取
     * @return
     */
    public static int loadAccessType(){
        return loadAccessType(
                ThinkerAdmin.request().getRequestParam(
                        ThinkerAdmin.properties().getToken().getTypeKey()
                )
        );
    }

    // 存储一下记录
    private static final Map<String, LoginAbstract<?>> loginAbstractMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> LoginAbstract<T> loadLogin(int index){
        int size = ThinkerAdmin.properties().getToken().getAuthTable().size();
        if(index > size - 1){
            return SpringContext.getOneBeanUseType(LoginAbstract.class);
        }else{
            // 找到对应的登录方法
            AuthTableConstant authTable = ThinkerAdmin.properties().getToken().getAuthTable().get(index);

            // 不存在，先找到所有的类型，判断是不是@Service方式
            if(!loginAbstractMap.containsKey(authTable.getAction())) {
                Map<String, LoginAbstract> map = SpringContext.getApplicationContext().getBeansOfType(LoginAbstract.class);
                map.forEach((s, loginAbstract) -> {
                    if(loginAbstract.getClass().getName().equals(authTable.getAction())) {
                        loginAbstractMap.put(authTable.getAction(), loginAbstract);
                    }
                });
            }

            // 如果还不存在，说明没调用@Service，直接生成
            if(!loginAbstractMap.containsKey(authTable.getAction())) {
                try{
                    loginAbstractMap.put(
                            authTable.getAction(),
                            (LoginAbstract<T>) ClassLoaderUtil.loadClass(authTable.getAction()).newInstance()
                    );
                } catch (Exception err) {
                    err.printStackTrace();
                    return SpringContext.getOneBeanUseType(LoginAbstract.class).setAuthTableConstant(authTable);
                }
            }

            return ((LoginAbstract<T>) loginAbstractMap.get(authTable.getAction())).setAuthTableConstant(authTable);
        }
    }

    public static <T> LoginAbstract<T> loadLogin() {
        return loadLogin(loadAccessType());
    }
}
