package com.hjy.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TreeVo树结构实体类
 *
 * @author xucy
 * @Date 2025-03-21 13:39:18
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Alias("treeBaseEntity")//别名，防止mybatis和ruoyi的名字冲突
public class TreeBaseEntity<T,ID> implements Serializable {
   // private static final long serialVersionUID = 1L;
    /**
     * 节点ID
     */
//    private Integer id;
    /**
     * 父ID
     */
    private ID parentId;
//    getParentId
    /**
     * 节点代码
     */
//    private String code;

    /**
     * 节点名称
     */
//    private String name;

    /**
     * 是否启用(0不启用,1启用)
     */
//    private String enable;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<T> children;
//    ID getId();                 // 获取主键
//    ID getParentId();           // 获取父ID
//    void setChildren(List<T> children); // 设置子节点
    /**
     * 根据普通的节点集合构建树形结构
     *
     * @param flatTreeBaseEntityList 扁平对象
     * @return 树形结构
     */
//    public static List<TreeBaseEntity> buildTree(List<TreeBaseEntity> flatTreeBaseEntityList) {
//        return buildTree(flatTreeBaseEntityList, null);
//    }
//
//    /**
//     * 根据普通的节点集合构建树形结构
//     *
//     * @param flatTreeBaseEntityList 扁平对象
//     * @param parentId   顶级父id为0或者空
//     * @return 树形结构
//     */
//    public static List<TreeBaseEntity> buildTree(List<TreeBaseEntity> flatTreeBaseEntityList, Integer parentId) {
//        List<TreeBaseEntity> parentTreeBaseEntityList = Collections.emptyList();
//        if (Objects.isNull(parentId)) {
//            //如果为空则自动找顶层父节点
//            List<Integer> allIdList = flatTreeBaseEntityList.stream().map(TreeBaseEntity::getId).collect(Collectors.toList());
//            parentTreeBaseEntityList = flatTreeBaseEntityList.stream()
//                    //父节点在id中找不到，则认为是顶层节点
//                    .filter(parent -> !allIdList.contains(parent.getParentId()))
//                    .collect(Collectors.toList());
//
//        } else {
//            parentTreeBaseEntityList = flatTreeBaseEntityList.stream()
//                    .filter(parent -> parent.getParentId().equals(parentId))
//                    .collect(Collectors.toList());
//        }
//        // 把父节点children递归赋值成为子节点
//        List<TreeBaseEntity> treeBaseEntityList = parentTreeBaseEntityList.stream()
//                .map(child -> {
//                    child.setChildren(buildTree(flatTreeBaseEntityList, child.getId()));
//                    return child;
//                }).collect(Collectors.toList());
//        return treeBaseEntityList;
//    }
}
