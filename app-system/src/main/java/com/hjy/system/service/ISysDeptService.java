package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysDept;
import com.hjy.common.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

public interface ISysDeptService extends IBaseService<SysDept> {

    @Transactional(rollbackFor = Exception.class)
    public boolean removeTreeByIds(Long[] ids);
}
