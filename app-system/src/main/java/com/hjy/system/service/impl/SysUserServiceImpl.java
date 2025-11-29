package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.common.utils.QueryWrapperUtils;
import com.hjy.system.domain.SysUserPost;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysUserMapper;
import com.hjy.system.service.ISysRoleService;
import com.hjy.system.service.ISysUserPostService;
import com.hjy.system.service.ISysUserRoleService;
import com.hjy.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private ISysRoleService sysRoleService;//@Autowired

//    private SysUserRoleService sysUserRoleService;
    @Autowired
    private ISysUserPostService sysUserPostService;

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
        List<SysRole> roles = new ArrayList<>();
        if (sysUserRoles != null && !sysUserRoles.isEmpty()) {
            queryWrapper.in("role_id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
            roles = sysRoleService.list(queryWrapper);
        }
        sysUser.setRoles( roles);
        return sysUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertUserWithRelations(SysUser entity) {


        boolean saveUserResult =this.save(entity);

        if (!saveUserResult) {
            throw new RuntimeException("用户保存失败，直接抛异常回滚");
        }

        // 2. 存角色关联
        sysUserRoleService.insertUserRoleByUser( entity);

        // 3. 存岗位关联（如果这里报错，第1步和第2步存入的数据会全部撤销，就像没发生过一样）
        sysUserPostService.insertUserPostByUser( entity);

        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserWithRelations(SysUser entity) {
//        return false;
        boolean saveUserResult =this.updateById(entity);
        if (!saveUserResult) {
            throw new RuntimeException("用户更新失败，直接抛异常回滚");
        }
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", entity.getUserId()));
        // 2. 存角色关联
        sysUserRoleService.insertUserRoleByUser( entity);

        sysUserPostService.remove(new QueryWrapper<SysUserPost>().eq("user_id", entity.getUserId()));

        sysUserPostService.insertUserPostByUser( entity);
//
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUsersWithRelationsByIds(List<Long> ids) {

        boolean deleteUserResult = this.removeByIds(ids);

        if (!deleteUserResult) {
            throw new RuntimeException("用户删除失败，直接抛异常回滚");
        }

        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("user_id", ids));

        sysUserPostService.remove(new QueryWrapper<SysUserPost>().in("user_id", ids));

        return true;
    }

    @Override
    public boolean resetPwd(SysUser user) {
        return sysUserMapper.updateById(user)>0;
    }
}
