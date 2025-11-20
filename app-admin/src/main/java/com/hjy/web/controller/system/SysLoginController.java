package com.hjy.web.controller.system;

import com.hjy.common.constant.Constants;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.model.LoginBody;
import com.hjy.framework.web.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        System.out.printf("loginBody = " + loginBody);
//        // 生成令牌
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
//        authenticationManager.authenticate(authenticationToken);
        String token = sysLoginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
}
