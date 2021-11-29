package com.thinker.framework.admin.serviceimpl;

import com.thinker.framework.admin.entity.TkRules;
import com.thinker.framework.admin.mapper.TkRulesMapper;
import com.thinker.framework.admin.service.TkRulesService;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TkRulesImpl extends ThinkerServiceImpl<TkRulesMapper, TkRules> implements TkRulesService {

}
