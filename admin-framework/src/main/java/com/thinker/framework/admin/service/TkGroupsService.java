package com.thinker.framework.admin.service;

import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.framework.database.mybatis.ThinkerIService;
import com.thinker.framework.framework.entity.vo.LabelValue;

import javax.xml.soap.Text;
import java.util.List;

public interface TkGroupsService extends ThinkerIService<TkGroups> {
    public List<LabelValue> getCanUseGroups();
    public List<LabelValue> getCanUseGroups(Long memberId, int accessType);
}
