package com.hjy.web.controller.system;

import com.hjy.system.domain.SysConfig;
import com.hjy.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SysConfigController {

    @Autowired
    private ISysConfigService sysConfigService;

    @GetMapping("/list")
    public List<SysConfig> getSysConfigList(){
//        sysConfigService.list();
        return sysConfigService.list();
    }


}
