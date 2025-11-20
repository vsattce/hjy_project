package com.hjy.framework.web.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.domain.model.LoginUser;
import com.hjy.common.enums.UserStatus;
import com.hjy.common.utils.StringUtils;
import com.hjy.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("username = " + username);

        SysUser user = sysUserService.selectUserByUserName(username);
        System.out.println("username = " + username);
        System.out.println("userObj = " + user);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
//            throw new ServiceException(MessageUtils.message("user.not.exists"));
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
//            throw new ServiceException(MessageUtils.message("user.password.delete"));
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
//            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, null);
    }
}
