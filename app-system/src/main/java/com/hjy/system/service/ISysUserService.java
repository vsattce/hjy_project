package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    public SysUser getUserInfoById(Long id);

    @Transactional(rollbackFor = Exception.class)
    public int insertUserWithRelations(SysUser entity);
}
