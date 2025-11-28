package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysPost;
import com.hjy.system.domain.SysUserPost;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysPostMapper;
import com.hjy.system.service.ISysPostService;
import com.hjy.system.service.ISysUserPostService;
import com.hjy.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysUserPostService sysUserPostService;

    @Override
    public List<SysPost> getByUserId(Long id) {

        List<SysUserPost> sysUserPosts = sysUserPostService.list(new SysUserPost(id, null));

        if (sysUserPosts == null || sysUserPosts.isEmpty()) {
            return Collections.emptyList();
        }

        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("post_id", sysUserPosts.stream().map(SysUserPost::getPostId).collect(Collectors.toList()));


        return super.list(queryWrapper);
    }
}
