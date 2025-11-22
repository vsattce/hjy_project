package com.hjy.system.service.impl;

import com.hjy.common.core.domain.entity.SysDept;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.common.core.service.impl.TreeBaseServiceImpl;
import com.hjy.system.mapper.SysDeptMapper;
import com.hjy.system.service.ISysDeptService;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl extends TreeBaseServiceImpl<SysDeptMapper, SysDept,Long> implements ISysDeptService {
    
}
