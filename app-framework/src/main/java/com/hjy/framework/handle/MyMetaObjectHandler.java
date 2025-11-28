package com.hjy.framework.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hjy.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class MyMetaObjectHandler  implements MetaObjectHandler {
    private static final String FIELD_CREATE_BY = "createBy";//创建者
    private static final String FIELD_UPDATE_BY = "updateBy";//更新者
    private static final String FIELD_CREATE_TIME = "createTime";//创建时间
    private static final String FIELD_UPDATE_TIME = "updateTime";//更新时间


    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityUtils.getAuthentication();
        if(Objects.nonNull(authentication)){
            this.fillStrategy(metaObject, FIELD_CREATE_BY, SecurityUtils.getUsername());
            // this.fillStrategy(metaObject, "companyId", sysUserUtils.getUser().getCompanyId());
        }
        this.fillStrategy(metaObject, FIELD_CREATE_TIME, new Date());
        updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityUtils.getAuthentication();
        if (Objects.nonNull(authentication)) {
            this.fillStrategy(metaObject, FIELD_UPDATE_BY, SecurityUtils.getUsername());
            // this.fillStrategy(metaObject, FIELD_COMPANY_ID, loginUser.getCompanyId());
        }
        this.fillStrategy(metaObject, FIELD_UPDATE_TIME, new Date());
    }
}
