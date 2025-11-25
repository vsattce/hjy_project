package com.hjy.web.controller.system;

import com.hjy.common.constant.Constants;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.entity.SysMenu;
import com.hjy.common.core.domain.entity.vo.SysMenuVo;
import com.hjy.common.core.domain.model.LoginBody;
import com.hjy.common.utils.SecurityUtils;
import com.hjy.common.utils.StringUtils;
import com.hjy.framework.web.service.SysLoginService;
import com.hjy.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysLoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private ISysMenuService  sysMenuService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        System.out.printf("loginBody = " + loginBody);
//        // 生成令牌
        String token = sysLoginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        if (StringUtils.isNotEmpty(token)) {
            ajax.put(Constants.TOKEN, token);
        }else {
            ajax = AjaxResult.error("登录失败");
        }
        return ajax;
    }

    @GetMapping("/getRouters")
    public AjaxResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<SysMenuVo> userMenuTree = sysMenuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(sysMenuService.buildMenus(userMenuTree));
    }
}
