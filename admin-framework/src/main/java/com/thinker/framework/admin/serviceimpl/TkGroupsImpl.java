package com.thinker.framework.admin.serviceimpl;

import com.thinker.framework.admin.entity.TkGroups;
import com.thinker.framework.admin.mapper.TkGroupsMapper;
import com.thinker.framework.admin.service.TkGroupsService;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TkGroupsImpl extends ThinkerServiceImpl<TkGroupsMapper, TkGroups> implements TkGroupsService {

}
