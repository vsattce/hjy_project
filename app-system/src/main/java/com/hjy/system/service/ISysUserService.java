package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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


    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserWithRelations(SysUser entity);

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUsersWithRelationsByIds(List<Long> ids);


    public boolean resetPwd(SysUser user);

    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds);
}
