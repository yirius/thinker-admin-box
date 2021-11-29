package com.thinker.framework.admin.serviceimpl;

import com.thinker.framework.admin.entity.TkMember;
import com.thinker.framework.admin.mapper.TkMemberMapper;
import com.thinker.framework.admin.service.TkMemberService;
import com.thinker.framework.framework.database.mybatis.ThinkerServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TkMemberImpl extends ThinkerServiceImpl<TkMemberMapper, TkMember> implements TkMemberService {

}
