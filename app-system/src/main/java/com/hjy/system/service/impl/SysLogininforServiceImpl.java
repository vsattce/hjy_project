package com.hjy.system.service.impl;

import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysLogininfor;
import com.hjy.system.mapper.SysLogininforMapper;
import com.hjy.system.service.ISysLogininforService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysLogininforServiceImpl extends BaseServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService {
    @Override
    public boolean save(SysLogininfor entity) {
        entity.setLoginTime(new Date());
        return super.save(entity);
    }
}
