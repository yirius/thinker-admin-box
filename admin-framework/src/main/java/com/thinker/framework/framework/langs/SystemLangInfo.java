package com.thinker.framework.framework.langs;

import cn.hutool.core.lang.Dict;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemLangInfo extends ILangInfo {

    @Override
    public Dict getZhCn() {
        return Dict.create().set(
                "thinker", Dict.create().set("exceptions", Dict.create()
                        .set("fillNoField", "数据中不存在参数{field},无法进行填充")
                        .set("updateNoParam", "暂无更新参数")
                        .set("updateNoWhere", "暂无更新条件")
                        .set("indexError", "{field}超出边界条件")
                        .set("incFactorZero", "递增因子必须大于0")
                        .set("decFactorZero", "递减因子必须大于0")
                        .set("systemError", "系统内部异常: {err}")
                ).set("uploads", Dict.create()
                        .set("mkdirError", "{file}上传失败, 创建文件夹失败")
                        .set("uploadError", "{file}上传失败, 原因:{reason}")
                        .set("sizeMaxError", "{file}大小超过限制:{size}MB")
                        .set("suffixError", "{suffix}格式不符合要求")
                ).set("admin", Dict.create()
                        .set("emptyUsername", "用户名称为空，请填写")
                        .set("emptyPassword", "密码为空，请填写")
                        .set("userNotFound", "用户未找到，请您检查用户名")
                        .set("userStatusFail", "用户状态异常，无法登录")
                        .set("passwordIncorrect", "用户提交密码不正确，请仔细确认输入正确")
                        .set("notDelete", "数据{reason}无法删除")
                        .set("emptyData", "未提交数据")
                        .set("deleteSuccess", "删除成功")
                        .set("loginSuccess", "登录成功，正在返回...")
                        .set("orignalDashboard", "初始仪表无法设置可关闭")
                ).set("token", Dict.create()
                        .set("noPermission", "暂无权限访问该模块")
                        .set("tokenMulti", "当前账户同时登录过多")
                        .set("inValid", "当前TOKEN不合法")
                        .set("emptyToken", "TOKEN为空，无法校验")
                        .set("notDelete", "数据{reason}无法删除")
                        .set("emptyVercode", "验证码不能为空")
                        .set("vercodeIncorrect", "验证码输入不正确")
                )
        );
    }

    @Override
    public Dict getEn() {
        return Dict.create().set(
                "thinker", Dict.create().set("exceptions", Dict.create()
                        .set("fillNoField", "NO {field} IN DATA, CAN'T FILL")
                        .set("updateNoParam", "No Params When Update Data")
                        .set("updateNoWhere", "No Where When Update Data")
                        .set("indexError", "{field} Index Error")
                        .set("incFactorZero", "Increase Factor Must > 0")
                        .set("decFactorZero", "Decrease Factor Musr > 0")
                        .set("systemError", "System Error: {err}")
                ).set("uploads", Dict.create()
                        .set("mkdirError", "{file} Create Dir Fail")
                        .set("uploadError", "{file} Upload Fail, Reason: {reason}")
                        .set("sizeMaxError", "{file} is Max Than {size}MB")
                        .set("suffixError", "{suffix} SuffixType Not Allow")
                ).set("admin", Dict.create()
                        .set("emptyUsername", "Username is Empty, Please Check")
                        .set("emptyPassword", "Password is Empty, Please Check")
                        .set("userNotFound", "User Not Found, Please Check Username")
                        .set("userStatusFail", "User Status Have Some Error, Can't login")
                        .set("passwordIncorrect", "Password is incorrect. Please carefully confirm")
                        .set("notDelete", "Data{reason}Can Not Delete")
                        .set("emptyData", "Empty Submit Data")
                        .set("deleteSuccess", "Delete Success")
                        .set("loginSuccess", "Login Success, ReDirecting...")
                        .set("orignalDashboard", "Orignal Dashboard Can't Close")
                ).set("token", Dict.create()
                        .set("noPermission", "You Have No Permission To Reach This Module")
                        .set("tokenMulti", "Token OnLine Max, Please ReLogin")
                        .set("inValid", "Token InValid")
                        .set("emptyToken", "Empty Token, Please Login")
                        .set("notDelete", "Data{reason}Can Not Delete")
                        .set("emptyVercode", "Empty Verify Code")
                        .set("vercodeIncorrect", "Verify Code is incorrect")
                )
        );
    }
}
