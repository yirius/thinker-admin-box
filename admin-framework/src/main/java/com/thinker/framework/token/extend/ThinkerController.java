package com.thinker.framework.token.extend;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.factory.LoginFactory;
import com.thinker.framework.renders.ThinkerTable;
import com.thinker.framework.renders.abstracts.RunClosure;
import com.thinker.framework.renders.assemblys.form.plugins.ElButton;
import com.thinker.framework.renders.assemblys.page.ElPopconfirm;
import com.thinker.framework.renders.assemblys.table.VxeColumn;
import com.thinker.framework.token.factory.TokenFactory;
import com.thinker.framework.token.util.ThreadTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ThinkerController {

    public List<LabelValue> quickLabelValues(Object[]... list) {
        List<LabelValue> textValues = new ArrayList<>();
        for (Object[] strings : list) {
            textValues.add(LabelValue.create(String.valueOf(strings[0]), strings[1]));
        }
        return textValues;
    }

    private String getPathWithinApplication() {
        HttpServletRequest httpServletRequest = ThinkerAdmin.request().getRequest();
        if(httpServletRequest != null) {
            String contextPath = httpServletRequest.getContextPath();
            String requestUri = httpServletRequest.getRequestURI();
            if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
                String path = requestUri.substring(contextPath.length());
                return StringUtils.hasText(path) ? path : "/";
            } else {
                return requestUri;
            }
        }
        return null;
    }

    /**
     * 渲染操作栏
     * @param thinkerTable
     * @return
     */
    public VxeColumn renderOperateColumn(ThinkerTable thinkerTable) {
        return renderOperateColumn(thinkerTable, "操作", null, null, null, null);
    }

    public VxeColumn renderOperateColumn(ThinkerTable thinkerTable, String title,
                                         RunClosure<ElButton> addBtnRun, RunClosure<ElButton> allDelBtnRun,
                                         RunClosure<ElButton> editBtnRun, RunClosure<ElPopconfirm> deletePopconfirm) {
        VxeColumn vxeColumn = thinkerTable.column("_op", title);

        if(TokenFactory.loadToken().isLogin()) {
            String url = getPathWithinApplication();
            if(url != null && Validator.isNotEmpty(url)) {
                List<String> userPageType = findUserPageType(url);

                if(userPageType.contains("addBtn")) {
                    thinkerTable.toolbar().add(addBtnRun);
                }

                if(userPageType.contains("allDelBtn")) {
                    thinkerTable.toolbar().delete(allDelBtnRun);
                }

                Integer width = 0;
                if(userPageType.contains("editBtn")) {
                    vxeColumn.edit(editBtnRun);
                    width+=90;
                }

                if(userPageType.contains("delBtn")) {
                    vxeColumn.delete(deletePopconfirm);
                    width+=90;
                }

                vxeColumn.setWidth(width+"px");
            }
        }

        return vxeColumn;
    }

    /**
     * 找到用户在这个界面上的可用操作
     * @param url
     * @return
     */
    public List<String> findUserPageType(String url) {
        Dict tokenInfo = TokenFactory.loadToken().checkLogin();
        Long userId = tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey());
        int accessType = tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey());

        // 找到可使用的PageType
        return ThreadTokenUtil.findUserUrlPageType(userId, accessType, url);
    }
}
