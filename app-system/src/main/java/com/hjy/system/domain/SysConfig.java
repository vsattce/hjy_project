package com.hjy.system.domain;

//import com.ruoyi.common.annotation.Excel;
//import com.ruoyi.common.annotation.Excel.ColumnType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hjy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfig extends BaseEntity
{
    @TableId(value = "config_id", type = IdType.AUTO)
    private Long id;
    /** 参数主键 */
    private Long configId;

    /** 参数名称 */
//    @Excel(name = "参数名称")
    @TableField("config_name")
    private String configName;

    /** 参数键名 */
//    @Excel(name = "参数键名")
    private String configKey;

    /** 参数键值 */
//    @Excel(name = "参数键值")
    private String configValue;

    /** 系统内置（Y是 N否） */
//    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;

}
