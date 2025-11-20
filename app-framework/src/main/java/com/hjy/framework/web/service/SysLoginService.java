package com.hjy.framework.web.service;

import com.hjy.common.core.domain.model.LoginUser;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(token);
        }
        catch (Exception e){
            System.out.printf("login-e:"+e);
        }
        finally {

        }
        //通过认证的对象，获得对应的实体信息，强制转换成为LoginUser
        LoginUser loginUser = null;
        if (authentication != null) {
            loginUser = (LoginUser) authentication.getPrincipal();
        }
        if (loginUser != null) {
            return tokenService.createToken(loginUser);
        }
        return null;
    }
}
