package com.thinker.framework.admin.serviceimpl;

import com.thinker.framework.admin.entity.TkLogs;
import com.thinker.framework.admin.mapper.TkLogsMapper;
import com.thinker.framework.admin.service.TkLogsService;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TkLogsImpl extends ThinkerServiceImpl<TkLogsMapper, TkLogs> implements TkLogsService {

}
