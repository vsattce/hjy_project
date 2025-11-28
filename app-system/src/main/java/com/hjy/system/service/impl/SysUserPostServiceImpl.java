package com.hjy.system.service.impl;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysUserPost;
import com.hjy.system.mapper.SysUserPostMapper;
import com.hjy.system.service.ISysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserPostServiceImpl extends BaseServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    public boolean insertUserPostByUser(SysUser entity) {
        List<SysUserPost> sysUserPosts = new ArrayList<>();
        if (entity.getPostIds() != null) {
            for (Long postId : entity.getPostIds()) {
                sysUserPosts.add(new SysUserPost(entity.getUserId(), postId));
            }
            if (!sysUserPosts.isEmpty()) {
                sysUserPostMapper.insert(sysUserPosts);
            }
        }

        return true;
    }
}
