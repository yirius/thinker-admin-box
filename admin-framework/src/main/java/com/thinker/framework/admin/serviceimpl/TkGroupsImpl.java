package com.thinker.framework.admin.serviceimpl;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.admin.mapper.TkGroupsMapper;
import com.thinker.framework.admin.service.TkGroupsService;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.token.factory.TokenFactory;
import com.thinker.framework.token.util.ThreadTokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TkGroupsImpl extends ThinkerServiceImpl<TkGroupsMapper, TkGroups> implements TkGroupsService {

    @Override
    public List<LabelValue> getCanUseGroups() {
        try{
            Dict tokenInfo = TokenFactory.loadToken().checkLogin();

            return getCanUseGroups(
                    tokenInfo.getLong(ThinkerAdmin.properties().getToken().getIdKey()),
                    tokenInfo.getInt(ThinkerAdmin.properties().getToken().getTypeKey())
            );
        } catch (Exception err) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<LabelValue> getCanUseGroups(Long memberId, int accessType) {
        if(accessType == 0 && memberId == 1) {
            // 如果是超管，返回所有超级管理员可用的
            return SpringContext.getBean(TkGroupsImpl.class).query().eq("status", 1).list()
                    .stream().map(tkGroups -> LabelValue.create(tkGroups.getTitle(), tkGroups.getId()))
                    .collect(Collectors.toList());
        }

        List<String> roleIds = ThreadTokenUtil.getUserRolesIds(memberId, accessType);

        List<LabelValue> labelValues = new ArrayList<>();
        if(roleIds.size() > 0) {
            labelValues.addAll(SpringContext.getBean(TkGroupsImpl.class).query().eq("status", 1).in("id", roleIds).list()
                    .stream().map(tkGroups -> LabelValue.create(tkGroups.getTitle(), tkGroups.getId()))
                    .collect(Collectors.toList()));
        }

        labelValues.addAll(SpringContext.getBean(TkGroupsImpl.class)
                .query().eq("status", 1)
                .eq("member_id", memberId)
                .eq("access_type", accessType).list()
                .stream().map(tkGroups -> LabelValue.create(tkGroups.getTitle(), tkGroups.getId()))
                .collect(Collectors.toList()));

        return labelValues;
    }

}
