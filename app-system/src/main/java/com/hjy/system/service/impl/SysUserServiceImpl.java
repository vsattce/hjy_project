package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.common.utils.QueryWrapperUtils;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysUserMapper;
import com.hjy.system.service.ISysRoleService;
import com.hjy.system.service.ISysUserRoleService;
import com.hjy.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public IPage<SysUser> page(IPage<SysUser> iPage, SysUser entity, Map<String,Object> parameters) {
        Long deptId = entity.getDeptId();

        // 这一步是为了骗过 QueryWrapperUtils，让它觉得“哦，没有部门ID，那我不生成 dept_id = ? 了”
        entity.setDeptId(null);
//        parameters.remove("deptId");
        if (parameters != null) {
            parameters.remove("deptId");
        }
        QueryWrapper<SysUser> queryWrapper = QueryWrapperUtils.entity2Wrapper(entity,parameters);
        if (deptId != null&& deptId != 0){
            // (可选) 讲究人的做法：把值塞回去，万一后面 entity 还要用
//            entity.setDeptId(deptId);
//            parameters.put("deptId",deptId);

            queryWrapper.and(wrapper ->
                    wrapper.eq("dept_id",deptId)
                            .or()
                            .apply("dept_id IN (SELECT dept_id FROM sys_dept WHERE find_in_set({0}, ancestors))", deptId));
            }
        return super.page(iPage, queryWrapper);
    }

//    @Override
    public SysUser getUserInfoById(Long id){
        SysUser sysUser = sysUserMapper.selectById(id);

        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new SysUserRole(id, null));
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
        queryWrapper.in("role_id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        List<SysRole> roles = sysRoleService.list(queryWrapper);
        sysUser.setRoles( roles);
        return sysUser;
    }
}
