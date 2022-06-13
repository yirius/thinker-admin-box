package com.thinker.framework.admin.login;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.admin.entity.TkMember;
import com.thinker.framework.admin.serviceimpl.TkGroupsImpl;
import com.thinker.framework.admin.serviceimpl.TkMemberImpl;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.abstracts.LoginAbstract;
import com.thinker.framework.framework.entity.bo.LoginResult;
import com.thinker.framework.framework.entity.vo.TextValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import com.thinker.framework.framework.utils.ToolsUtil;
import com.thinker.framework.token.factory.TokenFactory;
import com.thinker.framework.token.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminLogin extends LoginAbstract<TkMember> {

    /**
     * 获取到用户信息
     *
     * @param username
     * @return
     */
    @Override
    public TkMember getUser(Object username) {
        String findStr = String.valueOf(username).toLowerCase();
        if(Validator.isEmpty(findStr)) {
            throw new ThinkerException("message.thinker.admin.emptyUsername", "用户名不能为空", 201);
        }

        TkMember tkMember = (TkMember) ThinkerAdmin.redis().hashGet("ADMIN_USERS", String.valueOf(findStr));

        if(tkMember == null) {
            // 如果是数字，那就按id找
            if(Validator.isNumber(findStr)) {
                tkMember = SpringContext.getBean(TkMemberImpl.class).getById(findStr);
            }

            // 如果是空，那就需要从手机号或登录名称找
            if(tkMember == null) {
                tkMember = SpringContext.getBean(TkMemberImpl.class)
                        .query()
                        .eq("username", findStr).or().eq("phone", findStr)
                        .one();
            }

            if(tkMember != null) {
                ThinkerAdmin.redis().hashSet("ADMIN_USERS", String.valueOf(findStr), tkMember);
            }
        }

        return tkMember;
    }

    /**
     * 获取到用户组别
     *
     * @param user
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<TextValue> getUserRoles(TkMember user) {
        List<TextValue> roleList = null;
        if(user != null) {
            roleList = (List<TextValue>) ThinkerAdmin.redis().hashGet("ADMIN_USER_ROLES", String.valueOf(user.getId()));
            if(roleList == null) {
                if(Validator.isNotEmpty(user.getGroupIds())) {
                    List<String> strings = ToolsUtil.strToList(user.getGroupIds());
                    if(strings.size() > 0) {
                        roleList = SpringContext.getBean(TkGroupsImpl.class)
                                .query().in("id", strings).eq("status", 1).list()
                                .stream().map(tkGroups -> TextValue.create(tkGroups.getName(), tkGroups.getId())
                                        .set("title", tkGroups.getTitle())
                                        .set("rules", tkGroups.getRuleIds())
                                )
                                .collect(Collectors.toList());

                        ThinkerAdmin.redis().hashSet("ADMIN_USER_ROLES", String.valueOf(user.getId()), roleList);
                    }
                }
            }
        }
        return roleList == null ? new ArrayList<>() : roleList;
    }

    /**
     * 拼接LoginResult
     *
     * @param user
     * @return
     */
    @Override
    public LoginResult getLoginResult(TkMember user) {
        return (new LoginResult()).setUserId(user.getId()).setTokenType(0)
                .setUsername(user.getUsername())
                .setData(Dict.create().set("realname", user.getRealname()).set("phone", user.getPhone()));
    }

    /**
     * 登陆的接口
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public LoginResult login(String username, String password) throws ThinkerException {
        TkMember tkMember = getUser(username);

        if(tkMember == null) {
            throw new ThinkerException("message.thinker.admin.userNotFound", "用户未找到，请您检查用户名", 202);
        }

        if(!tkMember.getStatus().equals(1)) {
            throw new ThinkerException("message.thinker.admin.userStatusFail", "用户状态异常，无法登录", 203);
        }

        if(verifyPassword(password, tkMember)) {
            return getLoginResult(tkMember);
        } else {
            throw new ThinkerException("message.thinker.admin.passwordIncorrect", "用户提交密码不正确，请仔细确认输入正确", 204);
        }
    }

    /**
     * 密码的加密措施
     *
     * @param password
     * @param user
     * @return
     */
    @Override
    public String createPassword(String password, TkMember user) {
        if(Validator.isEmpty(password)) {
            throw new ThinkerException("message.thinker.admin.emptyPassword", "密码为空，请填写", 205);
        }

        return SecureUtil.sha1(SecureUtil.md5(password).toUpperCase() + user.getSalt()).toUpperCase();
    }

    /**
     * 校验密码是否正确
     *
     * @param password
     * @param user
     * @return
     */
    @Override
    public Boolean verifyPassword(String password, TkMember user) {
        return user.getPassword().equals(createPassword(password, user));
    }

    /**
     * 更新用户密码
     *
     * @param oldPassword
     * @param newPassword
     * @param id
     * @return
     */
    @Override
    public Boolean updatePassword(String oldPassword, String newPassword, Long id) {
        TkMember tkMember = getUser(id);

        if(tkMember == null) {
            throw new ThinkerException("message.thinker.admin.userNotFound", "用户未找到，请您检查用户名", 202);
        }

        if(Validator.isEmpty(oldPassword) || Validator.isEmpty(newPassword)) {
            throw new ThinkerException("message.thinker.admin.emptyPassword", "密码为空，请填写", 205);
        }

        if(!verifyPassword(oldPassword, tkMember)) {
            throw new ThinkerException("message.thinker.admin.passwordIncorrect", "用户提交密码不正确，请仔细确认输入正确", 204);
        }

        tkMember.setSalt(ToolsUtil.rand(6, ToolsUtil.RandFormat.NUMBER));
        tkMember.setPassword(createPassword(newPassword, tkMember));

        if(SpringContext.getBean(TkMemberImpl.class).updateById(tkMember)) {
            ThinkerAdmin.redis().hashDel("ADMIN_USERS", String.valueOf(tkMember.getId()), tkMember.getUsername().toLowerCase(), tkMember.getPhone());

            return true;
        }

        return false;
    }

    /**
     * 使用输出结果，生成TOKEN
     *
     * @param loginResult
     * @return
     */
    @Override
    public String createToken(LoginResult loginResult) {
        return TokenFactory.loadToken().setLoginId(loginResult.getUserId(), 0);
    }

    /**
     * 解析TOKEN值
     *
     * @param token
     * @param isVerify
     * @return
     */
    @Override
    public Dict decodeToken(String token, boolean isVerify) {
        if(isVerify) {
            JwtUtil.verify(token, getAuthTableConstant().getSecret());
        }

        return JwtUtil.decode(token);
    }
}
